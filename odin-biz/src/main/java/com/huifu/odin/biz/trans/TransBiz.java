package com.huifu.odin.biz.trans;

import com.huifu.odin.biz.base.BaseBiz;
import com.huifu.odin.dal.entity.AcctLog;
import com.huifu.odin.dal.entity.DtAcctInfo;
import com.huifu.odin.dal.entity.FrtDtlLog;
import com.huifu.odin.dal.entity.TransType;
import com.huifu.odin.dal.mapper.*;
import com.huifu.odin.dal.repository.FrzLogRepository;
import com.huifu.odin.facade.service.trans.AcctTransRequestDetailDTO;
import com.huifu.odin.facade.service.trans.AcctTransRequestPeg;
import com.huifu.odin.facade.service.trans.AcctTransResultPegDetailDTO;
import com.huifu.odin.util.common.DateUtils;
import com.huifu.odin.util.common.SortUtils;
import com.huifu.pyxis.client.PyxisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author frank
 */
@Service
public class TransBiz extends BaseBiz {

    @Autowired
    AcctLogMapper acctLogMapper;

    @Autowired
    DtAcctInfoMapper dtAcctInfoMapper;

    @Autowired
    FrtDtlLogMapper frtDtlLogMapper;

    @Autowired
    FrzLogRepository frzLogRepository;

    @Autowired
    TransTypeMapper transTypeMapper;

    @Autowired
    FrzLogMapper frzLogMapper;

    @Autowired
    TransTypeBiz transTypeBiz;

    @Autowired
    SequenceMapper sequenceMapper;

    @Autowired
    UnfreezeService unfreezeService;

    @Autowired
    SysStatMapper sysStatMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    double zeroBal = 0.00;

