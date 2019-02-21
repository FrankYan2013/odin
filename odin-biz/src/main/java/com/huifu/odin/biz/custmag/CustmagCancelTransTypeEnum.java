package com.huifu.odin.biz.custmag;

/**
 * @author frank
 */

public enum CustmagCancelTransTypeEnum {

    // 2007
    TRANS_TYPE_2007("2007", "2004", "转账转出失败"),
    // 2002
    TRANS_TYPE_2002("2002", "2001", "账户出款失败"),
    // 2001
    TRANS_TYPE_2001("2001", "2001", "账户取现");

    /**
     * @param acctTransType
     * @param custmagCancelTradeType
     * @param custmagCancelTradeDesc
     */

    CustmagCancelTransTypeEnum(String acctTransType, String custmagCancelTradeType, String custmagCancelTradeDesc) {
        this.setAcctTransType(acctTransType);
        this.setCustmagCancelTradeType(custmagCancelTradeType);
        this.setCustmagCancelTradeDesc(custmagCancelTradeDesc);
    }

    public static CustmagCancelTransTypeEnum valueByCode(String acctTransType) {
        for (CustmagCancelTransTypeEnum enu : CustmagCancelTransTypeEnum.values()) {
            if (enu.getAcctTransType().equals(acctTransType)) {
                return enu;
            }
        }
        return null;
    }

    /**
     * acct交易类型
     */
    private String acctTransType;

    /**
     * custmag交易类型
     */
    private String custmagCancelTradeType;

    /**
     * custmag交易类型描述
     */
    private String custmagCancelTradeDesc;

    public String getAcctTransType() {
        return acctTransType;
    }

    public void setAcctTransType(String acctTransType) {
        this.acctTransType = acctTransType;
    }

    public String getCustmagCancelTradeType() {
        return custmagCancelTradeType;
    }

    public void setCustmagCancelTradeType(String custmagCancelTradeType) {
        this.custmagCancelTradeType = custmagCancelTradeType;
    }

    public String getCustmagCancelTradeDesc() {
        return custmagCancelTradeDesc;
    }

    public void setCustmagCancelTradeDesc(String custmagCancelTradeDesc) {
        this.custmagCancelTradeDesc = custmagCancelTradeDesc;
    }


}
