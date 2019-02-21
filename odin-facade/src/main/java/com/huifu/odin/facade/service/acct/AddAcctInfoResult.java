/**
 * 汇付天下有限公司
 * <p>
 * Copyright (c) 2006-2012 ChinaPnR,Inc.All Rights Reserved.
 */
package com.huifu.odin.facade.service.acct;


import java.io.Serializable;
import java.util.List;

/**
 * @author frank
 */
public class AddAcctInfoResult implements Serializable {

    private static final long serialVersionUID = 5729493041614472422L;

    /**
     * 应答结果，000-成功,001-空数据,002-查不到信息,003-数据错误,010-数据已存在或已更新,999-系统异常
     */
    private String respCode;
    private String respDesc;


    private List<AddAcctInfoDetailResult> addAcctInfoDetailResults;

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

    public List<AddAcctInfoDetailResult> getAddAcctInfoDetailResults() {
        return addAcctInfoDetailResults;
    }

    public void setAddAcctInfoDetailResults(List<AddAcctInfoDetailResult> addAcctInfoDetailResults) {
        this.addAcctInfoDetailResults = addAcctInfoDetailResults;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AddAcctInfoResult{");
        sb.append("respCode='").append(respCode).append('\'');
        sb.append(", respDesc='").append(respDesc).append('\'');
        sb.append(", addAcctInfoDetailResults=").append(addAcctInfoDetailResults);
        sb.append('}');
        return sb.toString();
    }
}
