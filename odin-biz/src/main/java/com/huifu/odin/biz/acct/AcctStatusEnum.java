/**
 * 汇付天下有限公司
 * <p>
 * Copyright (c) 2006-2012 ChinaPnR,Inc.All Rights Reserved.
 */
package com.huifu.odin.biz.acct;

/**
 * @author frank
 */

public enum AcctStatusEnum {

    // 正常
    N("N", "正常"),

    C("C", "关闭"),

    F("F", "冻结"),

    D("D", "销户");

    /**
     * @param code
     * @param type
     */
    private AcctStatusEnum(String code, String type) {
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
