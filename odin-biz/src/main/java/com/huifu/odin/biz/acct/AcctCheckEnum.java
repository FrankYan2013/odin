/**
 * 汇付天下有限公司
 * <p>
 * Copyright (c) 2006-2012 ChinaPnR,Inc.All Rights Reserved.
 */
package com.huifu.odin.biz.acct;

/**
 * @author frank
 */

public enum AcctCheckEnum {
    // 请求参数非法
    REQ_PARAMS_ILLEGAL("060", "请求参数非法"),
    DT_ACCT_EXIST("250", "借记账户已经存在"),
    DT_ACCT_NOTEXIST("251", "账户更新失败,请确认账户信息"),
    SUB_ACCT_ID_NOT_MATCH_ACCT_TYPE("252", "借记账户子账号与账户类型不匹配"),
    BATCH_ADD_ACCT_SUCCESS("000", "批量新增借记账户成功"),
    MODIFY_ACCT_SUCCESS("000", "修改借记账户成功"),

    SYSTEM_ERROR("099", "系统异常");
    /**
     * @param code
     * @param type
     */
    AcctCheckEnum(String code, String type) {
        this.code = code;
        this.type = type;
    }

    private String code;

    private String type;

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }


}
