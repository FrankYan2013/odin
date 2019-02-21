package com.huifu.odin.biz.trans;

import com.huifu.odin.biz.base.BaseBiz;
import com.huifu.odin.dal.entity.DtAcctInfo;
import com.huifu.odin.dal.entity.FrtDtlLog;
import com.huifu.odin.dal.entity.FrzLog;
import com.huifu.odin.dal.mapper.*;
import com.huifu.odin.dal.repository.FrzLogRepository;
import com.huifu.odin.facade.service.trans.AcctUnfreezeRequestDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author frank
 */
@Service
public class UnfreezeService extends BaseBiz {

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

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public static String UNFREEZE_TRANS_TYPE = "9002";

    public static String UNFREEZE_STAT_DO_NOT_UNFREEZE = "N";


    public void doUnfreeze(String dbSysDate, String dbSysTimeToSave, List<AcctUnfreezeRequestDetailDTO> acctUnfreezeRequestDetailDTOs, String sysId) {
        try {
            if (null != acctUnfreezeRequestDetailDTOs) {
                for (AcctUnfreezeRequestDetailDTO acctUnfreezeRequestDetailDTO : acctUnfreezeRequestDetailDTOs) {
                    FrzLog frzLog = frzLogRepository.queryOriginalFrzLog(acctUnfreezeRequestDetailDTO.getFrozenAcctDate(), acctUnfreezeRequestDetailDTO.getFrozenAcctSeqId(), acctUnfreezeRequestDetailDTO.getFrzCode());
                    super.bizLogDebug("冻结原始交易", frzLog, logger);
                    if (frzLog == null) {
                        logger.error("解冻失败，原始交易信息不存在，解冻交易信息:" + acctUnfreezeRequestDetailDTO.toString());
                        throw new UnFreezeException(UnFreezeExceptionEnum.FREEZE_TRANS_NOT_EXSIT);
                    }
                    validate(acctUnfreezeRequestDetailDTO, frzLog);
                    String frzLogSeq = sequenceMapper.getFrzLogSeq();
                    FrzLog frzLogInsert = createFrzLog(acctUnfreezeRequestDetailDTO, sysId, dbSysTimeToSave, dbSysDate, frzLogSeq);
                    super.bizLogDebug("解冻Frz表插入记录", frzLogInsert, logger);
                    int insertResult = frzLogMapper.insertSelective(frzLogInsert);
                    if (insertResult != 1) {
                        logger.error("解冻失败，解冻9002交易入库失败，解冻交易信息:" + acctUnfreezeRequestDetailDTO.toString());
                        throw new UnFreezeException(UnFreezeExceptionEnum.UNFREEZE_TRANS_INSERT_ERROR);
                    }
                    int updateResult = frzLogRepository.updateFrzStatF2U(acctUnfreezeRequestDetailDTO.getFrozenAcctDate(), acctUnfreezeRequestDetailDTO.getFrozenAcctSeqId(), frzLogSeq, dbSysDate);
                    if (updateResult != 1) {
                        logger.error("解冻失败，原始交易已解冻，更新状态失败，解冻交易信息:" + acctUnfreezeRequestDetailDTO.toString());
                        throw new UnFreezeException(UnFreezeExceptionEnum.UNFREEZE_TRANS_ALREADY_UNFROZEN);
                    }
                    DtAcctInfo dtAcctInfo = dtAcctInfoMapper.selectByPrimaryKeyForUpdate(acctUnfreezeRequestDetailDTO.getCustId(), acctUnfreezeRequestDetailDTO.getSubAcctId());
                    super.bizLogDebug("解冻前余额", dtAcctInfo, logger);
                    int updateUnfreezeBalResult = dtAcctInfoMapper.updateUnfreezeAvlBalByPrimaryKey(new BigDecimal(frzLog.getTransAmt()), frzLog.getCustId(), frzLog.getSubAcctId(), dbSysDate);
                    if (updateUnfreezeBalResult != 1) {
                        logger.error("解冻失败，余额更新失败，解冻交易信息:" + acctUnfreezeRequestDetailDTO.toString());
                        throw new UnFreezeException(UnFreezeExceptionEnum.UNFREEZE_TRANS_AVLBAL_ERROR);
                    }
                    BigDecimal avlBal = dtAcctInfo.getAcctBal().add(new BigDecimal(frzLog.getTransAmt()));
                    super.bizLogDebug("解冻后可用余额", avlBal, logger);
                    FrtDtlLog frzFrtDtlLogUnFreeze = createFrzFrtDtlLog(acctUnfreezeRequestDetailDTO, avlBal.toString(), sysId, frzLogSeq, frzLogInsert);
                    super.bizLogDebug("解冻FrtDtl表插入记录", frzFrtDtlLogUnFreeze, logger);
                    frtDtlLogMapper.insert(frzFrtDtlLogUnFreeze);
                }
            }
        } catch (DuplicateKeyException duplicateKeyException) {
            throw duplicateKeyException;
        } catch (DataAccessException dataAccessException) {
            throw dataAccessException;
        } catch (UnFreezeException unFreezenEx) {
            super.bizLogDebug("解冻接口解冻失败", null, logger);
            throw unFreezenEx;
        } catch (Exception e) {
            super.bizLogDebug("解冻接口系统异常信息", null, logger);
            logger.error("出入账失败，系统异常:，交易信息:" + acctUnfreezeRequestDetailDTOs.toString());
            throw new UnFreezeException(UnFreezeExceptionEnum.SystemError);
        }
    }

