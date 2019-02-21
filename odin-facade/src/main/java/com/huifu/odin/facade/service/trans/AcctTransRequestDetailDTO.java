package com.huifu.odin.facade.service.trans;

import java.io.Serializable;

/**
 * @author frank
 */
public class AcctTransRequestDetailDTO implements Serializable {

    private static final long serialVersionUID = -2008949109602434117L;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AcctTransRequestDetailDTO{");
        sb.append("frtDate='").append(frtDate).append('\'');
        sb.append(", frtSeqId='").append(frtSeqId).append('\'');
        sb.append(", transType='").append(transType).append('\'');
        sb.append(", custId='").append(custId).append('\'');
        sb.append(", subAcctId='").append(subAcctId).append('\'');
        sb.append(", acctType='").append(acctType).append('\'');
        sb.append(", transAmt='").append(transAmt).append('\'');
        sb.append(", feeAmount='").append(feeAmount).append('\'');
        sb.append(", custId2='").append(custId2).append('\'');
        sb.append(", bedpId='").append(bedpId).append('\'');
        sb.append(", transName='").append(transName).append('\'');
        sb.append(", transObj='").append(transObj).append('\'');
        sb.append(", frtTxnId1='").append(frtTxnId1).append('\'');
        sb.append(", frtTxnId2='").append(frtTxnId2).append('\'');
        sb.append(", frtTxnId3='").append(frtTxnId3).append('\'');
        sb.append(", inAcctId='").append(inAcctId).append('\'');
        sb.append(", merId='").append(merId).append('\'');
        sb.append(", isPayAcct=").append(isPayAcct);
        sb.append(", privateFields='").append(privateFields).append('\'');
        sb.append('}');
        return sb.toString();
    }

    private String frtDate;
    private String frtSeqId;
    private String transType;
    private String custId;
    private String subAcctId;
    private String acctType;
    private String transAmt;
    private String feeAmount;
    private String custId2;
    private String bedpId;
    private String transName;
    private String transObj;
    private String frtTxnId1;
    private String frtTxnId2;
    private String frtTxnId3;
    private String inAcctId;
    private String merId;
    private boolean isPayAcct;
    private String dcFlag;

    public String getPrivateFields() {
        return privateFields;
    }

    public void setPrivateFields(String privateFields) {
        this.privateFields = privateFields;
    }

    private String privateFields;


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

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
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

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getCustId2() {
        return custId2;
    }

    public void setCustId2(String custId2) {
        this.custId2 = custId2;
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

    public String getFrtTxnId1() {
        return frtTxnId1;
    }

    public void setFrtTxnId1(String frtTxnId1) {
        this.frtTxnId1 = frtTxnId1;
    }

    public String getFrtTxnId2() {
        return frtTxnId2;
    }

    public void setFrtTxnId2(String frtTxnId2) {
        this.frtTxnId2 = frtTxnId2;
    }

    public String getFrtTxnId3() {
        return frtTxnId3;
    }

    public void setFrtTxnId3(String frtTxnId3) {
        this.frtTxnId3 = frtTxnId3;
    }

    public String getInAcctId() {
        return inAcctId;
    }

    public void setInAcctId(String inAcctId) {
        this.inAcctId = inAcctId;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public boolean isPayAcct() {
        return isPayAcct;
    }

    public void setPayAcct(boolean isPayAcct) {
        this.isPayAcct = isPayAcct;
    }

    public String getDcFlag() {
        return dcFlag;
    }

    public void setDcFlag(String dcFlag) {
        this.dcFlag = dcFlag;
    }
}
