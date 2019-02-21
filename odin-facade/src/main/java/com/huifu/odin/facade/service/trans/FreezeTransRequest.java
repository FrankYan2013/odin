package com.huifu.odin.facade.service.trans;

/**
 * @author frank
 */
public class FreezeTransRequest {

    private String versionId;

    private String reqSeqId;

    private String frtDate;

    private String frtSeqId;

    private String custId;

    private String subAcctId;

    private String acctType;

    private String transAmt;

    private String bedpId;

    private String transName;

    private String transObj;

    private String frzCode;

    private String sysId;

    public String getReqSeqId() {
        return reqSeqId;
    }

    public void setReqSeqId(String reqSeqId) {
        this.reqSeqId = reqSeqId;
    }

    public String getFrtDate() {
        return frtDate;
    }

    public void setFrtDate(String frtDate) {
        this.frtDate = frtDate;
    }

    public String getFrtSeqId() {
        return frtSeqId;
    }

    public void setFrtSeqId(String frtSeqId) {
        this.frtSeqId = frtSeqId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getSubAcctId() {
        return subAcctId;
    }

    public void setSubAcctId(String subAcctId) {
        this.subAcctId = subAcctId;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }

    public String getBedpId() {
        return bedpId;
    }

    public void setBedpId(String bedpId) {
        this.bedpId = bedpId;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public String getTransObj() {
        return transObj;
    }

    public void setTransObj(String transObj) {
        this.transObj = transObj;
    }

    public String getFrzCode() {
        return frzCode;
    }

    public void setFrzCode(String frzCode) {
        this.frzCode = frzCode;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FreezeTransRequest{");
        sb.append("versionId='").append(versionId).append('\'');
        sb.append(", reqSeqId='").append(reqSeqId).append('\'');
        sb.append(", frtDate='").append(frtDate).append('\'');
        sb.append(", frtSeqId='").append(frtSeqId).append('\'');
        sb.append(", custId='").append(custId).append('\'');
        sb.append(", subAcctId='").append(subAcctId).append('\'');
        sb.append(", acctType='").append(acctType).append('\'');
        sb.append(", transAmt='").append(transAmt).append('\'');
        sb.append(", bedpId='").append(bedpId).append('\'');
        sb.append(", transName='").append(transName).append('\'');
        sb.append(", transObj='").append(transObj).append('\'');
        sb.append(", frzCode='").append(frzCode).append('\'');
        sb.append(", sysId='").append(sysId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
