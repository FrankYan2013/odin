package com.huifu.odin.facade.service.trans;

import java.io.Serializable;

/**
 * @author frank
 */
public class UnfreezeTransResult implements Serializable {

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UnfreezeTransResult{");
        sb.append("respCode='").append(respCode).append('\'');
        sb.append(", respMessage='").append(respMessage).append('\'');
        sb.append(", versionId='").append(versionId).append('\'');
        sb.append(", sysId='").append(sysId).append('\'');
        sb.append(", reqSeqId='").append(reqSeqId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    private static final String ACCT_RESPONSE_SUCCESS = "000";

    private static final String ACCT_DUPLICATE_AND_RESPONSE_SUCCESS = "001";
    private static final long serialVersionUID = -6986474390945322862L;

    private String respCode;
    private String respMessage;
    private String versionId;
    private String sysId;
    private String reqSeqId;

    public UnfreezeTransResult() {
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

    public boolean isSuccess() {
        return (ACCT_RESPONSE_SUCCESS.equals(getRespCode()) || ACCT_DUPLICATE_AND_RESPONSE_SUCCESS.equals(getRespCode()));
    }

    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }

}
