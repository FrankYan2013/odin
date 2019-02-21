package com.huifu.odin.biz.trans;

import com.huifu.odin.facade.service.trans.AcctUnfreezeRequestDetailDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;


@RunWith(MockitoJUnitRunner.class)
public class UnfreezeBizTest {

    @InjectMocks
    private UnfreezeBiz unfreezeBiz;

    private String dbSysDte;
    private String dbSysTimeToSave;
    private List<AcctUnfreezeRequestDetailDTO> unfreezeRequestDtos;
    private String sysId;

    private void setupUnFreezeDto() {
        List<AcctUnfreezeRequestDetailDTO> acctUnfreezeRequestDetailDTOs = new ArrayList<>();
        AcctUnfreezeRequestDetailDTO dto = new AcctUnfreezeRequestDetailDTO();
        dto.setAcctType("BASEDT");
        dto.setBedpId("12");
        dto.setCustId("6666000000072283");
        dto.setFrtDate("20180814");
        dto.setFrtSeqId(UUID.randomUUID().toString().substring(0, 8));
        dto.setFrozenAcctSeqId("32334028");
        dto.setFrozenAcctDate("20180816");
        dto.setSubAcctId("163670");
        dto.setTransAmt("2");
        dto.setFrzCode("frzcode");
        acctUnfreezeRequestDetailDTOs.add(dto);
        unfreezeRequestDtos = acctUnfreezeRequestDetailDTOs;
    }

    @Mock
    UnfreezeService unfreezeService;

    @Before
    public void setUp() throws Exception {
        dbSysDte = "20180906";
        dbSysTimeToSave = "150405";
        setupUnFreezeDto();
        sysId = "PA";
        doNothing().when(unfreezeService).doUnfreeze(anyString(), anyString(), anyList(), anyString());
    }

    @Test
    public void 测试冻结接口入口正常() {
        unfreezeBiz.doUnfreeze(dbSysDte, dbSysTimeToSave, unfreezeRequestDtos, sysId);
    }

    @Test
    public void 测试冻结接口数据库重复交易() {
        doThrow(new DuplicateKeyException("")).when(unfreezeService).doUnfreeze(anyString(), anyString(), anyList(), anyString());
        try {
            unfreezeBiz.doUnfreeze(dbSysDte, dbSysTimeToSave, unfreezeRequestDtos, sysId);
        } catch (UnFreezeException e) {
            System.out.println(e);
            Assert.assertEquals(UnFreezeExceptionEnum.DuplicateKeyException.getReturnCode(), e.getCode());
        }
    }

    @Test
    public void 测试冻结接口数据库异常() {
        doThrow(Mockito.mock(DataAccessException.class)).when(unfreezeService).doUnfreeze(anyString(), anyString(), anyList(), anyString());
        try {
            unfreezeBiz.doUnfreeze(dbSysDte, dbSysTimeToSave, unfreezeRequestDtos, sysId);
        } catch (UnFreezeException e) {
            System.out.println(e);
            Assert.assertEquals(UnFreezeExceptionEnum.Db_Error.getReturnCode(), e.getCode());
        }
    }

    @Test
    public void 测试冻结接口业务异常处理() {
        doThrow(new UnFreezeException(UnFreezeExceptionEnum.FREEZE_TRANS_NOT_EXSIT)).when(unfreezeService).doUnfreeze(anyString(), anyString(), anyList(), anyString());
        try {
            unfreezeBiz.doUnfreeze(dbSysDte, dbSysTimeToSave, unfreezeRequestDtos, sysId);
        } catch (UnFreezeException e) {
            System.out.println(e);
            Assert.assertEquals(UnFreezeExceptionEnum.FREEZE_TRANS_NOT_EXSIT.getReturnCode(), e.getCode());
        }
    }

    @Test
    public void 测试冻结接口099异常() {
        doThrow(new NullPointerException()).when(unfreezeService).doUnfreeze(anyString(), anyString(), anyList(), anyString());
        try {
            unfreezeBiz.doUnfreeze(dbSysDte, dbSysTimeToSave, unfreezeRequestDtos, sysId);
        } catch (Exception e) {
            System.out.println(e);
            Assert.assertTrue(e instanceof Exception);
        }
    }

    @Test
    public void 测试金额比较() {
        int i = new BigDecimal("1000").compareTo(new BigDecimal("1000.00"));
        System.out.println(i);
        Assert.assertTrue(i == 0);
    }

}