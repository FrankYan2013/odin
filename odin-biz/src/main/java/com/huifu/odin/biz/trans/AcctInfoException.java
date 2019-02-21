package com.huifu.odin.biz.trans;

import com.huifu.odin.dal.entity.DtAcctInfo;

import java.util.List;

/**
 * @author frank
 */
public class AcctInfoException extends TransException {

    private List<DtAcctInfo> errorAcctInfos;

    public AcctInfoException(TransExceptionEnum transExceptionEnum, List<DtAcctInfo> acctInfos) {
        super(transExceptionEnum);
        setErrorAcctInfos(acctInfos);
    }

    public List<DtAcctInfo> getErrorAcctInfos() {
        return errorAcctInfos;
    }

    public void setErrorAcctInfos(List<DtAcctInfo> errorAcctInfos) {
        this.errorAcctInfos = errorAcctInfos;
    }
}
