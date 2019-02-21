package com.huifu.odin.biz.acct;

import com.huifu.odin.biz.exception.BizException;

/**
 * @author frank
 */
public class AcctCheckException extends BizException {


    public AcctCheckException(AcctCheckEnum acctCheckEnum, String message) {
        super(acctCheckEnum.getCode(), message);
    }
}