    @Transactional(rollbackFor = Exception.class)
    public List<AcctTransResultPegDetailDTO> doTrans(final AcctTransRequestPeg acctTransRequestPeg) {

        int sysStat = sysStatMapper.selectSysStat();
        if (1 != sysStat) {
            throw new TransException(TransExceptionEnum.AcctOffLine);
        }
        List<AcctTransResultPegDetailDTO> detailList = new ArrayList<>();
        int acctInfoStatIsDoTransactional = 0;
        List<DtAcctInfo> dtAcctInfoIfAcctInfoStatIsError = new ArrayList<>();
        HashMap<String, BigDecimal> transAmtMap = new HashMap<>();
        HashMap<String, BigDecimal> acctLogAmtMap = new HashMap<>();
        HashMap<String, DtAcctInfo> acctInfoHashMap = new HashMap<>();
        List<AcctLog> acctLogsInsert = new ArrayList<>();
        List<FrtDtlLog> frtDtlLogs = new ArrayList<>();

        try {
            String dbSysDateTime = acctLogMapper.selectDbDateTime();
            String dbSysDate = dbSysDateTime.substring(0, 8);
            String dbSysTimeToSave = dbSysDateTime.substring(8, 14);

            //做冻结
            unfreezeService.doUnfreeze(dbSysDate, dbSysTimeToSave, acctTransRequestPeg.getAcctUnfreezeRequestDetailDTOs(), acctTransRequestPeg.getSysId());

            //做交易
            List<AcctTransRequestDetailDTO> acctTransDetailList = acctTransRequestPeg.getAcctTransDetailList();
            setupDCFlag(acctTransRequestPeg.getAcctTransDetailList());
            sortList(acctTransRequestPeg.getAcctTransDetailList());
            for (AcctTransRequestDetailDTO acctTransRequestDetailDTO : acctTransDetailList) {
                addUpAmtAndLockAcct(transAmtMap, acctInfoHashMap, acctTransRequestDetailDTO);
            }

            List<String> seqs = acctLogMapper.selectSeqByNum(acctTransDetailList.size());

            for (int i = 0; i < acctTransDetailList.size(); i++) {
                AcctTransRequestDetailDTO acctTransRequestDetailDTO = acctTransDetailList.get(i);
                String hashMapKey = getAmtKey(acctTransRequestDetailDTO);
                TransType transType = transTypeBiz.getTransTypeByTransTypeCode(acctTransRequestDetailDTO.getTransType());
                DtAcctInfo dtAcctInfoLocking = acctInfoHashMap.get(hashMapKey);
                acctInfoStatIsDoTransactional = checkAcctStat(acctInfoStatIsDoTransactional, dtAcctInfoIfAcctInfoStatIsError, transType, dtAcctInfoLocking);
                String acctBal = calculateAcctBal(transType, acctTransRequestDetailDTO, acctLogAmtMap, dtAcctInfoLocking);
                AcctLog acctLog = createAcctLog(acctTransRequestDetailDTO, dtAcctInfoLocking, acctTransRequestPeg.getSysId(), transType.getDcFlag(), acctBal, seqs.get(i), dbSysDate, dbSysTimeToSave);
                FrtDtlLog frtDtlLog = createFrtDtlLog(acctTransRequestDetailDTO, acctBal, acctTransRequestPeg.getSysId(), acctTransRequestPeg.getReqSeqId(), acctLog);
                acctLogsInsert.add(acctLog);
                frtDtlLogs.add(frtDtlLog);
                createResultDetails(detailList, acctLog, acctTransRequestDetailDTO, dtAcctInfoLocking, dbSysDateTime, transType.getDcFlag());
            }
            if (acctInfoStatIsDoTransactional > 0) {
                throw new AcctInfoException(TransExceptionEnum.TransAcctInfoStatError, dtAcctInfoIfAcctInfoStatIsError);
            }
            acctLogMapper.insertList(acctLogsInsert);
            frtDtlLogMapper.insertList(frtDtlLogs);
            for (String key : acctInfoHashMap.keySet()) {
                DtAcctInfo acctInfoForUpdate = acctInfoHashMap.get(key);
                BigDecimal transAmt = transAmtMap.get(key);
                updateDtAcctInfos(acctInfoForUpdate.getAvlBal(), transAmt, acctInfoForUpdate.getCustId(), acctInfoForUpdate.getSubAcctId());
            }
            return detailList;
        } catch (DuplicateKeyException duplicateKeyException) {
            super.bizLogDebug("出入账接口重复交易", duplicateKeyException, logger);
            logger.error("出入账失败，此交易为重复交易，交易信息:" + acctTransRequestPeg.toString());
            throw new TransDuplicateException(TransExceptionEnum.DuplicateKeyException);
        } catch (DataAccessException dataAccessException) {
            super.bizLogDebug("出入账接口数据库操作异常", dataAccessException, logger);
            logger.error("出入账失败，数据库操作失败:" + dataAccessException.getMessage() + "，交易信息:" + acctTransRequestPeg.toString());
            throw new TransException(TransExceptionEnum.Db_Error);
        } catch (AcctInfoException acctInfoException) {
            super.bizLogDebug("出入账接口账户异常", null, logger);
            logger.error("出入账失败，账户异常:" + acctInfoException.getMessage() + "交易信息:" + acctTransRequestPeg.toString());
            throw acctInfoException;
        } catch (UnFreezeException unFreezeException) {
            super.bizLogDebug("出入账接口解冻操作异常", null, logger);
            logger.error("出入账失败，数据库操作失败:" + unFreezeException.getMessage() + "，交易信息:" + acctTransRequestPeg.toString());
            throw unFreezeException;
        } catch (TransException transException) {
            super.bizLogDebug("出入账接口业务逻辑异常", null, logger);
            logger.error("出入账失败，业务异常:" + transException.getMessage() + "，交易信息:" + acctTransRequestPeg.toString());
            throw transException;
        } catch (Exception e) {
            super.bizLogDebug("出入账接口系统异常信息", e, logger);
            logger.error("出入账失败，系统异常:，交易信息:" + acctTransRequestPeg.toString());
            throw new TransException(TransExceptionEnum.SystemError);
        }
    }

