package com.huifu.odin.biz.freeze;

import com.alibaba.fastjson.JSON;
import com.huifu.odin.biz.validate.SaturnValidating;
import com.huifu.odin.dal.entity.DtAcctInfo;
import com.huifu.odin.dal.entity.FrtDtlLog;
import com.huifu.odin.dal.entity.FrzLog;
import com.huifu.odin.dal.mapper.DtAcctInfoMapper;
import com.huifu.odin.dal.mapper.FrtDtlLogMapper;
import com.huifu.odin.dal.mapper.FrzLogMapper;
import com.huifu.odin.dal.mapper.SequenceMapper;
import com.huifu.odin.facade.service.trans.FreezeTransResult;
import com.huifu.odin.util.common.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * @author frank
 */
@Service
@SaturnValidating
public class FreezeTransBiz {

    private static final String ACCT_STAT_N = "N";

    @Autowired
    FrzLogMapper frzLogMapper;

    @Autowired
    FrtDtlLogMapper frtDtlLogMapper;

    @Autowired
    DtAcctInfoMapper dtAcctInfoMapper;

    @Autowired
    SequenceMapper sequenceMapper;


    public static String FREEZE_STAT_FROZEN = "F";

    public static String FREEZE_TRANS_TYPE = "9001";


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional(rollbackFor = Exception.class)
    public FreezeTransResponse doFreezeTrans(@Valid FreezeTransBo freezeTransRequest) throws FreezeTransException {
        FreezeTransResponse freezeTransResponse;
        try {
            String dbSysDateTime = frzLogMapper.selectDbDateTime();
            String dbSysDate = dbSysDateTime.substring(0, 8);
            String dbSysTimeToSave = dbSysDateTime.substring(8, 14);
            int updateSuccess = 0;
            DtAcctInfo dtAcctInfo = dtAcctInfoMapper.selectByPrimaryKeyForUpdate(freezeTransRequest.getCustId(), freezeTransRequest.getSubAcctId());
            if (dtAcctInfo == null) {
                throw new FreezeTransException(FreezeTransExceptionEnum.AcctIsNull);
            }
            logger.info("冻结账户行锁成功,操作前账户信息:{}", JSON.toJSONString(dtAcctInfo));
            if (dtAcctInfo.getAvlBal().compareTo(new BigDecimal(freezeTransRequest.getTransAmt())) < 0) {
                throw new FreezeTransException(FreezeTransExceptionEnum.InsufficientBal);
            }
            if (!ACCT_STAT_N.equals(dtAcctInfo.getAcctStatus())) {
                throw new FreezeTransException(FreezeTransExceptionEnum.TransAcctInfoStatError);
            }
            updateSuccess = dtAcctInfoMapper.updateFreezeAvlBalByPrimaryKey(new BigDecimal(freezeTransRequest.getTransAmt()), freezeTransRequest.getCustId(), freezeTransRequest.getSubAcctId(), DateUtils.getCurrentDate());
            logger.info("更新余额并解锁行,更新成功账户数量:{}", updateSuccess);
            DtAcctInfo dtAcctInfoUnlock = dtAcctInfoMapper.selectByPrimaryKey(freezeTransRequest.getCustId(), freezeTransRequest.getSubAcctId());
            logger.info("冻结账户解锁成功,操作后账户信息:{}", JSON.toJSONString(dtAcctInfoUnlock));
            String frzSeqId = sequenceMapper.getFrzLogSeq();
            FrtDtlLog frtDtlRecord = setupFrtDtLog(freezeTransRequest, dbSysDate, frzSeqId, dtAcctInfoUnlock.getAcctBal().toString());
            logger.info("增加frtDtl交易,交易信息:{}", JSON.toJSONString(frtDtlRecord));
            frtDtlLogMapper.insert(frtDtlRecord);
            logger.info("增加frtDtl交易成功");
            FrzLog frzRecord = setupFrzRecord(freezeTransRequest, dbSysTimeToSave, dbSysDate, frzSeqId, frtDtlRecord.getFrtSeqId());
            logger.info("增加frz交易,交易信息:{}", JSON.toJSONString(frzRecord));
            frzLogMapper.insert(frzRecord);
            logger.info("增加frz交易成功,冻结操作完成");
            freezeTransResponse = setupResponse(dbSysDate, frzRecord.getAcctSeqId(), freezeTransRequest.getTransAmt(), freezeTransRequest.getSysId(), freezeTransRequest.getVersionId(), freezeTransRequest.getReqSeqId());
        } catch (DuplicateKeyException duplicateKeyException) {
            logger.error("冻结交易失败，此交易为重复交易，交易信息:" + freezeTransRequest.toString());
            throw new FreezeTransException(FreezeTransExceptionEnum.DuplicateKeyException);
        } catch (DataAccessException dataAccessException) {
            logger.error("冻结交易失败，数据库操作失败:" + dataAccessException.getMessage() + "，交易信息:" + freezeTransRequest.toString());
            throw new FreezeTransException(FreezeTransExceptionEnum.Db_Error);
        } catch (FreezeTransException freezeTransException) {
            logger.info("冻结交易失败，交易信息:" + freezeTransRequest.toString() + ",异常code:" + freezeTransException.getCode() + ",异常message:" + freezeTransException.getMessage());
            throw freezeTransException;
        } catch (Exception e) {
            logger.error("冻结交易失败:" + e.getLocalizedMessage());
            throw e;
        }
        return freezeTransResponse;
    }

