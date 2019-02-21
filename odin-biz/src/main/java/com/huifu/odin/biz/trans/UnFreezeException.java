package com.huifu.odin.biz.trans;

import com.huifu.odin.biz.exception.BizException;

/**
 * @author frank
 */
public class UnFreezeException extends BizException {

    public UnFreezeException(UnFreezeExceptionEnum unFreezeExceptionEnum) {
        super(unFreezeExceptionEnum.getReturnCode(), unFreezeExceptionEnum.getReturnDesc());
    }
}
