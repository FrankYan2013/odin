package com.huifu.odin.facade.service.acct;

import java.io.Serializable;

/**
 * @author frank
 */
public class ModifyAcctInfoResult implements Serializable {

    private static final long serialVersionUID = -5004167836130195180L;
    private String respCode;

    private String respDesc;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ModifyAcctInfoResult{");
        sb.append("respCode='").append(respCode).append('\'');
        sb.append(", respDesc='").append(respDesc).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }
}
