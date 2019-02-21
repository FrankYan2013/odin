package com.huifu.odin.facade.service.trans;

import java.io.Serializable;

/**
 * @author frank
 */
public class AcctUnfreezeResultDetailDTO implements Serializable {

    private static final long serialVersionUID = -1253101977285113585L;

    private String frtDate;
    private String frtSeqId;
    private String custId;
    private String subAcctId;
    private String transAmt;
    private String acctDate;
    private String acctSeqId;
    private String acctBal;
    private String acctStatus;
    private String acctDateTime;
    private String frzCode;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AcctUnfreezeResultDetailDTO{");
        sb.append("frtDate='").append(frtDate).append('\'');
        sb.append(", frtSeqId='").append(frtSeqId).append('\'');
        sb.append(", custId='").append(custId).append('\'');
        sb.append(", subAcctId='").append(subAcctId).append('\'');
        sb.append(", transAmt='").append(transAmt).append('\'');
        sb.append(", acctDate='").append(acctDate).append('\'');
        sb.append(", acctSeqId='").append(acctSeqId).append('\'');
        sb.append(", acctBal='").append(acctBal).append('\'');
        sb.append(", acctStatus='").append(acctStatus).append('\'');
        sb.append(", acctDateTime='").append(acctDateTime).append('\'');
        sb.append(", frzCode='").append(frzCode).append('\'');
        sb.append('}');
        return sb.toString();
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

    public String getAcctBal() {
        return acctBal;
    }

    public void setAcctBal(String acctBal) {
        this.acctBal = acctBal;
    }

    public String getAcctStatus() {
        return acctStatus;
    }

    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus;
    }

    public String getAcctDateTime() {
        return acctDateTime;
    }

    public void setAcctDateTime(String acctDateTime) {
        this.acctDateTime = acctDateTime;
    }

    public String getFrzCode() {
        return frzCode;
    }

    public void setFrzCode(String frzCode) {
        this.frzCode = frzCode;
    }
}
