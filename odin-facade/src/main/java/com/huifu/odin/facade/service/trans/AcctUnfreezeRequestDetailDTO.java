package com.huifu.odin.facade.service.trans;

import java.io.Serializable;

/**
 * @author frank
 */
public class AcctUnfreezeRequestDetailDTO implements Serializable {

    private static final long serialVersionUID = -1253101977285113585L;

    private String frtDate;
    private String frtSeqId;
    private String custId;
    private String subAcctId;
    private String acctType;
    private String transAmt;
    private String bedpId;
    private String transName;
    private String transObj;
    private String frtTxnId1;
    private String frtTxnId2;
    private String frtTxnId3;
    private String merId;
    private String frzCode;
    private String frozenAcctSeqId;
    private String frozenAcctDate;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AcctUnfreezeRequestDetailDTO{");
        sb.append("frtDate='").append(frtDate).append('\'');
        sb.append(", frtSeqId='").append(frtSeqId).append('\'');
        sb.append(", custId='").append(custId).append('\'');
        sb.append(", subAcctId='").append(subAcctId).append('\'');
        sb.append(", acctType='").append(acctType).append('\'');
        sb.append(", transAmt='").append(transAmt).append('\'');
        sb.append(", bedpId='").append(bedpId).append('\'');
        sb.append(", transName='").append(transName).append('\'');
        sb.append(", transObj='").append(transObj).append('\'');
        sb.append(", frtTxnId1='").append(frtTxnId1).append('\'');
        sb.append(", frtTxnId2='").append(frtTxnId2).append('\'');
        sb.append(", frtTxnId3='").append(frtTxnId3).append('\'');
        sb.append(", merId='").append(merId).append('\'');
        sb.append(", frzCode='").append(frzCode).append('\'');
        sb.append(", frozenAcctSeqId='").append(frozenAcctSeqId).append('\'');
        sb.append(", frozenAcctDate='").append(frozenAcctDate).append('\'');
        sb.append('}');
        return sb.toString();
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

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getFrzCode() {
        return frzCode;
    }

    public void setFrzCode(String frzCode) {
        this.frzCode = frzCode;
    }

    public String getFrtDate() {
        return frtDate;
    }

    public void setFrtDate(String frtDate) {
        this.frtDate = frtDate;
    }
}
