package com.huifu.odin.facade.service.acct;

import java.io.Serializable;

/**
 * @author frank
 */
public class AddAcctInfoDetailResult implements Serializable {

    private static final long serialVersionUID = -139741957687636283L;

    private String custId;
    private String subAcctId;
    private String acctType;
    private String acctName;
    private String capType;
    private String curyType;
    private String custInfo;
    private String acctStatus;
    private String sysId;
    private String bdepId;
    private String openDate;
    private String openTime;

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

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getCapType() {
        return capType;
    }

    public void setCapType(String capType) {
        this.capType = capType;
    }

    public String getCuryType() {
        return curyType;
    }

    public void setCuryType(String curyType) {
        this.curyType = curyType;
    }

    public String getCustInfo() {
        return custInfo;
    }

    public void setCustInfo(String custInfo) {
        this.custInfo = custInfo;
    }

    public String getAcctStatus() {
        return acctStatus;
    }

    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getBdepId() {
        return bdepId;
    }

    public void setBdepId(String bdepId) {
        this.bdepId = bdepId;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AddAcctInfoDetailResult{");
        sb.append("custId='").append(custId).append('\'');
        sb.append(", subAcctId='").append(subAcctId).append('\'');
        sb.append(", acctType='").append(acctType).append('\'');
        sb.append(", acctName='").append(acctName).append('\'');
        sb.append(", capType='").append(capType).append('\'');
        sb.append(", curyType='").append(curyType).append('\'');
        sb.append(", custInfo='").append(custInfo).append('\'');
        sb.append(", acctStatus='").append(acctStatus).append('\'');
        sb.append(", sysId='").append(sysId).append('\'');
        sb.append(", bdepId='").append(bdepId).append('\'');
        sb.append(", openDate='").append(openDate).append('\'');
        sb.append(", openTime='").append(openTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
