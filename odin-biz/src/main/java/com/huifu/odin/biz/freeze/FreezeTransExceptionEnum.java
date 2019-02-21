package com.huifu.odin.biz.freeze;

/**
 * @author frank
 */

public enum FreezeTransExceptionEnum {

    /**
     * 账户状态异常信息描述
     */
    TransAcctInfoStatError("201", "账户状态异常"),

    /**
     * 重复交易异常信息描述
     */
    DuplicateKeyException("001", "重复交易"),

    /**
     * 请求数据校验失败
     */
    TransReqValdationFail("062", "请求数据校验失败"),
    /**
     * 数据库异常
     */
    Db_Error("098", "数据库异常"),


    /**
     * 系统异常信息描述
     */
    SystemError("099", "系统异常"),

    /**
     * 账户不存在,余额更新失败
     */
    AcctIsNull("200", "账户不存在"),

    /**
     * 账户余额不足
     */
    InsufficientBal("203", "账户余额不足");

    /**
     * @param returnCode
     * @param returnDesc
     */
    FreezeTransExceptionEnum(String returnCode, String returnDesc) {
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
