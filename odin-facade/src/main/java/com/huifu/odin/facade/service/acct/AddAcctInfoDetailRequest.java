/**
 * 汇付天下有限公司
 * <p>
 * Copyright (c) 2006-2012 ChinaPnR,Inc.All Rights Reserved.
 */
package com.huifu.odin.facade.service.acct;

import java.io.Serializable;

/**
 * @author frank
 */
public class AddAcctInfoDetailRequest implements Serializable {


    private static final long serialVersionUID = -5243408336948786237L;

    private String custId;
    private String subAcctId;
    private String capType;
    private String curyType;
    private String acctType;
    private String acctName;
    private String custInfo;
    private String sysId;
    private String bdepId;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AddAcctInfoDetailRequest{");
        sb.append("custId='").append(custId).append('\'');
        sb.append(", subAcctId='").append(subAcctId).append('\'');
        sb.append(", capType='").append(capType).append('\'');
        sb.append(", curyType='").append(curyType).append('\'');
        sb.append(", acctType='").append(acctType).append('\'');
        sb.append(", acctName='").append(acctName).append('\'');
        sb.append(", custInfo='").append(custInfo).append('\'');
        sb.append(", sysId='").append(sysId).append('\'');
        sb.append(", bdepId='").append(bdepId).append('\'');
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

    public String getCustInfo() {
        return custInfo;
    }

    public void setCustInfo(String custInfo) {
        this.custInfo = custInfo;
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

}
