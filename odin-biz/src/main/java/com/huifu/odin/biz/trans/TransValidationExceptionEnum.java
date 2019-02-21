package com.huifu.odin.biz.trans;

/**
 * @author frank
 */

public enum TransValidationExceptionEnum {
    /**
     * 请求数据校验失败,解冻交易对象为空
     */
    TransReqValdationFailListUnfreezeCountIsNull("0646", "解冻交易对象为空"),
    /**
     * 请求数据校验失败,frzCode冻结码非空或超长
     */
    TransReqValdationFailFrozenAcctSeqIdError("0645", "原始冻结流水号非空或超长"),
    /**
     * 请求数据校验失败,frzCode冻结码非空或超长
     */
    TransReqValdationFailFrozenAcctDateError("0646", "原始冻结日期非空或超长"),
    /**
     * 请求数据校验失败,商户ID超长
     */
    TransReqValdationFailMerIdError("0644", "商户ID超长"),
    /**
     * 请求数据校验失败,frzCode冻结码非空或超长
     */
    TransReqValdationFailFrzCodeError("0643", "frzCode冻结码非空或超长"),
    /**
     * 请求数据校验失败,账户类型非空或超长
     */
    TransReqValdationFailIsPayAcctMerIdIsNull("0642", "支付账户MerId为空"),
    /**
     * 请求数据校验失败,账户类型非空或超长
     */
    TransReqValdationFailAcctTypeError("0641", "账户类型非空或超长"),
    /**
     * 请求数据校验失败,交易名称超长
     */
    TransReqValdationFailTransNameError("0640", "交易名称超长"),
    /**
     * 请求数据校验失败,第二客户号超长
     */
    TransReqValdationFailCustId2Error("0639", "CustId2超长"),
    /**
     * 请求数据校验失败,交易对象超长
     */
    TransReqValdationFailTransObjError("0638", "交易对象超长"),
    /**
     * 请求数据校验失败,前台交易号超长
     */
    TransReqValdationFailFrtTxnIdError("0637", "前台交易号超长"),
    /**
     * 请求数据校验失败,事业部代号超长
     */
    TransReqValdationFailBedpIdError("0636", "事业部代号非空或超长"),
    /**
     * 请求数据校验失败,账户号非空或超长
     */
    TransReqValdationFailSubAcctIdError("0635", "账户号非空或超长"),
    /**
     * 请求数据校验失败,交易类型非空或超长
     */
    TransReqValdationFailTransTypeError("0634", "交易类型非空或超长"),
    /**
     * 请求数据校验失败,日期非空或超长
     */
    TransReqValdationFailReqDateError("0633", "请求日期非空或最大8位"),
    /**
     * 请求数据校验失败,请求流水号非空或超长
     */
    TransReqValdationFailReqSeqIdError("0632", "请求流水号非空或超长"),
    /**
     * 请求数据校验失败,版本号非空或超长
     */
    TransReqValdationFailVersionIdError("0631", "版本号非空或超长"),
    /**
     * 请求数据校验失败,系统代号非空或超长
     */
    TransReqValdationFailSystemIdError("0630", "系统代号非空或超长"),
    /**
     * 请求数据校验失败,交易数量超长
     */
    TransReqValdationFailListCountError("0629", "交易数量验证不通过"),
    /**
     * 请求数据校验失败,交易金额非空或超长
     */
    TransReqValdationFailFeeAmtBaseError("0628", "手续费金额非空或超长"),
    /**
     * 请求数据校验失败,交易金额非空或超长
     */
    TransReqValdationFailAmtBaseError("0627", "交易金额非空或超长"),
    /**
     * 请求数据校验失败,手续费金额校验失败
     */
    TransReqValdationFailFeeAmtFromatError("0626", "手续费金额校验失败"),
    /**
     * 请求数据校验失败,交易金额校验失败
     */
    TransReqValdationFailAmtFormatError("0625", "交易金额校验失败"),
    /**
     * 请求数据校验失败,FrtSeqId非空或超长
     */
    TransReqValdationFailFrtSeqIdError("0624", "FrtSeqId非空或超长"),
    /**
     * 请求数据校验失败,CustId非空或超长
     */
    TransReqValdationFailCustIdError("0623", "CustId非空或超长"),
    /**
     * 请求数据校验失败,交易数量超长
     */
    TransReqValdationFailListCountToLong("0622", "交易数量超长"),
    /**
     * 请求数据校验失败,解冻交易数量超长
     */
    TransReqValdationFailListUnfreezeCountToLong("0622", "解冻交易数量超长"),
    /**
     * 请求数据校验失败,交易内容为空
     */
    TransReqValdationFailListIsNull("0621", "交易内容为空");


    /**
     * @param returnCode
     * @param returnDesc
     */
    TransValidationExceptionEnum(String returnCode, String returnDesc) {
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
