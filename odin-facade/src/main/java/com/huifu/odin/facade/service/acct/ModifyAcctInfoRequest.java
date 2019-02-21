package com.huifu.odin.facade.service.acct;

import java.io.Serializable;

/**
 * @author frank
 */
public class ModifyAcctInfoRequest implements Serializable {


    private static final long serialVersionUID = -2881515568172113990L;
    private String custId;

    private String subAcctId;

    private String acctStatus;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ModifyAcctInfoRequest{");
        sb.append("custId='").append(custId).append('\'');
        sb.append(", subAcctId='").append(subAcctId).append('\'');
        sb.append(", acctStatus='").append(acctStatus).append('\'');
        sb.append('}');
        return sb.toString();
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

    public String getAcctStatus() {
        return acctStatus;
    }

    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus;
    }

}