    private FreezeTransResponse setupResponse(String acctDate, String acctSeqId, String transAmt, String sysId, String versionId, String reqSeqId) {
        FreezeTransResponse freezeTransResponse = new FreezeTransResponse();
        freezeTransResponse.setAcctDate(acctDate);
        freezeTransResponse.setAcctSeqId(acctSeqId);
        freezeTransResponse.setVersionId(versionId);
        freezeTransResponse.setReturnCode(FreezeTransEnum.FREEZE_SUCCESS.getReturnCode());
        freezeTransResponse.setReturnDesc(FreezeTransEnum.FREEZE_SUCCESS.getReturnDesc());
        freezeTransResponse.setTransAmt(transAmt);
        freezeTransResponse.setSysId(sysId);
        freezeTransResponse.setReqSeqId(reqSeqId);
        return freezeTransResponse;
    }

    private FrtDtlLog setupFrtDtLog(FreezeTransBo freezeTransRequest, String dbAcctDate, String acctSeqId, String acctBal) {
        FrtDtlLog ftDtlLog = new FrtDtlLog();
        ftDtlLog.setAcctBal(acctBal);
        ftDtlLog.setAcctType(freezeTransRequest.getAcctType());
        ftDtlLog.setBdepId(freezeTransRequest.getBedpId());
        ftDtlLog.setBgAcctDate(dbAcctDate);
        ftDtlLog.setBgSeqId(acctSeqId);
        ftDtlLog.setCustId(freezeTransRequest.getCustId());
        ftDtlLog.setFrtDate(freezeTransRequest.getFrtDate());
        ftDtlLog.setFrtSeqId(freezeTransRequest.getFrtSeqId());
        ftDtlLog.setFrtTxnId1(freezeTransRequest.getFrzCode());
        ftDtlLog.setReqSeqId(freezeTransRequest.getReqSeqId());
        ftDtlLog.setSubAcctId(freezeTransRequest.getSubAcctId());
        ftDtlLog.setSysId(freezeTransRequest.getSysId());
        ftDtlLog.setTransAmt(freezeTransRequest.getTransAmt());
        ftDtlLog.setTransName(freezeTransRequest.getTransName());
        ftDtlLog.setTransObj(freezeTransRequest.getTransObj());
        ftDtlLog.setTransType(FREEZE_TRANS_TYPE);
        return ftDtlLog;
    }

    private FrzLog setupFrzRecord(FreezeTransBo freezeTransRequest, String sysTime, String sysDate, String frzSeqId, String frtSeqId) {
        FrzLog frzLog = new FrzLog();
        frzLog.setCustId(freezeTransRequest.getCustId());
        frzLog.setFrtDate(freezeTransRequest.getFrtDate());
        frzLog.setFrtSeqId(frtSeqId);
        frzLog.setFrtTxnId1(freezeTransRequest.getFrzCode());
        frzLog.setFrzCode(freezeTransRequest.getFrzCode());
        frzLog.setFrzStat(FREEZE_STAT_FROZEN);
        frzLog.setSubAcctId(freezeTransRequest.getSubAcctId());
        frzLog.setSysId(freezeTransRequest.getSysId());
        frzLog.setSysTime(sysTime);
        frzLog.setTransAmt(freezeTransRequest.getTransAmt());
        frzLog.setTransName(freezeTransRequest.getTransName());
        frzLog.setTransType(FREEZE_TRANS_TYPE);
        frzLog.setTransObj(freezeTransRequest.getTransObj());
        frzLog.setAcctDate(sysDate);
        frzLog.setAcctSeqId(frzSeqId);
        return frzLog;
    }


    public FreezeTransResult createDoFreezeResult(FreezeTransResponse freezeTransResponse, String returnCode, String returnDesc) {
        FreezeTransResult freezeTransResult = new FreezeTransResult();
        if (freezeTransResponse != null) {
            freezeTransResult.setSysId(freezeTransResponse.getSysId());
            freezeTransResult.setVersionId(freezeTransResponse.getVersionId());
            freezeTransResult.setFrozenAcctSeqId(freezeTransResponse.getAcctSeqId());
            freezeTransResult.setFrozenAcctDate(freezeTransResponse.getAcctDate());
            freezeTransResult.setReqSeqId(freezeTransResponse.getReqSeqId());
        }
        freezeTransResult.setRespCode(returnCode);
        freezeTransResult.setRespMessage(returnDesc);
        return freezeTransResult;
    }
}
