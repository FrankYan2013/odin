package com.huifu.odin.integration.custmag.impl;

import com.huifu.custmag.facade.model.TradeCancelDto;
import com.huifu.custmag.facade.model.TradeLimitDto;
import com.huifu.custmag.facade.response.TradeCancelResult;
import com.huifu.custmag.facade.response.TradeLimitResult;
import com.huifu.custmag.facade.service.CustService;
import com.huifu.odin.integration.custmag.CustmagClient;
import com.huifu.pegasus.common.rpc.exception.NetTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author frank
 */
@Service
public class CustmagClientImpl implements CustmagClient {

    @Autowired
    private CustService custService;

    @Override
    public TradeLimitResult tradeLimitBatch(ArrayList<TradeLimitDto> tradeLimitDtoList) {
        try {
            TradeLimitResult tradeCancelResult = custService.tradeLimitBatch(tradeLimitDtoList);
            return tradeCancelResult;
        } catch (NetTimeoutException ex) {
            throw new CustmagRemoteTimeOutException();
        }
    }

    @Override
    public TradeCancelResult tradeCancelBatch(ArrayList<TradeCancelDto> tradeCancelDtoList) {
        TradeCancelResult tradeCancelResult = custService.tradeCancelBatch(tradeCancelDtoList);
        return tradeCancelResult;
    }

    public CustService getCustService() {
        return custService;
    }

    public void setCustService(CustService custService) {
        this.custService = custService;
    }


}
