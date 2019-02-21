/**
 * Chinapnr.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.huifu.odin.facade.service.trans;


/**
 * @author frank
 */
public interface TransFacade {

    /**
     * 批量出入账，批量转账，单笔出入账
     * @param acctTransRequestPeg
     * @return
     */
    AcctTransResultPeg doTrans(AcctTransRequestPeg acctTransRequestPeg);

    /**
     * 批量解冻
     * @param unfreezeTransRequest
     * @return
     */
    UnfreezeTransResult unfreezeTrans(UnfreezeTransRequest unfreezeTransRequest);

    /**
     * 单笔冻结
     * @param freezeTransRequest
     * @return
     */
    FreezeTransResult freezeTrans(FreezeTransRequest freezeTransRequest);
}
