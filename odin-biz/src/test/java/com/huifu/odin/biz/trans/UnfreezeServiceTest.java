package com.huifu.odin.biz.trans;

import com.huifu.odin.dal.entity.DtAcctInfo;
import com.huifu.odin.dal.entity.FrtDtlLog;
import com.huifu.odin.dal.entity.FrzLog;
import com.huifu.odin.dal.mapper.DtAcctInfoMapper;
import com.huifu.odin.dal.mapper.FrtDtlLogMapper;
import com.huifu.odin.dal.mapper.FrzLogMapper;
import com.huifu.odin.dal.mapper.SequenceMapper;
import com.huifu.odin.dal.repository.FrzLogRepository;
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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UnfreezeServiceTest {

    @InjectMocks
    UnfreezeService unfreezeService;
    private String dbSysDte;
    private String dbSysTimeToSave;
    private List<AcctUnfreezeRequestDetailDTO> unfreezeRequestDtos;
    private String sysId;

    @Mock
    FrzLogRepository frzLogRepository;

    @Mock
    SequenceMapper sequenceMapper;

    @Mock
    FrzLogMapper frzLogMapper;

    @Mock
    DtAcctInfoMapper dtAcctInfoMapper;

    @Mock
    FrtDtlLogMapper frtDtlLogMapper;


    DtAcctInfo mockDtAcctInfo;

    @Before
    public void setUp() throws Exception {
        dbSysDte = "20180906";
        dbSysTimeToSave = "150405";
        setupUnFreezeDto();
        sysId = "PA";
        mockDtAcctInfo();
        FrzLog frzLog = new FrzLog();
        frzLog.setCustId("6666000000072283");
        frzLog.setSubAcctId("163670");
        frzLog.setTransAmt("2");
        when(frzLogRepository.queryOriginalFrzLog(anyString(), anyString(), anyString())).thenReturn(frzLog);
        when(sequenceMapper.getFrzLogSeq()).thenReturn("11223344");
        when(frzLogMapper.insertSelective(any(FrzLog.class))).thenReturn(1);
        when(frzLogRepository.updateFrzStatF2U(anyString(), anyString(), anyString(), anyString())).thenReturn(1);
        when(dtAcctInfoMapper.selectByPrimaryKeyForUpdate(anyString(), anyString())).thenReturn(mockDtAcctInfo);
        when(dtAcctInfoMapper.updateUnfreezeAvlBalByPrimaryKey(any(BigDecimal.class), anyString(), anyString(), anyString())).thenReturn(1);
        when(frtDtlLogMapper.insert(any(FrtDtlLog.class))).thenReturn(1);
    }

    protected void mockDtAcctInfo() {
        mockDtAcctInfo = new DtAcctInfo();
        mockDtAcctInfo.setAcctBal(new BigDecimal(99.00));
        mockDtAcctInfo.setAcctName("mockAcctName");
        mockDtAcctInfo.setAcctStatus("N");
        mockDtAcctInfo.setAcctType("BASEDT");
        mockDtAcctInfo.setSubAcctId("mockSubAcctId");
        mockDtAcctInfo.setSysId("PA");
        mockDtAcctInfo.setBdepId("PA");
        mockDtAcctInfo.setAvlBal(new BigDecimal(99.00));
    }

    private void setupUnFreezeDto() {
        List<AcctUnfreezeRequestDetailDTO> acctUnfreezeRequestDetailDTOs = new ArrayList<>();
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
        acctUnfreezeRequestDetailDTOs.add(dto);
        unfreezeRequestDtos = acctUnfreezeRequestDetailDTOs;
    }

    @Test
    public void 测试正常解冻() {
        unfreezeService.doUnfreeze(dbSysDte, dbSysTimeToSave, unfreezeRequestDtos, sysId);
    }

    @Test
    public void 测试解冻原始交易不存在() {
        try {
            when(frzLogRepository.queryOriginalFrzLog(anyString(), anyString(), anyString())).thenReturn(null);
            unfreezeService.doUnfreeze(dbSysDte, dbSysTimeToSave, unfreezeRequestDtos, sysId);
        } catch (UnFreezeException e) {
            System.out.println(e);
            Assert.assertEquals(UnFreezeExceptionEnum.FREEZE_TRANS_NOT_EXSIT.getReturnCode(), e.getCode());
        }
    }

    @Test
    public void 测试解冻入库失败() {
        try {
            when(frzLogMapper.insertSelective(any(FrzLog.class))).thenReturn(0);
            unfreezeService.doUnfreeze(dbSysDte, dbSysTimeToSave, unfreezeRequestDtos, sysId);
        } catch (UnFreezeException e) {
            System.out.println(e);
            Assert.assertEquals(UnFreezeExceptionEnum.UNFREEZE_TRANS_INSERT_ERROR.getReturnCode(), e.getCode());
        }
    }

    @Test
    public void 测试解冻原始交易已解冻() {
        try {
            when(frzLogRepository.updateFrzStatF2U(anyString(), anyString(), anyString(), anyString())).thenReturn(0);
            unfreezeService.doUnfreeze(dbSysDte, dbSysTimeToSave, unfreezeRequestDtos, sysId);
        } catch (UnFreezeException e) {
            System.out.println(e);
            Assert.assertEquals(UnFreezeExceptionEnum.UNFREEZE_TRANS_ALREADY_UNFROZEN.getReturnCode(), e.getCode());
        }
    }

    @Test
    public void 测试解冻余额更新失败() {
        try {
            when(dtAcctInfoMapper.updateUnfreezeAvlBalByPrimaryKey(any(BigDecimal.class), anyString(), anyString(), anyString())).thenReturn(0);
            unfreezeService.doUnfreeze(dbSysDte, dbSysTimeToSave, unfreezeRequestDtos, sysId);
        } catch (UnFreezeException e) {
            System.out.println(e);
            Assert.assertEquals(UnFreezeExceptionEnum.UNFREEZE_TRANS_AVLBAL_ERROR.getReturnCode(), e.getCode());
        }
    }

    @Test
    public void 测试解冻数据库异常() {
        try {
            when(dtAcctInfoMapper.updateUnfreezeAvlBalByPrimaryKey(any(BigDecimal.class), anyString(), anyString(), anyString())).thenThrow(new DuplicateKeyException(""));
            unfreezeService.doUnfreeze(dbSysDte, dbSysTimeToSave, unfreezeRequestDtos, sysId);
        } catch (Exception e) {
            System.out.println(e);
            Assert.assertEquals(DuplicateKeyException.class, e.getClass());
        }
    }

    @Test
    public void 测试解冻重复交易() {
        try {
            when(dtAcctInfoMapper.updateUnfreezeAvlBalByPrimaryKey(any(BigDecimal.class), anyString(), anyString(), anyString())).thenThrow(Mockito.mock(DataAccessException.class));
            unfreezeService.doUnfreeze(dbSysDte, dbSysTimeToSave, unfreezeRequestDtos, sysId);
        } catch (Exception e) {
            System.out.println(e);
            Assert.assertTrue(e instanceof DataAccessException);
        }
    }

    @Test
    public void 测试解冻未知异常() {
        try {
            when(dtAcctInfoMapper.updateUnfreezeAvlBalByPrimaryKey(any(BigDecimal.class), anyString(), anyString(), anyString())).thenThrow(new NullPointerException("test"));
            unfreezeService.doUnfreeze(dbSysDte, dbSysTimeToSave, unfreezeRequestDtos, sysId);
        } catch (Exception e) {
            System.out.println(e);
            Assert.assertTrue(e instanceof Exception);
        }
    }

    @Test
    public void 测试解冻custId不一致() {
        try {
            FrzLog frzLog = new FrzLog();
            frzLog.setCustId("errorCustId");
            frzLog.setSubAcctId("163670");
            frzLog.setTransAmt("2");
            when(frzLogRepository.queryOriginalFrzLog(anyString(), anyString(), anyString())).thenReturn(frzLog);
            unfreezeService.doUnfreeze(dbSysDte, dbSysTimeToSave, unfreezeRequestDtos, sysId);
        } catch (UnFreezeException e) {
            System.out.println(e);
            Assert.assertEquals(UnFreezeExceptionEnum.UNFREEZE_TRANS_VAL_CUST_ERROR.getReturnCode(), e.getCode());
        }
    }

    @Test
    public void 测试解冻acctId不一致() {
        try {
            FrzLog frzLog = new FrzLog();
            frzLog.setCustId("6666000000072283");
            frzLog.setSubAcctId("9999");
            frzLog.setTransAmt("2");
            when(frzLogRepository.queryOriginalFrzLog(anyString(), anyString(), anyString())).thenReturn(frzLog);
            unfreezeService.doUnfreeze(dbSysDte, dbSysTimeToSave, unfreezeRequestDtos, sysId);
        } catch (UnFreezeException e) {
            System.out.println(e);
            Assert.assertEquals(UnFreezeExceptionEnum.UNFREEZE_TRANS_VAL_ACCTID_ERROR.getReturnCode(), e.getCode());
        }
    }

    @Test
    public void 测试解冻金额不一致() {
        try {
            FrzLog frzLog = new FrzLog();
            frzLog.setCustId("6666000000072283");
            frzLog.setSubAcctId("163670");
            frzLog.setTransAmt("3");
            when(frzLogRepository.queryOriginalFrzLog(anyString(), anyString(), anyString())).thenReturn(frzLog);
            unfreezeService.doUnfreeze(dbSysDte, dbSysTimeToSave, unfreezeRequestDtos, sysId);
        } catch (UnFreezeException e) {
            System.out.println(e);
            Assert.assertEquals(UnFreezeExceptionEnum.UNFREEZE_TRANS_VAL_AMT_ERROR.getReturnCode(), e.getCode());
        }
    }
}