    private String getAmtKey(AcctTransRequestDetailDTO acctTransRequestDetailDTO) {
        return acctTransRequestDetailDTO.getCustId() + acctTransRequestDetailDTO.getSubAcctId() + acctTransRequestDetailDTO.getAcctType();
    }


    private String calculateAcctBal(TransType transType, AcctTransRequestDetailDTO acctTransRequestDetailDTO, HashMap<String, BigDecimal> acctLogMap, DtAcctInfo acctInfo) {
        try {
            BigDecimal transAmt;
            String acctLogAvlBalAmt;
            String acctKey = getAmtKey(acctTransRequestDetailDTO);
            if (acctLogMap.get(acctKey) == null) {
                transAmt = acctInfo.getAcctBal();
            } else {
                transAmt = acctLogMap.get(acctKey);
            }
            super.bizLogDebug("计算交易扣后余额开始,交易对象", acctTransRequestDetailDTO, logger);
            super.bizLogDebug("对象扣前余额", transAmt, logger);
            super.bizLogDebug("对象交易金额", acctTransRequestDetailDTO.getTransAmt(), logger);
            super.bizLogDebug("对象手续费金额", acctTransRequestDetailDTO.getFeeAmount(), logger);
            if (TransEnum.TransTypeD.getReturnCode().equals(transType.getDcFlag())) {
                acctLogAvlBalAmt = transAmt.subtract(new BigDecimal(acctTransRequestDetailDTO.getTransAmt())).subtract(new BigDecimal(acctTransRequestDetailDTO.getFeeAmount())).toString();
            } else {
                acctLogAvlBalAmt = transAmt.add(new BigDecimal(acctTransRequestDetailDTO.getTransAmt())).subtract(new BigDecimal(acctTransRequestDetailDTO.getFeeAmount())).toString();
            }
            acctLogMap.put(acctKey, new BigDecimal(acctLogAvlBalAmt));
            super.bizLogDebug("对象扣后余额为", acctLogAvlBalAmt, logger);
            return acctLogAvlBalAmt;
        } catch (Exception e) {
            throw new TransException(TransExceptionEnum.CALCULATE_ACCT_BAL_ERROR);
        }
    }

    private void addUpAmtAndLockAcct(HashMap<String, BigDecimal> transAmtMap, HashMap<String, DtAcctInfo> acctInfoHashMap, AcctTransRequestDetailDTO acctTransRequestDetailDTO) {
        TransType transType = transTypeBiz.getTransTypeByTransTypeCode(acctTransRequestDetailDTO.getTransType());
        super.bizLogDebug("交易类型为", transType, logger);
        String key = getAmtKey(acctTransRequestDetailDTO);
        super.bizLogDebug("需要锁账户key", key, logger);
        BigDecimal updateAmt;
        if (TransEnum.TransTypeD.getReturnCode().equals(transType.getDcFlag())) {
            updateAmt = new BigDecimal(acctTransRequestDetailDTO.getTransAmt()).multiply(new BigDecimal(-1));
        } else {
            updateAmt = new BigDecimal(acctTransRequestDetailDTO.getTransAmt());
        }
        super.bizLogDebug("计算本账户本次需要累计交易金额:", updateAmt, logger);
        updateAmt = updateAmt.subtract(new BigDecimal(acctTransRequestDetailDTO.getFeeAmount()));
        super.bizLogDebug("计算本账户本次需要累计交易金额扣除手续费后:", updateAmt, logger);
        if (transAmtMap.containsKey(key)) {
            updateAmt = transAmtMap.get(key).add(updateAmt);
            super.bizLogDebug("账户数据已行锁，此交易存在相同账户，累计后交易金额", updateAmt, logger);
        } else {
            DtAcctInfo dtAcctInfo = dtAcctInfoMapper.selectByPrimaryKeyForUpdate(acctTransRequestDetailDTO.getCustId(), acctTransRequestDetailDTO.getSubAcctId());
            super.bizLogDebug("查询账户信息", dtAcctInfo, logger);
            if (null == dtAcctInfo) {
                super.bizLogDebug("账户不存在", null, logger);
                throw new TransException(TransExceptionEnum.AcctIsNull);
            }
            acctInfoHashMap.put(key, dtAcctInfo);
            super.bizLogDebug("账户数据行锁，第一次累计交易金额", updateAmt, logger);
        }
        transAmtMap.put(key, updateAmt);
    }