    private void validate(AcctUnfreezeRequestDetailDTO acctUnfreezeRequestDetailDTO, FrzLog frzLog) {
        if (!acctUnfreezeRequestDetailDTO.getCustId().equals(frzLog.getCustId())) {
            throw new UnFreezeException(UnFreezeExceptionEnum.UNFREEZE_TRANS_VAL_CUST_ERROR);
        }
        if (!acctUnfreezeRequestDetailDTO.getSubAcctId().equals(frzLog.getSubAcctId())) {
            throw new UnFreezeException(UnFreezeExceptionEnum.UNFREEZE_TRANS_VAL_ACCTID_ERROR);
        }
        if (0 != new BigDecimal(acctUnfreezeRequestDetailDTO.getTransAmt()).compareTo(new BigDecimal(frzLog.getTransAmt()))) {
            throw new UnFreezeException(UnFreezeExceptionEnum.UNFREEZE_TRANS_VAL_AMT_ERROR);
        }
    }

    private FrtDtlLog createFrzFrtDtlLog(final AcctUnfreezeRequestDetailDTO acctTransRequestDetailDTO, String acctBal
            , String sysId, String reqSeqId, FrzLog frzLogInsert) {
        FrtDtlLog ftDtlLog = new FrtDtlLog();
        ftDtlLog.setAcctBal(acctBal);
        ftDtlLog.setAcctType(acctTransRequestDetailDTO.getAcctType());
        ftDtlLog.setBdepId(acctTransRequestDetailDTO.getBedpId());
        ftDtlLog.setBgAcctDate(frzLogInsert.getAcctDate());
        ftDtlLog.setBgSeqId(frzLogInsert.getAcctSeqId());
        ftDtlLog.setCustId(acctTransRequestDetailDTO.getCustId());
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
        ftDtlLog.setTransType(UNFREEZE_TRANS_TYPE);
        return ftDtlLog;
    }

    private FrzLog createFrzLog(AcctUnfreezeRequestDetailDTO acctUnfreezeRequestDetailDTO, String sysId, String sysTime, String frzLogDate, String frzLogSeq) {
        FrzLog frzLog = new FrzLog();
        frzLog.setCustId(acctUnfreezeRequestDetailDTO.getCustId());
        frzLog.setFrtDate(acctUnfreezeRequestDetailDTO.getFrtDate());
        frzLog.setFrtSeqId(acctUnfreezeRequestDetailDTO.getFrtSeqId());
        frzLog.setFrtTxnId1(acctUnfreezeRequestDetailDTO.getFrtTxnId1());
        frzLog.setFrtTxnId2(acctUnfreezeRequestDetailDTO.getFrtTxnId2());
        frzLog.setFrtTxnId3(acctUnfreezeRequestDetailDTO.getFrtTxnId3());
        frzLog.setFrzCode(acctUnfreezeRequestDetailDTO.getFrzCode());
        frzLog.setFrzStat(UNFREEZE_STAT_DO_NOT_UNFREEZE);
        frzLog.setSubAcctId(acctUnfreezeRequestDetailDTO.getSubAcctId());
        frzLog.setSysId(sysId);
        frzLog.setSysTime(sysTime);
        frzLog.setTransAmt(acctUnfreezeRequestDetailDTO.getTransAmt());
        frzLog.setTransName(acctUnfreezeRequestDetailDTO.getTransName());
        frzLog.setTransObj(acctUnfreezeRequestDetailDTO.getTransObj());
        frzLog.setTransType(UNFREEZE_TRANS_TYPE);
        frzLog.setAcctSeqId(frzLogSeq);
        frzLog.setAcctDate(frzLogDate);
        return frzLog;
    }
}
