package com.huifu.odin.test.trans;

import com.huifu.odin.application.BootApplication;
import com.huifu.odin.facade.service.trans.*;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class TransTest {

    @Autowired
    private TransFacade transFacade;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void 测试交易() {

        for (int i = 0; i < 1; i++) {
            AcctTransRequestPeg acctTransRequestPeg = new AcctTransRequestPeg();
            List<AcctTransRequestDetailDTO> acctTransDetailList = setupAcctTransDetailList();
            acctTransRequestPeg.setAcctTransDetailList(acctTransDetailList);
            acctTransRequestPeg.setReqSeqId(UUID.randomUUID().toString().substring(0, 10));
            acctTransRequestPeg.setSysId("PA");
            acctTransRequestPeg.setTransCnt(acctTransDetailList.size() + "");
            acctTransRequestPeg.setVersionId("01");
            acctTransRequestPeg.setVerifyType("01");
            //acctTransRequestPeg.setAcctUnfreezeRequestDetailDTOs(setupUnfreezeAcctTransDetailList());
            logger.info("DoTrans接口入参:" + acctTransRequestPeg.toString());
            AcctTransResultPeg acctTransResultPeg = transFacade.doTrans(acctTransRequestPeg);
            logger.info("DoTrans接口返回:" + acctTransResultPeg.toString());
            Assert.assertNotNull(acctTransRequestPeg);
        }
    }

    private List<AcctUnfreezeRequestDetailDTO> setupUnfreezeAcctTransDetailList() {
        List<AcctUnfreezeRequestDetailDTO> acctTransDetailList = new ArrayList<>();
        AcctUnfreezeRequestDetailDTO dto = new AcctUnfreezeRequestDetailDTO();
        dto.setAcctType("BASEDT");
        dto.setBedpId("12");
        dto.setCustId("6666000000072283");
        dto.setFrtDate("20180814");
        dto.setFrtSeqId(UUID.randomUUID().toString().substring(0, 8));
        dto.setFrozenAcctSeqId("3233408");
        dto.setFrozenAcctDate("20180816");
        dto.setSubAcctId("163670");
        dto.setTransAmt("2");
        dto.setFrzCode("frzcode");
        acctTransDetailList.add(dto);

        return acctTransDetailList;
    }

    private List<AcctTransRequestDetailDTO> setupAcctTransDetailList() {
        List<AcctTransRequestDetailDTO> acctTransDetailList = new ArrayList<AcctTransRequestDetailDTO>();
        for (int i = 0; i < 2; i++) {
            AcctTransRequestDetailDTO dto = new AcctTransRequestDetailDTO();
            dto.setAcctType("BASEDT");
            dto.setBedpId("12");
            dto.setCustId("6666000000072283");
            dto.setFeeAmount("0.0");
            dto.setFrtDate("20180528");
            dto.setFrtSeqId(UUID.randomUUID().toString().substring(0, 8));
            dto.setSubAcctId("163670");
            dto.setTransAmt("0.01");
            dto.setFeeAmount("0.02");
            dto.setTransName("T0提现");
            dto.setTransObj("aaa");
            dto.setPayAcct(true);
            dto.setTransType("2001");
            dto.setMerId("6666000000072283");
            acctTransDetailList.add(dto);
        }
        return acctTransDetailList;
    }
}