    private int checkAcctStat(int acctInfoStatIsDoTransactional, List<DtAcctInfo> dtAcctInfoIfAcctInfoStatIsError, TransType transType, DtAcctInfo dtAcctInfo) {
        if (!validationAcctInfoStat(transType.getDcFlag(), dtAcctInfo, dtAcctInfoIfAcctInfoStatIsError)) {
            acctInfoStatIsDoTransactional++;
        }
        return acctInfoStatIsDoTransactional;
    }

    private void sortList(List<AcctTransRequestDetailDTO> acctTransDetailList) {
        super.bizLogDebug("分账串排序前", acctTransDetailList, logger);
        ArrayList<String> sortFields = new ArrayList<>();
        sortFields.add(SortUtils.SORT_CUST_ID);
        sortFields.add(SortUtils.SORT_SUB_ACCT_ID);
        if (PyxisConfig.getInstance().getBooleanProperty(SortUtils.SORT_DC_FLAG_PYXIS_KEY, SortUtils.SORT_DC_FLAG_PYXIS_DEFAULT_VALUE)) {
            sortFields.add(SortUtils.SORT_DC_FLAG);
        }
        SortUtils.sortList(acctTransDetailList, sortFields, SortUtils.SORT_ORDER_ASC);
        super.bizLogDebug("分账串排序后", acctTransDetailList, logger);
    }

    private boolean validationAcctInfoStat(String transTrypeDCFlag, DtAcctInfo dtAcctInfoForUpdate, List<DtAcctInfo> dtAcctInfosIfacctinfoStatIsError) {
        dtAcctInfosIfacctinfoStatIsError.add(dtAcctInfoForUpdate);
        if (TransEnum.AcctInfoC.getReturnCode().equals(dtAcctInfoForUpdate.getAcctStatus())) {
            super.bizLogDebug("出入账接口账户状态为:" + TransEnum.AcctInfoC.getReturnDesc() + "，交易失败", dtAcctInfoForUpdate, logger);
            return false;
        }
        if (TransEnum.AcctInfoD.getReturnCode().equals(dtAcctInfoForUpdate.getAcctStatus())) {
            super.bizLogDebug("出入账接口账户状态为:" + TransEnum.AcctInfoD.getReturnDesc() + "，交易失败", dtAcctInfoForUpdate, logger);
            return false;
        }
        if (TransEnum.AcctInfoF.getReturnCode().equals(dtAcctInfoForUpdate.getAcctStatus()) && TransEnum.TransTypeD.getReturnCode().equals(transTrypeDCFlag)) {
            super.bizLogDebug("出入账接口账户状态为:" + TransEnum.AcctInfoF.getReturnDesc() + "，交易失败", dtAcctInfoForUpdate, logger);
            return false;
        }
        return true;
    }

    private void createResultDetails(List<AcctTransResultPegDetailDTO> detailList, AcctLog acctLog, AcctTransRequestDetailDTO acctTransRequestDetailDTO, DtAcctInfo dtAcctInfo, String dbSysDateTime, String dcFlag) {
        AcctTransResultPegDetailDTO detail = new AcctTransResultPegDetailDTO();
        detail.setFrtDate(acctLog.getFrtDate());
        detail.setFrtSeqId(acctLog.getFrtSeqId());
        detail.setTransType(acctLog.getTransType());
        detail.setCustId(acctLog.getCustId());
        detail.setSubAcctId(acctLog.getSubAcctId());
        detail.setTransAmt(acctLog.getTransAmt());
        detail.setAcctDate(acctLog.getAcctDate());
        detail.setAcctSeqId(acctLog.getAcctSeqId());
        detail.setAcctBal(acctLog.getAcctBal());
        detail.setPrivateFields(acctTransRequestDetailDTO.getPrivateFields());
        detail.setAcctStatus(dtAcctInfo.getAcctStatus());
        detail.setAcctDateTime(dbSysDateTime);
        detail.setDcFlag(dcFlag);
        detail.setFeeAmt(acctLog.getFeeAmt());
        detailList.add(detail);
    }


