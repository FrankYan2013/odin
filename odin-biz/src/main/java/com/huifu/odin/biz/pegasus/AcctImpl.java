/**
 * Chinapnr.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.huifu.odin.biz.pegasus;

import com.huifu.odin.biz.acct.AcctService;
import com.huifu.odin.facade.service.acct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author frank
 */
@Service("acctFacade")
public class AcctImpl implements AcctFacade {

    @Autowired
    private AcctService acctService;

    @Override
    public AddAcctInfoResult batchAddAcctInfos(List<AddAcctInfoDetailRequest> addAcctInfoDetailRequests) {
        return acctService.batchAddAcctInfosWithMonitor(addAcctInfoDetailRequests);
    }

    @Override
    public ModifyAcctInfoResult modifyAcctInfo(ModifyAcctInfoRequest modifyAcctInfoRequest) {
        return acctService.modifyAcctInfoWithMonitor(modifyAcctInfoRequest);
    }
}
