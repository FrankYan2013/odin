package com.huifu.odin.test.trans;

import com.huifu.odin.application.BootApplication;
import com.huifu.odin.facade.service.trans.AcctUnfreezeRequestDetailDTO;
import com.huifu.odin.facade.service.trans.TransFacade;
import com.huifu.odin.facade.service.trans.UnfreezeTransRequest;
import com.huifu.odin.facade.service.trans.UnfreezeTransResult;
import org.junit.Assert;
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
public class UnfreezeTest {

    @Autowired
    private TransFacade transFacade;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void 测试解冻() {

        for (int i = 0; i < 1; i++) {
            UnfreezeTransRequest unfreezeRequest = new UnfreezeTransRequest();
            unfreezeRequest.setReqSeqId(UUID.randomUUID().toString().substring(0, 10));
            unfreezeRequest.setSysId("PA");
            unfreezeRequest.setVersionId("01");

            List<AcctUnfreezeRequestDetailDTO> acctUnFreezeDtos = new ArrayList<>();
            AcctUnfreezeRequestDetailDTO acctUnfreezeDto = new AcctUnfreezeRequestDetailDTO();
            acctUnfreezeDto.setAcctType("BASEDT");
            acctUnfreezeDto.setBedpId("12");
            acctUnfreezeDto.setCustId("6666000000072283");
            acctUnfreezeDto.setFrtDate("20180814");
            acctUnfreezeDto.setFrtSeqId(UUID.randomUUID().toString().substring(0, 8));
            acctUnfreezeDto.setFrozenAcctSeqId("3233408");
            acctUnfreezeDto.setFrozenAcctDate("20180816");
            acctUnfreezeDto.setSubAcctId("163670");
            acctUnfreezeDto.setTransAmt("2");
            acctUnfreezeDto.setFrzCode("frzcode");
            acctUnFreezeDtos.add(acctUnfreezeDto);
            unfreezeRequest.setAcctUnfreezeRequestDetailDTOs(acctUnFreezeDtos);

            logger.info("DoTrans接口入参:" + unfreezeRequest.toString());
            UnfreezeTransResult unfreezeTransResult = transFacade.unfreezeTrans(unfreezeRequest);
            logger.info("DoTrans接口返回:" + unfreezeTransResult.toString());
            Assert.assertNotNull(unfreezeTransResult);
        }
    }

}
