package com.huifu.odin.biz.trans;

/**
 * @author frank
 */

public enum TransExceptionEnum {

    /**
     * 账户状态异常信息描述
     */
    TransAcctInfoStatError("201", "账户状态异常"),

    /**
     * 重复交易异常信息描述
     */
    DuplicateKeyException("001", "重复交易"),

    /**
     * 交易类型不存在异常描述
     */
    TransTypeException("091", "交易类型不存在"),

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
     * 客户管理系统限制交易
     */
    SystemCustMagLimit("097", "客户管理系统限制交易"),


    /**
     * 数据排序异常
     */
    SystemSortError("098", "数据排序异常"),

    /**
     * 账户不存在,余额更新失败
     */
    AcctIsNull("200", "账户不存在"),

    /**
     * 账户余额不足
     */
    InsufficientBal("203", "账户余额不足"),

    /**
     * 系统离线状态，正在进行日终批处理
     */
    AcctOffLine("2", "系统离线状态，正在进行日终批处理"),

    /**
     * 计算交易扣后余额异常
     */
    CALCULATE_ACCT_BAL_ERROR("081", "计算交易扣后余额异常");


    /**
     * @param returnCode
     * @param returnDesc
     */
    TransExceptionEnum(String returnCode, String returnDesc) {
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
