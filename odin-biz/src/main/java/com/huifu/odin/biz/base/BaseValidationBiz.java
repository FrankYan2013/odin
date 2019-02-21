package com.huifu.odin.biz.base;

import org.apache.commons.lang.StringUtils;

/**
 * @author frank
 */
public class BaseValidationBiz {

    protected boolean checkPara(String parameter, boolean canbeEmpty, int maxLength) {
        if (canbeEmpty == false) {
            if (StringUtils.isEmpty(parameter)) {
                return false;
            }
        }
        if (StringUtils.length(parameter) > maxLength) {
            return false;
        }
        return true;
    }
}
