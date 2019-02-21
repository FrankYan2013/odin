package com.huifu.odin.biz.trans;

/**
 * @author frank
 */

public enum TransEnum {


    /**
     * 交易成功
     */
    Success("000", "交易成功"),
    /**
     * 交易成功
     */
    UnFreezeSuccess("000", "解冻成功"),
    /**
     * 交易成功
     */
    FreezeSuccess("000", "冻结成功"),
    /**
     * 交易成功
     */
    Dup_Success("001", "重复交易，交易成功"),
    /**
     * 账户状态可用
     */
    AcctInfoN("N", "账户状态可用"),
    /**
     * 账户状态被关闭，不能出，不能入
     */
    AcctInfoC("C", "账户已关闭"),
    /**
     * 账户状态已冻结，不能出账，可以入账
     */
    AcctInfoF("F", "账户已冻结"),
    /**
     * 账户状态销户，不能出，不能入
     */
    AcctInfoD("D", "账户状态销户"),
    /**
     * 账户空
     */
    AcctInfoNull("D", "账户状态为空"),
    /**
     * TransTypeD出款
     */
    TransTypeD("D", "TransTypeD"),
    /**
     * TransTypeC人款
     */
    TransTypeC("C", "TransTypeC"),;

    /**
     * @param returnCode
     * @param returnDesc
     */
    TransEnum(String returnCode, String returnDesc) {
        this.setReturnCode(returnCode);
        this.setReturnDesc(returnDesc);
    }

    /**
     * 返回码
     */
    private String returnCode;

    /**
     * 返回码说明
     */
    private String returnDesc;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnDesc() {
        return returnDesc;
    }

    public void setReturnDesc(String returnDesc) {
        this.returnDesc = returnDesc;
    }
}
