package com.huifu.odin.dal.entity;

public class FrzLog extends FrzLogKey {
    private String custId;

    private String subAcctId;

    private String transType;

    private String frzStat;

    private String transAmt;

    private String frzCode;

    private String sysTime;

    private String unfrzDate;

    private String unfrzSeq;

    private String transName;

    private String transObj;

    private String frtTxnId1;

    private String frtTxnId2;

    private String frtTxnId3;

    private String sysId;

    private String frtDate;

    private String frtSeqId;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getSubAcctId() {
        return subAcctId;
    }

    public void setSubAcctId(String subAcctId) {
        this.subAcctId = subAcctId == null ? null : subAcctId.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public String getFrzStat() {
        return frzStat;
    }

    public void setFrzStat(String frzStat) {
        this.frzStat = frzStat == null ? null : frzStat.trim();
    }

    public String getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt == null ? null : transAmt.trim();
    }

    public String getFrzCode() {
        return frzCode;
    }

    public void setFrzCode(String frzCode) {
        this.frzCode = frzCode == null ? null : frzCode.trim();
    }

    public String getSysTime() {
        return sysTime;
    }

    public void setSysTime(String sysTime) {
        this.sysTime = sysTime == null ? null : sysTime.trim();
    }

    public String getUnfrzDate() {
        return unfrzDate;
    }

    public void setUnfrzDate(String unfrzDate) {
        this.unfrzDate = unfrzDate == null ? null : unfrzDate.trim();
    }

    public String getUnfrzSeq() {
        return unfrzSeq;
    }

    public void setUnfrzSeq(String unfrzSeq) {
        this.unfrzSeq = unfrzSeq == null ? null : unfrzSeq.trim();
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName == null ? null : transName.trim();
    }

    public String getTransObj() {
        return transObj;
    }

    public void setTransObj(String transObj) {
        this.transObj = transObj == null ? null : transObj.trim();
    }

    public String getFrtTxnId1() {
        return frtTxnId1;
    }

    public void setFrtTxnId1(String frtTxnId1) {
        this.frtTxnId1 = frtTxnId1 == null ? null : frtTxnId1.trim();
    }

    public String getFrtTxnId2() {
        return frtTxnId2;
    }

    public void setFrtTxnId2(String frtTxnId2) {
        this.frtTxnId2 = frtTxnId2 == null ? null : frtTxnId2.trim();
    }

    public String getFrtTxnId3() {
        return frtTxnId3;
    }

    public void setFrtTxnId3(String frtTxnId3) {
        this.frtTxnId3 = frtTxnId3 == null ? null : frtTxnId3.trim();
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId == null ? null : sysId.trim();
    }

    public String getFrtDate() {
        return frtDate;
    }

    public void setFrtDate(String frtDate) {
        this.frtDate = frtDate == null ? null : frtDate.trim();
    }

    public String getFrtSeqId() {
        return frtSeqId;
    }

    public void setFrtSeqId(String frtSeqId) {
        this.frtSeqId = frtSeqId == null ? null : frtSeqId.trim();
    }
}