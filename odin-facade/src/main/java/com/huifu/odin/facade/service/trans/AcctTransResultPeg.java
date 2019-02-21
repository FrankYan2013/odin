package com.huifu.odin.facade.service.trans;

import java.io.Serializable;
import java.util.List;

/**
 * @author frank
 */
public class AcctTransResultPeg implements Serializable {

    private static final long serialVersionUID = -5000017793447526201L;

    private static final String ACCT_RESPONSE_SUCCESS = "000";

    private static final String ACCT_DUPLICATE_AND_RESPONSE_SUCCESS = "001";

    private String respCode;
    private String respMessage;
    private String versionId;
    private String sysId;
    private String reqSeqId;
    private String transCnt;

    private List<AcctUnfreezeResultDetailDTO> acctUnfreezeResultDetailDTOS;

    public String getPrivateFields() {
        return privateFields;
    }

    public void setPrivateFields(String privateFields) {
        this.privateFields = privateFields;
    }

    private String privateFields;
    private List<AcctTransResultPegDetailDTO> acctTransResultPegDetailDTOList;

    public AcctTransResultPeg() {
    }

    public String getReqSeqId() {
        return this.reqSeqId;
    }

    public void setReqSeqId(String reqSeqId) {
        this.reqSeqId = reqSeqId;
    }

    public String getRespCode() {
        return this.respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getVersionId() {
        return this.versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getSysId() {
        return this.sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getCtrlSeqId() {
        return this.reqSeqId;
    }

    public void setCtrlSeqId(String ctrlSeqId) {
        this.reqSeqId = ctrlSeqId;
    }

    public String getTransCnt() {
        return this.transCnt;
    }

    public void setTransCnt(String transCnt) {
        this.transCnt = transCnt;
    }

    public List<AcctTransResultPegDetailDTO> getAcctTransResultPegDetailDTOList() {
        return acctTransResultPegDetailDTOList;
    }

    public void setAcctTransResultPegDetailDTOList(
            List<AcctTransResultPegDetailDTO> acctTransResultPegDetailDTOList) {
        this.acctTransResultPegDetailDTOList = acctTransResultPegDetailDTOList;
    }

    public boolean isSuccess() {
        return (ACCT_RESPONSE_SUCCESS.equals(getRespCode()) || ACCT_DUPLICATE_AND_RESPONSE_SUCCESS.equals(getRespCode()));
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AcctTransResultPeg{");
        sb.append("respCode='").append(respCode).append('\'');
        sb.append(", respMessage='").append(respMessage).append('\'');
        sb.append(", versionId='").append(versionId).append('\'');
        sb.append(", sysId='").append(sysId).append('\'');
        sb.append(", reqSeqId='").append(reqSeqId).append('\'');
        sb.append(", transCnt='").append(transCnt).append('\'');
        sb.append(", acctUnfreezeResultDetailDTOS=").append(acctUnfreezeResultDetailDTOS);
        sb.append(", privateFields='").append(privateFields).append('\'');
        sb.append(", acctTransResultPegDetailDTOList=").append(acctTransResultPegDetailDTOList);
        sb.append('}');
        return sb.toString();
    }

    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }

    public List<AcctUnfreezeResultDetailDTO> getAcctUnfreezeResultDetailDTOS() {
        return acctUnfreezeResultDetailDTOS;
    }

    public void setAcctUnfreezeResultDetailDTOS(List<AcctUnfreezeResultDetailDTO> acctUnfreezeResultDetailDTOS) {
        this.acctUnfreezeResultDetailDTOS = acctUnfreezeResultDetailDTOS;
    }
}
