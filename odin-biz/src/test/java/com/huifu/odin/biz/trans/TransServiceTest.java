package com.huifu.odin.biz.trans;

import com.huifu.odin.biz.base.BaseBiz;
import com.huifu.odin.biz.custmag.CustMagService;
import com.huifu.odin.biz.monitor.KafkaSenderService;
import com.huifu.odin.facade.service.trans.AcctTransRequestDetailDTO;
import com.huifu.odin.facade.service.trans.AcctTransRequestPeg;
import com.huifu.odin.facade.service.trans.AcctTransResultPeg;
import com.huifu.odin.facade.service.trans.AcctTransResultPegDetailDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransServiceTest {


    @InjectMocks
    TransService transService;

    AcctTransRequestPeg acctTransRequestPeg;


    private List<AcctTransRequestDetailDTO> setupAcctTransDetailList() {
        List<AcctTransRequestDetailDTO> acctTransDetailList = new ArrayList<AcctTransRequestDetailDTO>();
        for (int i = 0; i < 1; i++) {
            AcctTransRequestDetailDTO dto = new AcctTransRequestDetailDTO();
            dto.setAcctType("BASEDT");
            dto.setBedpId("12");
            dto.setCustId("6666000000072283");
            dto.setFeeAmount("0.0");
            dto.setFrtDate("20180528");
//            dto.setFrtSeqId("1111111");
            dto.setFrtSeqId(UUID.randomUUID().toString().substring(0, 8));
            dto.setSubAcctId("163670");
            dto.setTransAmt("0.02");
            dto.setTransName("T0提现");
            dto.setTransObj("aaa");
            dto.setPayAcct(true);
            dto.setTransType("2001");
            dto.setMerId("6666000000072283");
            acctTransDetailList.add(dto);
        }
        return acctTransDetailList;
    }

    @Mock
    TransValidationBiz transValidationBiz;

    @Mock
    TransBiz transBiz;

    @Mock
    BaseBiz baseBiz;

    @Mock
    CustMagService custMagService;

    @Mock
    KafkaSenderService kafkaSenderService;

    @Before
    public void setUp() throws Exception {
        acctTransRequestPeg = new AcctTransRequestPeg();
        List<AcctTransRequestDetailDTO> acctTransDetailList = setupAcctTransDetailList();
        acctTransRequestPeg.setReqSeqId(UUID.randomUUID().toString().substring(0, 10));
        acctTransRequestPeg.setSysId("PA");
        acctTransRequestPeg.setTransCnt(acctTransDetailList.size() + "");
        acctTransRequestPeg.setVersionId("02");
        acctTransRequestPeg.setVerifyType("01");
        acctTransRequestPeg.setAcctTransDetailList(acctTransDetailList);
        TransValidationResult transVal = new TransValidationResult();
        transVal.setSuccessed(true);
        when(transValidationBiz.baseValidation(any(AcctTransRequestPeg.class))).thenReturn(transVal);
        List<AcctTransResultPegDetailDTO> dtos = new ArrayList<>();
        AcctTransResultPegDetailDTO resultDetailDTO = new AcctTransResultPegDetailDTO();
        resultDetailDTO.setPrivateFields("mock");
        dtos.add(resultDetailDTO);
        when(transBiz.doTrans(any(AcctTransRequestPeg.class))).thenReturn(dtos);
        when(baseBiz.createDoTransSuccessResult(any(AcctTransRequestPeg.class), any(ArrayList.class), anyString(), anyString())).thenCallRealMethod();
        doNothing().when(custMagService).tradeCancelControl(any(AcctTransRequestPeg.class), anyBoolean());
        when(kafkaSenderService.sendToMimirByKafka(any(AcctTransResultPeg.class))).thenReturn(true);
    }

    @Test
    public void 测试交易接口正确性() {
        AcctTransResultPeg acctTransResultPeg = transService.doTransWithMonitor(acctTransRequestPeg);
        System.out.println(acctTransResultPeg);
    }

}