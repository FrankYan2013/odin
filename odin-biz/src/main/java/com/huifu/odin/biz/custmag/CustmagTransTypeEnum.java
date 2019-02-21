package com.huifu.odin.biz.custmag;


/**
 * @author frank
 */

public enum CustmagTransTypeEnum {

    // 2007
    TRANS_TYPE_2007("2007", "1004", "转账转出"),
    TRANS_TYPE_2002("2002", "1001", "账户支付"),
    TRANS_TYPE_2001("2001", "1002", "账户取现"),;

    /**
     * @param acctTransType
     * @param custmagTradeType
     * @param custmagTradeDesc
     */

    private CustmagTransTypeEnum(String acctTransType, String custmagTradeType, String custmagTradeDesc) {
        this.setAcctTransType(acctTransType);
        this.setCustmagTradeType(custmagTradeType);
        this.setCustmagTradeDesc(custmagTradeDesc);
    }

    public static CustmagTransTypeEnum valueByCode(String acctTransType) {
        for (CustmagTransTypeEnum enu : CustmagTransTypeEnum.values()) {
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
    private String custmagTradeType;

    /**
     * custmag交易类型描述
     */
    private String custmagTradeDesc;

    public String getAcctTransType() {
        return acctTransType;
    }

    public void setAcctTransType(String acctTransType) {
        this.acctTransType = acctTransType;
    }

    public String getCustmagTradeType() {
        return custmagTradeType;
    }

    public void setCustmagTradeType(String custmagTradeType) {
        this.custmagTradeType = custmagTradeType;
    }

    public String getCustmagTradeDesc() {
        return custmagTradeDesc;
    }

    public void setCustmagTradeDesc(String custmagTradeDesc) {
        this.custmagTradeDesc = custmagTradeDesc;
    }

}
