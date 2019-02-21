package com.huifu.odin.biz.freeze;

/**
 * @author frank
 */
public class FreezeTransResponse {

    private String returnCode;

    private String returnDesc;

    private String transAmt;

    private String acctDate;

    private String acctSeqId;

    private String sysId;

    private String versionId;

    private String reqSeqId;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnDesc() {
        return returnDesc;
    }

    public void setReturnDesc(String returnDesc) {
        this.returnDesc = returnDesc;
    }

    public String getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }

    public String getAcctDate() {
        return acctDate;
    }

    public void setAcctDate(String acctDate) {
        this.acctDate = acctDate;
    }

    public String getAcctSeqId() {
        return acctSeqId;
    }

    public void setAcctSeqId(String acctSeqId) {
        this.acctSeqId = acctSeqId;
    }

    public String getSysId() {
        return sysId;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FreezeTransResponse{");
        sb.append("returnCode='").append(returnCode).append('\'');
        sb.append(", returnDesc='").append(returnDesc).append('\'');
        sb.append(", transAmt='").append(transAmt).append('\'');
        sb.append(", acctDate='").append(acctDate).append('\'');
        sb.append(", acctSeqId='").append(acctSeqId).append('\'');
        sb.append(", sysId='").append(sysId).append('\'');
        sb.append(", versionId='").append(versionId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

}
