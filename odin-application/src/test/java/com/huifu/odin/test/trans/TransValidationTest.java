/**
 *
 */
package com.huifu.odin.test.trans;

import com.huifu.odin.application.BootApplication;
import com.huifu.odin.biz.trans.TransException;
import com.huifu.odin.biz.trans.TransExceptionEnum;
import com.huifu.odin.facade.service.trans.AcctTransRequestDetailDTO;
import com.huifu.odin.facade.service.trans.AcctTransRequestPeg;
import com.huifu.odin.facade.service.trans.AcctTransResultPeg;
import com.huifu.odin.facade.service.trans.TransFacade;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * @author anxiang.liu_c
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class TransValidationTest {

    @Autowired
    private TransFacade transFacade;


    @Test
    public void 测试交易明细为空() {
        AcctTransRequestPeg pege = new AcctTransRequestPeg();
        AcctTransResultPeg result = transFacade.doTrans(pege);
        assertEquals(TransExceptionEnum.TransReqValdationFail.getReturnCode(), result.getRespCode());
    }

    @Test
    public void 测试交易明细条数大于20条记录() {
        AcctTransRequestPeg pege = new AcctTransRequestPeg();
        List<AcctTransRequestDetailDTO> list = Lists.newArrayList();
        for (int i = 0; i < 25; i++) {
            AcctTransRequestDetailDTO dto = new AcctTransRequestDetailDTO();
            list.add(dto);
        }
        pege.setAcctTransDetailList(list);
        AcctTransResultPeg result = transFacade.doTrans(pege);
        assertEquals(TransExceptionEnum.TransReqValdationFail.getReturnCode(), result.getRespCode());
    }

    public void 测试客户号超过16位() {
        AcctTransRequestPeg pege = new AcctTransRequestPeg();
        AcctTransRequestDetailDTO dto = new AcctTransRequestDetailDTO();
        dto.setCustId("00000000000000000");
        List<AcctTransRequestDetailDTO> list = Lists.newArrayList(dto);
        pege.setAcctTransDetailList(list);
        AcctTransResultPeg result = transFacade.doTrans(pege);
        assertEquals(TransExceptionEnum.TransReqValdationFail.getReturnCode(), result.getRespCode());
    }

    @Test
    public void 测试请求流水号超过12位() {
        AcctTransRequestPeg pege = new AcctTransRequestPeg();
        AcctTransRequestDetailDTO dto = new AcctTransRequestDetailDTO();
        dto.setCustId("0000000000000000");
        dto.setFrtSeqId("0000000000000");
        List<AcctTransRequestDetailDTO> list = Lists.newArrayList(dto);
        pege.setAcctTransDetailList(list);
        AcctTransResultPeg result = transFacade.doTrans(pege);
        assertEquals(TransExceptionEnum.TransReqValdationFail.getReturnCode(), result.getRespCode());
    }

    @Test
    public void 测试交易金额格式不合法() {
        AcctTransRequestPeg pege = new AcctTransRequestPeg();
        AcctTransRequestDetailDTO dto = new AcctTransRequestDetailDTO();
        dto.setCustId("0000000000000000");
        dto.setFrtSeqId("000000000000");
        dto.setTransAmt("&*20");
        List<AcctTransRequestDetailDTO> list = Lists.newArrayList(dto);
        pege.setAcctTransDetailList(list);
        AcctTransResultPeg result = transFacade.doTrans(pege);
        assertEquals(TransExceptionEnum.TransReqValdationFail.getReturnCode(), result.getRespCode());
    }

    @Test
    public void 测试交易手续费格式不合法() {
        AcctTransRequestPeg pege = new AcctTransRequestPeg();
        AcctTransRequestDetailDTO dto = new AcctTransRequestDetailDTO();
        dto.setCustId("0000000000000000");
        dto.setFrtSeqId("000000000000");
        dto.setTransAmt("1000.36");
        dto.setFeeAmount("&@#%^20");
        List<AcctTransRequestDetailDTO> list = Lists.newArrayList(dto);
        pege.setAcctTransDetailList(list);
        AcctTransResultPeg result = transFacade.doTrans(pege);
        assertEquals(TransExceptionEnum.TransReqValdationFail.getReturnCode(), result.getRespCode());
    }

    @Test
    public void 测试交易金额超长() {
        AcctTransRequestPeg pege = new AcctTransRequestPeg();
        AcctTransRequestDetailDTO dto = new AcctTransRequestDetailDTO();
        dto.setCustId("0000000000000000");
        dto.setFrtSeqId("000000000000");
        dto.setFeeAmount("100.10");
        dto.setTransAmt("100000000000000000.10");
        List<AcctTransRequestDetailDTO> list = Lists.newArrayList(dto);
        pege.setAcctTransDetailList(list);
        AcctTransResultPeg result = transFacade.doTrans(pege);
        assertEquals(TransExceptionEnum.TransReqValdationFail.getReturnCode(), result.getRespCode());
    }

    @Test
    public void 测试手续费金额超长() {
        AcctTransRequestPeg pege = new AcctTransRequestPeg();
        AcctTransRequestDetailDTO dto = new AcctTransRequestDetailDTO();
        dto.setCustId("0000000000000000");
        dto.setFrtSeqId("000000000000");
        dto.setFeeAmount("100000000000000000.10");
        dto.setTransAmt("100.10");
        List<AcctTransRequestDetailDTO> list = Lists.newArrayList(dto);
        pege.setAcctTransDetailList(list);
        AcctTransResultPeg result = transFacade.doTrans(pege);
        assertEquals(TransExceptionEnum.TransReqValdationFail.getReturnCode(), result.getRespCode());
    }

    @Test
    public void 测试交易类型不存在() {
        AcctTransRequestPeg pege = new AcctTransRequestPeg();
        AcctTransRequestDetailDTO dto = new AcctTransRequestDetailDTO();
        dto.setCustId("0000000000000000");
        dto.setFrtSeqId("000000000000");
        dto.setFeeAmount("0.10");
        dto.setTransAmt("100.10");
        List<AcctTransRequestDetailDTO> list = Lists.newArrayList(dto);
        pege.setAcctTransDetailList(list);
        try {
            AcctTransResultPeg result = transFacade.doTrans(pege);
        } catch (TransException ex) {
            assertEquals(TransExceptionEnum.TransTypeException.getReturnCode(), ex.getCode());
        }
    }


    @Test
    public void 测试交易异常() {
        AcctTransRequestPeg pege = new AcctTransRequestPeg();
        AcctTransRequestDetailDTO dto = new AcctTransRequestDetailDTO();
        dto.setCustId("U000000000000286");
        dto.setFrtSeqId("792080dd");
        dto.setFeeAmount("0.01");
        dto.setTransAmt("0.00");
        dto.setTransType("2001");
        List<AcctTransRequestDetailDTO> list = Lists.newArrayList(dto);
        pege.setAcctTransDetailList(list);
        try {
            AcctTransResultPeg result = transFacade.doTrans(pege);
        } catch (TransException ex) {
            assertEquals(TransExceptionEnum.SystemError.getReturnCode(), ex.getCode());
        }
    }

    @Test
    public void 测试账户冻结交易失败() {
        AcctTransRequestPeg pege = new AcctTransRequestPeg();
        AcctTransRequestDetailDTO dto = new AcctTransRequestDetailDTO();
        dto.setCustId("6000060002899314");
        dto.setFrtSeqId(getSplitUUID());
        dto.setFeeAmount("0.01");
        dto.setTransAmt("0.00");
        dto.setTransType("2001");
        dto.setSubAcctId("BASEDT");
        List<AcctTransRequestDetailDTO> list = Lists.newArrayList(dto);
        pege.setAcctTransDetailList(list);
        try {
            AcctTransResultPeg result = transFacade.doTrans(pege);
        } catch (TransException ex) {
            assertEquals(TransExceptionEnum.SystemError.getReturnCode(), ex.getCode());
        }
    }


    @Test
    public void 测试账户关闭交易失败() {
        AcctTransRequestPeg pege = new AcctTransRequestPeg();
        AcctTransRequestDetailDTO dto = new AcctTransRequestDetailDTO();
        dto.setCustId("6000060002887522");
        dto.setFrtSeqId(getSplitUUID());
        dto.setFeeAmount("0.01");
        dto.setTransAmt("0.00");
        dto.setTransType("2001");
        dto.setSubAcctId("MDT000001");
        List<AcctTransRequestDetailDTO> list = Lists.newArrayList(dto);
        pege.setAcctTransDetailList(list);
        try {
            AcctTransResultPeg result = transFacade.doTrans(pege);
        } catch (TransException ex) {
            assertEquals(TransExceptionEnum.SystemError.getReturnCode(), ex.getCode());
        }
    }

    @Test
    public void 测试账户销户交易失败() {
        AcctTransRequestPeg pege = new AcctTransRequestPeg();
        AcctTransRequestDetailDTO dto = new AcctTransRequestDetailDTO();
        dto.setCustId("6666000000041846");
        dto.setFrtSeqId(getSplitUUID());
        dto.setFeeAmount("0.01");
        dto.setTransAmt("0.00");
        dto.setTransType("2001");
        dto.setSubAcctId("99486");
        List<AcctTransRequestDetailDTO> list = Lists.newArrayList(dto);
        pege.setAcctTransDetailList(list);
        try {
            AcctTransResultPeg result = transFacade.doTrans(pege);
        } catch (TransException ex) {
            assertEquals(TransExceptionEnum.SystemError.getReturnCode(), ex.getCode());
        }
    }


    public String getSplitUUID() {
        String message = UUID.randomUUID().toString();
        return message.substring(1, 10);
    }
}
