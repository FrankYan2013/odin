package com.huifu.odin.test.trans;

import com.huifu.odin.application.BootApplication;
import com.huifu.odin.facade.service.acct.*;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class AcctTest {

    @Autowired
    private AcctFacade acctFacade;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void 测试批量开户() {
        List<AddAcctInfoDetailRequest> addAcctInfoDetails = new ArrayList<>();
        AddAcctInfoDetailRequest addAcctInfoDetail = new AddAcctInfoDetailRequest();
        addAcctInfoDetail.setAcctName("testName");
        addAcctInfoDetail.setAcctType("BASEDT");
        addAcctInfoDetail.setBdepId("01");
        addAcctInfoDetail.setCapType("N");
        addAcctInfoDetail.setCuryType("1");
        addAcctInfoDetail.setCustId("60020020302");
        addAcctInfoDetail.setCustInfo("testInfo");
        addAcctInfoDetail.setSubAcctId("我们");
        addAcctInfoDetail.setSysId("P3");
        addAcctInfoDetails.add(addAcctInfoDetail);
        AddAcctInfoResult addAcctInfoResult = acctFacade.batchAddAcctInfos(addAcctInfoDetails);
        logger.info("接口返回:" + addAcctInfoResult.toString());
        Assert.assertNotNull(addAcctInfoResult);
    }

    @Test
    public void 测试用户修改() {
        ModifyAcctInfoRequest modifyAcctInfo = new ModifyAcctInfoRequest();
        modifyAcctInfo.setCustId("6666900000076681");
        modifyAcctInfo.setAcctStatus("S");
        modifyAcctInfo.setSubAcctId("146994");
        ModifyAcctInfoResult modifyAcctInfoResult = acctFacade.modifyAcctInfo(modifyAcctInfo);
        logger.info("接口返回:" + modifyAcctInfoResult.toString());
        Assert.assertNotNull(modifyAcctInfoResult);
    }

}
