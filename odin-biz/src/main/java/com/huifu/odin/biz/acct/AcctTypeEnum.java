package com.huifu.odin.biz.acct;

/**
 * @author frank
 */

public enum AcctTypeEnum {
    // 基本户
    BASEDT("BASEDT", "基本借记户", "BASEDT"),
    SLB("SLB","生利宝余额理财户","SLB"),
    // 收款户
    RECV("RECV", "收款户", "RC"),
    PAY("PAY", "付款户", "PAY"),
    // 商户专用借记账户
    MERDT("MERDT", "商户专用借记账户", "MDT"),
    FUND("FUND", "基金户", "FUND"),
    BUY("BUY", "申购户", "BUY"),
    RED("RED", "赎回户", "RED"),
    FAS("FAS", "基金代销户", "FAS"),
    INT("INT", "内部账户", "INT"),
    FC("FC", "外币账户", "FC"),
    TG("TG", "托管账户", "TG"),
    FS("FS", "基金份额账户", "FS"),
    PT("PT", "积分账户", "PT"),
    // 专用借记账户
    SPEDT("SPEDT", "专用借记账户", "SDT"),
    DEP("DEP", "保证金账户", "DEP");

    private String code;
    private String desc;
    private String subAcctHeader;

    private AcctTypeEnum(String code, String desc, String subAcctHeader) {
        this.code = code;
        this.desc = desc;
        this.subAcctHeader = subAcctHeader;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getSubAcctHeader() {
        return this.subAcctHeader;
    }

    public static AcctTypeEnum valueByCode(String code) {
        AcctTypeEnum[] arr = values();
        int len = arr.length;

        for(int i = 0; i < len; ++i) {
            AcctTypeEnum accountType = arr[i];
            if (accountType.getCode().equals(code)) {
                return accountType;
            }
        }

        return null;
    }
}