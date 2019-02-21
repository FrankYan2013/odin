package com.huifu.odin.integration.custmag.impl;

import org.apache.commons.lang.StringUtils;

/**
 * @author frank
 */

public enum CustmagLimitCodeEnum {

    // 000
    CUSTMAG_CODE_000("000", "000", "成功"),
    CUSTMAG_CODE_001("001", "C001", "参数验证错误"),
    CUSTMAG_CODE_002("002", "C002", "该账户不存在"),
    CUSTMAG_CODE_101("101", "C101", "交易权限不足"),
    CUSTMAG_CODE_103("102", "C102", "交易限额不足"),
    CUSTMAG_CODE_901("901", "C901", "该客户不存在"),
    CUSTMAG_CODE_999("999", "C999", "客户管理系统错误"),;

    /**
     * @param custmagCode
     * @param acctTradeLimtCode
     * @param tradeLimtDesc
     */

    CustmagLimitCodeEnum(String custmagCode, String acctTradeLimtCode, String tradeLimtDesc) {
        this.setCustmagCode(custmagCode);
        this.setAcctTradeLimtCode(acctTradeLimtCode);
        this.setTradeLimtDesc(tradeLimtDesc);
    }

    public static CustmagLimitCodeEnum valueByCode(String custmagCode) {
        for (CustmagLimitCodeEnum enu : CustmagLimitCodeEnum.values()) {
            if (enu.getCustmagCode().equals(custmagCode)) {
                return enu;
            }
        }
        return null;
    }

    public static boolean transactionPermited(String custmagCode) {
        if (StringUtils.equals(custmagCode, CUSTMAG_CODE_000.getCustmagCode())
                || StringUtils.equals(custmagCode, CUSTMAG_CODE_901.getCustmagCode())
                || StringUtils.equals(custmagCode, CUSTMAG_CODE_999.getCustmagCode())) {
            return true;
        }
        return false;
    }

    /**
     * custmag返回码
     */
    private String custmagCode;

    /**
     * acctTradeLimtCode返回码
     */
    private String acctTradeLimtCode;

    /**
     * custmag返回描述
     */
    private String tradeLimtDesc;

    public String getCustmagCode() {
        return custmagCode;
    }

    public void setCustmagCode(String custmagCode) {
        this.custmagCode = custmagCode;
    }

    public String getAcctTradeLimtCode() {
        return acctTradeLimtCode;
    }

    public void setAcctTradeLimtCode(String acctTradeLimtCode) {
        this.acctTradeLimtCode = acctTradeLimtCode;
    }

    public String getTradeLimtDesc() {
        return tradeLimtDesc;
    }

    public void setTradeLimtDesc(String tradeLimtDesc) {
        this.tradeLimtDesc = tradeLimtDesc;
    }

}
