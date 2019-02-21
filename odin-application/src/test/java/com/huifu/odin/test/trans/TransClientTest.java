package com.huifu.odin.test.trans;

import com.huifu.odin.facade.service.trans.AcctTransRequestDetailDTO;
import com.huifu.odin.facade.service.trans.AcctTransRequestPeg;
import com.huifu.odin.facade.service.trans.AcctTransResultPeg;
import com.huifu.odin.facade.service.trans.TransFacade;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-client.xml"})
public class TransClientTest {
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
            acctTransRequestPeg.setPrivateFields("测试私有域主");
            logger.info("DoTrans接口入参:" + acctTransRequestPeg.toString());
            AcctTransResultPeg acctTransResultPeg = transFacade.doTrans(acctTransRequestPeg);
            logger.info("DoTrans接口返回:" + acctTransResultPeg.toString());
        }
    }

    private List<AcctTransRequestDetailDTO> setupAcctTransDetailList() {
        List<AcctTransRequestDetailDTO> acctTransDetailList = new ArrayList<AcctTransRequestDetailDTO>();
        for (int i = 0; i < 1; i++) {
            AcctTransRequestDetailDTO dto = new AcctTransRequestDetailDTO();
            dto.setAcctType("BASEDT");
            dto.setBedpId("1");
            dto.setCustId("6000060000017547");
            dto.setFeeAmount("0.00");
            dto.setFrtDate("20151207");
            dto.setFrtSeqId(UUID.randomUUID().toString().substring(0, 8));
            dto.setSubAcctId("BASEDT");
            dto.setTransAmt("0.01");
            dto.setTransName("T0提现");
            dto.setTransObj("aaa");
            dto.setTransType("1001");
            dto.setPrivateFields("测试私有域子");
            dto.setInAcctId("inAcctId");
            acctTransDetailList.add(dto);
        }
        return acctTransDetailList;
    }
}
