package com.huifu.odin.biz.validate;

import com.huifu.odin.biz.exception.BizException;

import java.util.TreeSet;

/**
 * @author frank
 */
public class SaturnValidateException extends BizException {

    public SaturnValidateException(String code, String message, String allMessage, TreeSet<String> msgSet) {
        super(code, message);
        this.msgSet = msgSet;
        this.allMessage = allMessage;
        this.code = code;
    }

    private String allMessage;

    private TreeSet<String> msgSet;

    private String code;

    public String getAllMessage() {
        return allMessage;
    }

    public void setAllMessage(String allMessage) {
        this.allMessage = allMessage;
    }

    public TreeSet<String> getMsgSet() {
        return msgSet;
    }

    public void setMsgSet(TreeSet<String> msgSet) {
        this.msgSet = msgSet;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
