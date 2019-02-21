package com.huifu.odin.biz.freeze;


import com.huifu.odin.biz.exception.BizException;

/**
 * @author frank
 */
public class FreezeTransException extends BizException {
    private String message;
    private String code;


    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FreezeTransException{");
        sb.append("message='").append(message).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public FreezeTransException(FreezeTransExceptionEnum transExceptionEnum) {
        super(transExceptionEnum.getReturnCode(), transExceptionEnum.getReturnDesc());
        this.setCode(transExceptionEnum.getReturnCode());
        this.setMessage(transExceptionEnum.getReturnDesc());
    }
}
