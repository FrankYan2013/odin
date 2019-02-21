package com.huifu.odin.biz.freeze;

/**
 * @author frank
 */

public enum FreezeTransEnum {


    /**
     * 交易成功
     */
    FREEZE_SUCCESS("000", "冻结成功"),

    /**
     * 交易成功
     */
    SystemError("099", "系统异常");

    /**
     * @param returnCode
     * @param returnDesc
     */
    FreezeTransEnum(String returnCode, String returnDesc) {
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
