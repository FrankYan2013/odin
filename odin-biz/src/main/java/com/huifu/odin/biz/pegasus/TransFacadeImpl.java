/**
 * Chinapnr.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.huifu.odin.biz.pegasus;

import com.huifu.odin.biz.freeze.FreezeTransBiz;
import com.huifu.odin.biz.trans.TransService;
import com.huifu.odin.facade.service.trans.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author frank
 */
@Service("transFacade")
public class TransFacadeImpl implements TransFacade {

    @Autowired
    TransService transService;

    @Autowired
    FreezeTransBiz freezeTransBiz;

    @Override
    public AcctTransResultPeg doTrans(AcctTransRequestPeg acctTransRequestPeg) {
        return transService.doTransWithMonitor(acctTransRequestPeg);
    }

    @Override
    public UnfreezeTransResult unfreezeTrans(UnfreezeTransRequest unfreezeTransRequest) {
        return transService.unfreezeTransWithMonitor(unfreezeTransRequest);
    }

    @Override
    public FreezeTransResult freezeTrans(FreezeTransRequest freezeTransRequest) {
        return transService.freezeTransWithMonitor(freezeTransRequest);
    }

}
