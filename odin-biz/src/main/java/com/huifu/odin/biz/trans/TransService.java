package com.huifu.odin.biz.trans;

import com.huifu.custmag.facade.response.TradeLimitResult;
import com.huifu.odin.biz.base.BaseBiz;
import com.huifu.odin.biz.custmag.CustMagService;
import com.huifu.odin.biz.exception.BizException;
import com.huifu.odin.biz.freeze.FreezeTransBiz;
import com.huifu.odin.biz.freeze.FreezeTransEnum;
import com.huifu.odin.biz.freeze.FreezeTransException;
import com.huifu.odin.biz.freeze.FreezeTransResponse;
import com.huifu.odin.biz.monitor.KafkaSenderService;
import com.huifu.odin.biz.monitor.TransMonitor;
import com.huifu.odin.dal.mapper.AcctLogMapper;
import com.huifu.odin.facade.service.trans.*;
import com.huifu.odin.integration.custmag.impl.CustmagLimitCodeEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author frank
 */
@Service
@TransMonitor
public class TransService extends BaseBiz {

    @Autowired
    AcctLogMapper acctLogMapper;

    @Autowired
    private TransBiz transBiz;

    @Autowired
    private TransValidationBiz transValidationBiz;

    @Autowired
    private CustMagService custMagService;

    @Autowired
    private KafkaSenderService kafkaSenderService;

    @Autowired
    private UnfreezeBiz unfreezeBiz;

    @Autowired
    private FreezeTransBiz freezeTransBiz;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public AcctTransResultPeg doTransWithMonitor(AcctTransRequestPeg acctTransRequestPeg) {
        boolean needCancelControl = false;
        AcctTransResultPeg acctTransResultPeg = new AcctTransResultPeg();
        TransValidationResult transValidationResult = transValidationBiz.baseValidation(acctTransRequestPeg);
        if (!transValidationResult.isSuccessed()) {
            logger.info("交易验证失败,Code:" + transValidationResult.getFailCode() + ",Message:" + transValidationResult.getFailMessage());
            acctTransResultPeg = transBiz.createDoTransFailResult(acctTransRequestPeg, TransExceptionEnum.TransReqValdationFail.getReturnCode(), transValidationResult.getFailMessage());
        } else {
            try {
                if (!StringUtils.isBlank(acctTransRequestPeg.getVerifyType())) {
                    TradeLimitResult tradeLimitResult = custMagService.tradeLimitControl(acctTransRequestPeg);
                    if (tradeLimitResult != null && !CustmagLimitCodeEnum.transactionPermited(tradeLimitResult.getCode())) {
                        return transBiz.createDoTransFailResult(acctTransRequestPeg, TransExceptionEnum.SystemCustMagLimit.getReturnCode(), TransExceptionEnum.SystemCustMagLimit.getReturnDesc());
                    }
                }
            } catch (Exception e) {
                logger.error("CustMag扣除限额接口调用失败:" + e.getMessage());
            }
            try {
                List<AcctTransResultPegDetailDTO> acctTransResultPegDetailDTOS = transBiz.doTrans(acctTransRequestPeg);
                super.bizLogDebug("返回交易后明细", acctTransResultPegDetailDTOS, logger);
                acctTransResultPeg = transBiz.createDoTransSuccessResult(acctTransRequestPeg, acctTransResultPegDetailDTOS, TransEnum.Success.getReturnCode(), TransEnum.Success.getReturnDesc());
            } catch (TransDuplicateException tex) {
                needCancelControl = true;
                super.bizLogDebug("重复交易,查询原始交易开始", null, logger);
                List<AcctTransResultPegDetailDTO> acctTransResultPegDetailDTOS = transBiz.getDuplicateTrans(acctTransRequestPeg);
                super.bizLogDebug("重复交易,查询原始交易结束，查询结果:", acctTransResultPegDetailDTOS, logger);
                acctTransResultPeg = transBiz.createDoTransSuccessResult(acctTransRequestPeg, acctTransResultPegDetailDTOS, TransEnum.Dup_Success.getReturnCode(), TransEnum.Dup_Success.getReturnDesc());
            } catch (AcctInfoException acctInfoException) {
                needCancelControl = true;
                acctTransResultPeg = transBiz.createDoTransFailResultByAcctError(acctTransRequestPeg, acctInfoException.getCode(), acctInfoException.getMessage(), acctInfoException.getErrorAcctInfos());
            } catch (TransException tex) {
                needCancelControl = true;
                acctTransResultPeg = transBiz.createDoTransFailResult(acctTransRequestPeg, tex.getCode(), tex.getMessage());
            } catch (UnFreezeException unFreezeException) {
                needCancelControl = true;
                acctTransResultPeg = transBiz.createDoTransFailResult(acctTransRequestPeg, unFreezeException.getCode(), unFreezeException.getMessage());
            } finally {
                try {
                    super.bizLogDebug("返回交易结果", acctTransResultPeg, logger);
                    custMagService.tradeCancelControl(acctTransRequestPeg, needCancelControl);
                } catch (Exception e) {
                    logger.error("CustMag取消扣除限额接口调用失败:" + e);
                }
                kafkaSenderService.sendToMimirByKafka(acctTransResultPeg);
            }
        }
        return acctTransResultPeg;
    }

