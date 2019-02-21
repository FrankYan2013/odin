package com.huifu.odin.biz.trans;

/**
 * @author frank
 */
public class TransValidationResult {

    public TransValidationResult(TransValidationExceptionEnum transValidationExceptionEnum) {
        this.isSuccessed = false;
        this.failCode = transValidationExceptionEnum.getReturnCode();
        this.failMessage = transValidationExceptionEnum.getReturnDesc();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TransValidationResult{");
        sb.append("isSuccessed=").append(isSuccessed);
        sb.append(", failCode='").append(failCode).append('\'');
        sb.append(", failMessage='").append(failMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public TransValidationResult() {
        this.isSuccessed = true;
    }

    public TransValidationResult(String failCode, String failMessage) {
        this.isSuccessed = false;
        this.failCode = failCode;
        this.failMessage = failMessage;
    }

    boolean isSuccessed = false;

    String failCode;

    String failMessage;

    public boolean isSuccessed() {
        return isSuccessed;
    }

    public void setSuccessed(boolean successed) {
        isSuccessed = successed;
    }

    public String getFailCode() {
        return failCode;
    }

    public void setFailCode(String failCode) {
        this.failCode = failCode;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }
}
