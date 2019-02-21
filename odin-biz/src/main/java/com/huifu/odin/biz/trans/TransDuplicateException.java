package com.huifu.odin.biz.trans;

import com.huifu.odin.biz.exception.BizException;

/**
 * @author frank
 */
public class TransDuplicateException extends BizException {


    public TransDuplicateException(TransExceptionEnum transExceptionEnum) {
        super(transExceptionEnum.getReturnCode(), transExceptionEnum.getReturnDesc());
    }

}