    public UnfreezeTransResult unfreezeTransWithMonitor(UnfreezeTransRequest unfreezeTransRequest) {
        UnfreezeTransResult unfreezeTransResult;
        try {
            TransValidationResult transValidationResult = transValidationBiz.baseValidation(unfreezeTransRequest);
            if (!transValidationResult.isSuccessed()) {
                logger.info("解冻交易验证失败,Code:" + transValidationResult.getFailCode() + ",Message:" + transValidationResult.getFailMessage());
                unfreezeTransResult = unfreezeBiz.createDoUnfreezeResult(unfreezeTransRequest, TransExceptionEnum.TransReqValdationFail.getReturnCode(), transValidationResult.getFailMessage());
            } else {
                String dbSysDateTime = acctLogMapper.selectDbDateTime();
                String dbSysDate = dbSysDateTime.substring(0, 8);
                String dbSysTimeToSave = dbSysDateTime.substring(8, 14);
                unfreezeBiz.doUnfreeze(dbSysDate, dbSysTimeToSave, unfreezeTransRequest.getAcctUnfreezeRequestDetailDTOs(), unfreezeTransRequest.getSysId());
                unfreezeTransResult = unfreezeBiz.createDoUnfreezeResult(unfreezeTransRequest, TransEnum.UnFreezeSuccess.getReturnCode(), TransEnum.UnFreezeSuccess.getReturnDesc());

            }
        } catch (UnFreezeException unFreezeException) {
            unfreezeTransResult = unfreezeBiz.createDoUnfreezeResult(unfreezeTransRequest, unFreezeException.getCode(), unFreezeException.getMessage());
        } catch (Exception e) {
            unfreezeTransResult = unfreezeBiz.createDoUnfreezeResult(unfreezeTransRequest, UnFreezeExceptionEnum.SystemError.getReturnCode(), UnFreezeExceptionEnum.SystemError.getReturnDesc());
        }
        return unfreezeTransResult;
    }

    public FreezeTransResult freezeTransWithMonitor(FreezeTransRequest freezeTransRequest) {
        FreezeTransResult freezeTransResult;
        try {
            FreezeTransResponse freezeTransResponse = freezeTransBiz.doFreezeTrans(createFreezeTransBo(freezeTransRequest));
            freezeTransResult = freezeTransBiz.createDoFreezeResult(freezeTransResponse, TransEnum.FreezeSuccess.getReturnCode(), TransEnum.FreezeSuccess.getReturnDesc());
        } catch (FreezeTransException e) {
            freezeTransResult = freezeTransBiz.createDoFreezeResult(null, e.getCode(), e.getMessage());
        } catch (BizException e) {
            freezeTransResult = freezeTransBiz.createDoFreezeResult(null, e.getCode(), e.getMessage());
        } catch (Exception e) {
            freezeTransResult = freezeTransBiz.createDoFreezeResult(null, FreezeTransEnum.SystemError.getReturnCode(), FreezeTransEnum.SystemError.getReturnDesc());
        }
        return freezeTransResult;
    }


}
