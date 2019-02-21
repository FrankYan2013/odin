package com.huifu.odin.util.common;
/*
 * @(#)CurrencyUtils.java 1.0 2011-6-15下午08:53:56
 *
 * 上海汇付网络科技有限公司
 * Copyright (c) 2006-2011 ChinaPnR, Inc. All rights reserved.
 */

import org.apache.commons.lang3.math.NumberUtils;


public class CurrencyUtils {

    /**
     * 判断穿过来的amt是否符合金额格式：1.001 , 1.01 , 1.1 , 1
     *
     * @param amt
     * @return
     */
    public static boolean isCurrency(String amt) {
        if (amt.contains(".")) {
            if (amt.indexOf(".") != amt.lastIndexOf(".")) {
                return false;
            }
            String a[] = amt.split("\\.");
            if (a.length == 2) {
                if (NumberUtils.isDigits(a[0]) && NumberUtils.isDigits(a[1])) {
                    return true;
                }
            }
            return false;
        } else if (NumberUtils.isDigits(amt)) {
            return true;
        }
        return false;
    }

}
