package com.huifu.odin.biz.exception;

/**
 * 示例代码，可直接删除
 * <p>
 * Created by jianfei.chen on 2014/4/15.
 */
public class BizException extends RuntimeException {
    private String message;
    private String code;

    public BizException(String code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BizException{");
        sb.append("message='").append(message).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