    protected AcctLog createAcctLog(final AcctTransRequestDetailDTO acctTransRequestDetailDTO, DtAcctInfo
            dtAcctInfo, String sysId, String dcFlag, String acctBal, String seqId, String dbSysDate, String dbSysTime) {
        AcctLog acctLog = new AcctLog();
        acctLog.setAcctBal(acctBal);
        acctLog.setAcctName(dtAcctInfo.getAcctName());
        acctLog.setCustInfo(dtAcctInfo.getCustInfo());
        acctLog.setDcFlag(dcFlag);
        acctLog.setAcctType(acctTransRequestDetailDTO.getAcctType());
        acctLog.setBdepId(acctTransRequestDetailDTO.getBedpId());
        acctLog.setCustId(acctTransRequestDetailDTO.getCustId());
        acctLog.setCustId2(acctTransRequestDetailDTO.getCustId2());
        acctLog.setFeeAmt(acctTransRequestDetailDTO.getFeeAmount());
        acctLog.setFrtDate(acctTransRequestDetailDTO.getFrtDate());
        acctLog.setFrtSeqId(acctTransRequestDetailDTO.getFrtSeqId());
        acctLog.setFrtTxnId1(acctTransRequestDetailDTO.getFrtTxnId1());
        acctLog.setFrtTxnId2(acctTransRequestDetailDTO.getFrtTxnId2());
        acctLog.setFrtTxnId3(acctTransRequestDetailDTO.getFrtTxnId3());
        acctLog.setSubAcctId(acctTransRequestDetailDTO.getSubAcctId());
        acctLog.setSysId(sysId);
        acctLog.setAcctDate(dbSysDate);
        acctLog.setSysTime(dbSysTime);
        acctLog.setTransAmt(acctTransRequestDetailDTO.getTransAmt());
        acctLog.setTransName(acctTransRequestDetailDTO.getTransName());
        acctLog.setTransObj(acctTransRequestDetailDTO.getTransObj());
        acctLog.setTransType(acctTransRequestDetailDTO.getTransType());
        acctLog.setAcctSeqId(seqId);
        return acctLog;
    }

    protected void updateDtAcctInfos(BigDecimal acctBal, BigDecimal transAmt, String custId, String acctId) {
        int updateSuccess;
        if (acctBal.add(transAmt).compareTo(new BigDecimal(zeroBal)) < 0) {
            throw new TransException(TransExceptionEnum.InsufficientBal);
        }
        super.bizLogDebug("账户出款，尝试更新并解锁表，账户可用余额:" + acctBal + ",需要增减余额", transAmt, logger);
        updateSuccess = dtAcctInfoMapper.updateAvlBalByPrimaryKey(transAmt, custId, acctId, DateUtils.getCurrentDate());
        if (updateSuccess != 1) {
            throw new TransException(TransExceptionEnum.AcctIsNull);
        } else {
            DtAcctInfo dtAcctInfo = dtAcctInfoMapper.selectByPrimaryKey(custId, acctId);
            super.bizLogDebug("账户出款，更新并解锁表成功，操作后账户信息:", dtAcctInfo, logger);

        }
    }


