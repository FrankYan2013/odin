package com.huifu.odin.biz.base;

import com.alibaba.fastjson.JSON;
import com.huifu.odin.biz.freeze.FreezeTransBo;
import com.huifu.odin.dal.entity.DtAcctInfo;
import com.huifu.odin.facade.service.trans.*;
import com.huifu.pyxis.client.PyxisConfig;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author frank
 */
public class BaseBiz {

    protected boolean couldPrintMonitor() {
        Integer prob = PyxisConfig.getInstance().getIntProperty("logger.monitor.prob", 100);
        int num = 1 + (int) (Math.random() * (prob));
        if (num == 1) {
            return true;
        } else {
            return false;
        }
    }

    protected void monitorDebug(String loggerMsg, Logger logger, boolean couldPrint) {
        Boolean isMonitor = PyxisConfig.getInstance().getBooleanProperty("logger.monitor", false);
        if (isMonitor && couldPrint) {
            logger.info("------ Moitor模式 ------" + loggerMsg);
        }
    }

    protected void bizLogDebug(String loggerMsg, Object loggerParameter, Logger logger) {
        Boolean isDebug = PyxisConfig.getInstance().getBooleanProperty("logger.debug", false);
        if (isDebug) {
            String loggerParameterString = "";
            if (loggerParameter != null) {
                if (loggerParameter instanceof String) {
                    loggerParameterString = String.valueOf(loggerParameter);
                } else {
                    loggerParameterString = JSON.toJSONString(loggerParameter);
                }
            }
            logger.info("++++++ DEBUG模式 ++++++" + loggerMsg + ":" + loggerParameterString);
            if (loggerParameter instanceof Exception) {
                ((Exception) loggerParameter).printStackTrace();
            }
        }
    }


    public AcctTransResultPeg createDoTransFailResult(AcctTransRequestPeg acctTransRequestPeg, String failCode, String failMessage) {
        AcctTransResultPeg acctTransResultPeg = new AcctTransResultPeg();
        acctTransResultPeg.setRespCode(failCode);
        acctTransResultPeg.setReqSeqId(acctTransRequestPeg.getReqSeqId());
        acctTransResultPeg.setSysId(acctTransRequestPeg.getSysId());
        acctTransResultPeg.setVersionId(acctTransRequestPeg.getVersionId());
        acctTransResultPeg.setTransCnt(acctTransRequestPeg.getTransCnt());
        acctTransResultPeg.setRespMessage(failMessage);
        acctTransResultPeg.setPrivateFields(acctTransRequestPeg.getPrivateFields());
        return acctTransResultPeg;
    }

    public AcctTransResultPeg createDoTransSuccessResult(AcctTransRequestPeg acctTransRequestPeg, List<AcctTransResultPegDetailDTO> acctTransResultPegDetailDTOS, String successCode, String successMessage) {
        AcctTransResultPeg result = new AcctTransResultPeg();
        result.setRespCode(successCode);
        result.setRespMessage(successMessage);
        result.setVersionId(acctTransRequestPeg.getVersionId());
        result.setSysId(acctTransRequestPeg.getSysId());
        result.setReqSeqId(acctTransRequestPeg.getReqSeqId());
        result.setTransCnt(acctTransRequestPeg.getTransCnt());
        result.setAcctTransResultPegDetailDTOList(acctTransResultPegDetailDTOS);
        result.setPrivateFields(acctTransRequestPeg.getPrivateFields());
        return result;
    }

    public AcctTransResultPeg createDoTransFailResultByAcctError(AcctTransRequestPeg acctTransRequestPeg, String failCode, String failMessage, List<DtAcctInfo> errorAcctInfos) {
        AcctTransResultPeg doTransFailResult = createDoTransFailResult(acctTransRequestPeg, failCode, failMessage);
        List<AcctTransResultPegDetailDTO> acctTransResultPegDetailDtoList = new ArrayList<>();
        for (DtAcctInfo dtAcctInfo : errorAcctInfos) {
            AcctTransResultPegDetailDTO acctTransResultPegDetailDTO = new AcctTransResultPegDetailDTO();
            acctTransResultPegDetailDTO.setAcctStatus(dtAcctInfo.getAcctStatus());
            acctTransResultPegDetailDTO.setCustId(dtAcctInfo.getCustId());
            acctTransResultPegDetailDTO.setSubAcctId(dtAcctInfo.getSubAcctId());
            acctTransResultPegDetailDtoList.add(acctTransResultPegDetailDTO);
        }
        doTransFailResult.setAcctTransResultPegDetailDTOList(acctTransResultPegDetailDtoList);
        return doTransFailResult;
    }

    public UnfreezeTransResult createDoUnfreezeResult(UnfreezeTransRequest unfreezeTransRequest, String returnCode, String returnDesc) {
        UnfreezeTransResult unfreezeTransResult = new UnfreezeTransResult();
        unfreezeTransResult.setSysId(unfreezeTransRequest.getSysId());
        unfreezeTransResult.setVersionId(unfreezeTransRequest.getVersionId());
        unfreezeTransResult.setReqSeqId(unfreezeTransRequest.getReqSeqId());
        unfreezeTransResult.setRespCode(returnCode);
        unfreezeTransResult.setRespMessage(returnDesc);
        return unfreezeTransResult;
    }

    public FreezeTransBo createFreezeTransBo(FreezeTransRequest freezeTransRequest) {
        FreezeTransBo freezeTransBo = new FreezeTransBo();
        if (freezeTransRequest != null) {
            freezeTransBo.setAcctType(freezeTransRequest.getAcctType());
            freezeTransBo.setBedpId(freezeTransRequest.getBedpId());
            freezeTransBo.setCustId(freezeTransRequest.getCustId());
            freezeTransBo.setFrtDate(freezeTransRequest.getFrtDate());
            freezeTransBo.setFrtSeqId(freezeTransRequest.getFrtSeqId());
            freezeTransBo.setFrzCode(freezeTransRequest.getFrzCode());
            freezeTransBo.setReqSeqId(freezeTransRequest.getReqSeqId());
            freezeTransBo.setSubAcctId(freezeTransRequest.getSubAcctId());
            freezeTransBo.setSysId(freezeTransRequest.getSysId());
            freezeTransBo.setTransAmt(freezeTransRequest.getTransAmt());
            freezeTransBo.setTransName(freezeTransRequest.getTransName());
            freezeTransBo.setTransObj(freezeTransRequest.getTransObj());
            freezeTransBo.setVersionId(freezeTransRequest.getVersionId());
        }
        return freezeTransBo;
    }
}
