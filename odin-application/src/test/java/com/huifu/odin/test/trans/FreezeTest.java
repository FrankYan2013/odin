package com.huifu.odin.test.trans;

import com.huifu.odin.application.BootApplication;
import com.huifu.odin.facade.service.trans.FreezeTransRequest;
import com.huifu.odin.facade.service.trans.FreezeTransResult;
import com.huifu.odin.facade.service.trans.TransFacade;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class FreezeTest {

    @Autowired
    private TransFacade transFacade;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void 测试冻结() {
        FreezeTransRequest freezeRequest = new FreezeTransRequest();
        freezeRequest.setReqSeqId(UUID.randomUUID().toString().substring(0, 10));
        freezeRequest.setSysId("PA");
        freezeRequest.setAcctType("BASEDT");
        freezeRequest.setBedpId("12");
        freezeRequest.setCustId("6666000000072283");
        freezeRequest.setFrtDate("20180814");
        freezeRequest.setFrtSeqId(UUID.randomUUID().toString().substring(0, 8));
        freezeRequest.setSubAcctId("163670");
        freezeRequest.setTransAmt("100");
        freezeRequest.setFrzCode("frzcode");
        freezeRequest.setVersionId("01");
        FreezeTransResult freezeTransResult = transFacade.freezeTrans(freezeRequest);
        logger.info("DoTrans接口返回:" + freezeTransResult.toString());
        Assert.assertNotNull(freezeTransResult);
    }

}
