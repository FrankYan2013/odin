package com.huifu.odin.integration.custmag;

import com.huifu.custmag.facade.model.TradeCancelDto;
import com.huifu.custmag.facade.model.TradeLimitDto;
import com.huifu.custmag.facade.response.TradeCancelResult;
import com.huifu.custmag.facade.response.TradeLimitResult;

import java.util.ArrayList;

/**
 * @author frank
 */
public interface CustmagClient {

    TradeLimitResult tradeLimitBatch(ArrayList<TradeLimitDto> tradeLimitDtoList) throws Exception;

    TradeCancelResult tradeCancelBatch(ArrayList<TradeCancelDto> tradeCancelDtoList) throws Exception;

}
