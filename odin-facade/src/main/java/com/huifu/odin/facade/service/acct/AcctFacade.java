package com.huifu.odin.facade.service.acct;

import java.util.List;

/**
 * @author frank
 */
public interface AcctFacade {

    /**
     * 批量开户接口
     *
     * @param addAcctInfoDetailRequests
     * @return AddAcctInfoResult
     */
    AddAcctInfoResult batchAddAcctInfos(List<AddAcctInfoDetailRequest> addAcctInfoDetailRequests);


    /**
     * 账户信息修改接口
     *
     * @param modifyAcctInfoRequest
     * @return
     */
    ModifyAcctInfoResult modifyAcctInfo(ModifyAcctInfoRequest modifyAcctInfoRequest);

}
