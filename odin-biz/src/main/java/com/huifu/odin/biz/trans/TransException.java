package com.huifu.odin.biz.trans;

import com.huifu.odin.biz.exception.BizException;

/**
 * @author frank
 */
public class TransException extends BizException {

    public TransException(TransExceptionEnum transExceptionEnum) {
        super(transExceptionEnum.getReturnCode(), transExceptionEnum.getReturnDesc());
    }
}
