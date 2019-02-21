package com.huifu.odin.facade.service.trans;

import java.io.Serializable;

/**
 * @author frank
 */
public class FreezeTransResult implements Serializable {

    private static final long serialVersionUID = 623244029941643719L;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FreezeTransResult{");
        sb.append("respCode='").append(respCode).append('\'');
        sb.append(", respMessage='").append(respMessage).append('\'');
        sb.append(", sysId='").append(sysId).append('\'');
        sb.append(", versionId='").append(versionId).append('\'');
        sb.append(", reqSeqId='").append(reqSeqId).append('\'');
        sb.append(", frozenAcctSeqId='").append(frozenAcctSeqId).append('\'');
        sb.append(", frozenAcctDate='").append(frozenAcctDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    private String respCode;
    private String respMessage;
    private String sysId;
    private String versionId;
    private String reqSeqId;
    private String frozenAcctSeqId;
    private String frozenAcctDate;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getReqSeqId() {
        return reqSeqId;
    }

    public void setReqSeqId(String reqSeqId) {
        this.reqSeqId = reqSeqId;
    }

    public String getFrozenAcctSeqId() {
        return frozenAcctSeqId;
    }

    public void setFrozenAcctSeqId(String frozenAcctSeqId) {
        this.frozenAcctSeqId = frozenAcctSeqId;
    }

    public String getFrozenAcctDate() {
        return frozenAcctDate;
    }

    public void setFrozenAcctDate(String frozenAcctDate) {
        this.frozenAcctDate = frozenAcctDate;
    }
}
