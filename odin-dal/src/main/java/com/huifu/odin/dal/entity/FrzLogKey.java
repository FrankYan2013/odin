package com.huifu.odin.dal.entity;

public class FrzLogKey {
    private String acctDate;

    private String acctSeqId;

    public String getAcctDate() {
        return acctDate;
    }

    public void setAcctDate(String acctDate) {
        this.acctDate = acctDate == null ? null : acctDate.trim();
    }

    public String getAcctSeqId() {
        return acctSeqId;
    }

    public void setAcctSeqId(String acctSeqId) {
        this.acctSeqId = acctSeqId == null ? null : acctSeqId.trim();
    }
}