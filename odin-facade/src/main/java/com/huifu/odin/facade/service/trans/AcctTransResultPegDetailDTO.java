package com.huifu.odin.facade.service.trans;

import java.io.Serializable;

/**
 * @author frank
 */
public class AcctTransResultPegDetailDTO implements Serializable {

    private static final long serialVersionUID = 604576704995795966L;

    private String frtDate;
    private String frtSeqId;
    private String transType;
    private String custId;
    private String subAcctId;
    private String transAmt;
    private String feeAmt;
    private String acctDate;
    private String acctSeqId;
    private String acctBal;
    private String acctStatus;
    private String acctDateTime;
    private String dcFlag;

    public String getPrivateFields() {
        return privateFields;
    }

    public void setPrivateFields(String privateFields) {
        this.privateFields = privateFields;
    }

    private String privateFields;


    public AcctTransResultPegDetailDTO() {
    }

    public String getFrtSeqId() {
        return this.frtSeqId;
    }

    public String getTransType() {
        return this.transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public void setFrtSeqId(String frtSeqId) {
        this.frtSeqId = frtSeqId;
    }

    public String getCustId() {
        return this.custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getSubAcctId() {
        return this.subAcctId;
    }

    public void setSubAcctId(String subAcctId) {
        this.subAcctId = subAcctId;
    }

    public String getTransAmt() {
        return this.transAmt;
    }

    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }

    public String getAcctDate() {
        return this.acctDate;
    }

    public void setAcctDate(String acctDate) {
        this.acctDate = acctDate;
    }

    public String getAcctSeqId() {
        return this.acctSeqId;
    }

    public void setAcctSeqId(String acctSeqId) {
        this.acctSeqId = acctSeqId;
    }

    public String getAcctBal() {
        return this.acctBal;
    }

    public void setAcctBal(String acctBal) {
        this.acctBal = acctBal;
    }

    public void setFrtDate(String frtDate) {
        this.frtDate = frtDate;
    }

    public String getFrtDate() {
        return this.frtDate;
    }

    public String getAcctStatus() {
        return acctStatus;
    }

    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AcctTransResultPegDetailDTO{");
        sb.append("frtDate='").append(frtDate).append('\'');
        sb.append(", frtSeqId='").append(frtSeqId).append('\'');
        sb.append(", transType='").append(transType).append('\'');
        sb.append(", custId='").append(custId).append('\'');
        sb.append(", subAcctId='").append(subAcctId).append('\'');
        sb.append(", transAmt='").append(transAmt).append('\'');
        sb.append(", feeAmt='").append(feeAmt).append('\'');
        sb.append(", acctDate='").append(acctDate).append('\'');
        sb.append(", acctSeqId='").append(acctSeqId).append('\'');
        sb.append(", acctBal='").append(acctBal).append('\'');
        sb.append(", acctStatus='").append(acctStatus).append('\'');
        sb.append(", acctDateTime='").append(acctDateTime).append('\'');
        sb.append(", dcFlag='").append(dcFlag).append('\'');
        sb.append(", privateFields='").append(privateFields).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getAcctDateTime() {
        return acctDateTime;
    }

    public void setAcctDateTime(String acctDateTime) {
        this.acctDateTime = acctDateTime;
    }

    public String getDcFlag() {
        return dcFlag;
    }

    public void setDcFlag(String dcFlag) {
        this.dcFlag = dcFlag;
    }

    public String getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(String feeAmt) {
        this.feeAmt = feeAmt;
    }
}