    protected FrtDtlLog createFrtDtlLog(final AcctTransRequestDetailDTO acctTransRequestDetailDTO, String acctBal
            , String sysId, String reqSeqId, AcctLog acctLog) {
        FrtDtlLog ftDtlLog = new FrtDtlLog();
        ftDtlLog.setAcctBal(acctBal);
        ftDtlLog.setAcctType(acctTransRequestDetailDTO.getAcctType());
        ftDtlLog.setBdepId(acctTransRequestDetailDTO.getBedpId());
        ftDtlLog.setBgAcctDate(acctLog.getAcctDate());
        ftDtlLog.setBgSeqId(acctLog.getAcctSeqId());
        ftDtlLog.setCustId(acctTransRequestDetailDTO.getCustId());
        ftDtlLog.setCustId2(acctTransRequestDetailDTO.getCustId2());
        ftDtlLog.setFeeAmt(acctTransRequestDetailDTO.getFeeAmount());
        ftDtlLog.setFrtDate(acctTransRequestDetailDTO.getFrtDate());
        ftDtlLog.setFrtSeqId(acctTransRequestDetailDTO.getFrtSeqId());
        ftDtlLog.setFrtTxnId1(acctTransRequestDetailDTO.getFrtTxnId1());
        ftDtlLog.setFrtTxnId2(acctTransRequestDetailDTO.getFrtTxnId2());
        ftDtlLog.setFrtTxnId3(acctTransRequestDetailDTO.getFrtTxnId3());
        ftDtlLog.setReqSeqId(reqSeqId);
        ftDtlLog.setSubAcctId(acctTransRequestDetailDTO.getSubAcctId());
        ftDtlLog.setSysId(sysId);
        ftDtlLog.setTransAmt(acctTransRequestDetailDTO.getTransAmt());
        ftDtlLog.setTransName(acctTransRequestDetailDTO.getTransName());
        ftDtlLog.setTransObj(acctTransRequestDetailDTO.getTransObj());
        ftDtlLog.setTransType(acctTransRequestDetailDTO.getTransType());
        return ftDtlLog;
    }


    public void setupDCFlag(List<AcctTransRequestDetailDTO> acctTransRequestDetailDTOList) {
        for (AcctTransRequestDetailDTO acctTransRequestDetailDTO : acctTransRequestDetailDTOList) {
            TransType transTypeByTransTypeCode = transTypeBiz.getTransTypeByTransTypeCode(acctTransRequestDetailDTO.getTransType());
            acctTransRequestDetailDTO.setDcFlag(transTypeByTransTypeCode.getDcFlag());
        }
        super.bizLogDebug("分账串增加DC_Flag", acctTransRequestDetailDTOList, logger);
    }

    public List<AcctTransResultPegDetailDTO> getDuplicateTrans(AcctTransRequestPeg acctTransRequestPeg) {
        List<AcctTransResultPegDetailDTO> acctTransResultPegDetailDTOs = new ArrayList<>();
        for (AcctTransRequestDetailDTO acctTransRequestDetailDTO : acctTransRequestPeg.getAcctTransDetailList()) {
            AcctTransResultPegDetailDTO detail = new AcctTransResultPegDetailDTO();
            FrtDtlLog frtDtlLog = frtDtlLogMapper.selectByPrimaryKey(acctTransRequestDetailDTO.getFrtDate(), acctTransRequestDetailDTO.getFrtSeqId(), acctTransRequestPeg.getSysId(), acctTransRequestDetailDTO.getTransType());
            detail.setFrtDate(acctTransRequestDetailDTO.getFrtDate());
            detail.setFrtSeqId(acctTransRequestDetailDTO.getFrtSeqId());
            detail.setTransType(acctTransRequestDetailDTO.getTransType());
            detail.setCustId(acctTransRequestDetailDTO.getCustId());
            detail.setSubAcctId(acctTransRequestDetailDTO.getSubAcctId());
            detail.setTransAmt(acctTransRequestDetailDTO.getTransAmt());
            if (frtDtlLog != null) {
                detail.setAcctDate(frtDtlLog.getBgAcctDate());
                detail.setAcctSeqId(frtDtlLog.getBgSeqId());
                detail.setAcctBal(frtDtlLog.getAcctBal());
            }
            detail.setPrivateFields(acctTransRequestDetailDTO.getPrivateFields());
            acctTransResultPegDetailDTOs.add(detail);
        }
        return acctTransResultPegDetailDTOs;
    }
}
