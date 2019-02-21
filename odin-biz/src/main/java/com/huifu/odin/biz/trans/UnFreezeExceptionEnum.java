package com.huifu.odin.biz.trans;

/**
 * @author frank
 */

public enum UnFreezeExceptionEnum {

    /**
     * 解冻验证失败，custId不一致
     */
    UNFREEZE_TRANS_VAL_CUST_ERROR("850", "解冻验证失败，custId不一致"),
    /**
     * 解冻验证失败，acctId不一致
     */
    UNFREEZE_TRANS_VAL_ACCTID_ERROR("851", "解冻验证失败，acctId不一致"),
    /**
     * 解冻验证失败，金额不一致
     */
    UNFREEZE_TRANS_VAL_AMT_ERROR("853", "解冻验证失败，金额不一致"),

    /**
     * 解冻余额更新失败
     */
    UNFREEZE_TRANS_AVLBAL_ERROR("804", "解冻余额更新失败"),
    /**
     * 原始交易已解冻
     */
    UNFREEZE_TRANS_ALREADY_UNFROZEN("803", "原始交易已解冻"),
    /**
     * 解冻交易入库失败
     */
    UNFREEZE_TRANS_INSERT_ERROR("802", "解冻交易入库失败"),
    /**
     * 解冻原始交易不存在
     */
    FREEZE_TRANS_NOT_EXSIT("801", "解冻原始交易不存在"),
    /**
     * 系统异常信息描述
     */
    SystemError("099", "系统异常"),
    /**
     * 数据库异常
     */
    Db_Error("098", "数据库异常"),

    /**
     * 重复交易异常信息描述
     */
    DuplicateKeyException("001", "重复交易");

    /**
     * @param returnCode
     * @param returnDesc
     */
    UnFreezeExceptionEnum(String returnCode, String returnDesc) {
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
