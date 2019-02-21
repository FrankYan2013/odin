package com.huifu.odin.biz.trans;

import com.huifu.odin.biz.exception.BizException;
import com.huifu.odin.dal.entity.DtAcctInfo;
import com.huifu.odin.dal.entity.FrtDtlLog;
import com.huifu.odin.dal.entity.TransType;
import com.huifu.odin.dal.mapper.*;
import com.huifu.odin.facade.service.trans.AcctTransRequestDetailDTO;
import com.huifu.odin.facade.service.trans.AcctTransRequestPeg;
import com.huifu.odin.facade.service.trans.AcctTransResultPegDetailDTO;
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

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransBizTest {

    @InjectMocks
    private TransBiz transBiz;

    private AcctTransRequestPeg acctTransRequestPeg;

    private TransType mockTransTypeC;

    private TransType mockTransTypeD;

    private DtAcctInfo mockDtAcctInfo;

    private List<String> seqs;

    @Mock
    private UnfreezeService unfreezeService;

    @Mock
    private AcctLogMapper acctLogMapper;

    @Mock
    private TransTypeBiz transTypeBiz;

    @Mock
    private TransTypeMapper transTypeMapper;

    @Mock
    private DtAcctInfoMapper dtAcctInfoMapper;

    @Mock
    private FrtDtlLogMapper frtDtlLogMapper;

    @Mock
    private SysStatMapper sysStatMapper;

    @Before
    public void setUp() throws Exception {
        setupAcctTransRequestPeg();
        mockTransTypeC();
        mockTransTypeD();
        mockDtAcctInfo();
        mockSeqs();
        when(transTypeBiz.getTransTypeByTransTypeCode(any(String.class))).thenReturn(mockTransTypeC);
        when(transTypeMapper.selectByPrimaryKey(any(String.class))).thenReturn(mockTransTypeC);
        when(acctLogMapper.selectDbDateTime()).thenReturn("201809051425000");
        when(acctLogMapper.selectSeqByNum(anyInt())).thenReturn(seqs);
        when(dtAcctInfoMapper.selectByPrimaryKeyForUpdate(any(String.class), any(String.class))).thenReturn(mockDtAcctInfo);
        when(dtAcctInfoMapper.updateAvlBalByPrimaryKey(any(BigDecimal.class), anyString(), anyString(), anyString())).thenReturn(1);
        when(sysStatMapper.selectSysStat()).thenReturn(1);
    }

    protected void mockDtAcctInfo() {
        mockDtAcctInfo = new DtAcctInfo();
        mockDtAcctInfo.setAcctBal(new BigDecimal(9999.00));
        mockDtAcctInfo.setAcctName("mockAcctName");
        mockDtAcctInfo.setAcctStatus("N");
        mockDtAcctInfo.setAcctType("BASEDT");
        mockDtAcctInfo.setSubAcctId("mockSubAcctId");
        mockDtAcctInfo.setSysId("PA");
        mockDtAcctInfo.setBdepId("PA");
        mockDtAcctInfo.setAvlBal(new BigDecimal(9999.00));
    }

    protected void mockTransTypeC() {
        mockTransTypeC = new TransType();
        mockTransTypeC.setDcFlag("C");
        mockTransTypeC.setTransDesc("mockTransDesc");
        mockTransTypeC.setTransType("mockTransType");
    }

    protected void mockTransTypeD() {
        mockTransTypeD = new TransType();
        mockTransTypeD.setDcFlag("D");
        mockTransTypeD.setTransDesc("mockTransDesc");
        mockTransTypeD.setTransType("mockTransType");
    }

    protected List<String> mockSeqs() {
        seqs = new ArrayList<>();
        for (int i = 0; i < acctTransRequestPeg.getAcctTransDetailList().size(); i++) {
            seqs.add(UUID.randomUUID().toString().substring(0, 8));
        }
        return seqs;
    }

    private void setupAcctTransRequestPeg() {
        acctTransRequestPeg = new AcctTransRequestPeg();
        List<AcctTransRequestDetailDTO> acctTransDetailList = setupAcctTransDetailList();
        acctTransRequestPeg.setAcctTransDetailList(acctTransDetailList);
        acctTransRequestPeg.setReqSeqId(UUID.randomUUID().toString().substring(0, 10));
        acctTransRequestPeg.setSysId("PA");
        acctTransRequestPeg.setTransCnt(acctTransDetailList.size() + "");
        acctTransRequestPeg.setVersionId("01");
        acctTransRequestPeg.setVerifyType("01");
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
            dto.setTransName("T0提现");
            dto.setTransObj("aaa");
            dto.setTransType("2001");
            dto.setMerId("6666000000072283");
            acctTransDetailList.add(dto);
        }
        return acctTransDetailList;
    }

    @Test
    public void 测试doTrans正常入金交易() {
        List<AcctTransResultPegDetailDTO> acctTransResultPegDetailDTOS = transBiz.doTrans(acctTransRequestPeg);
        Assert.assertNotNull(acctTransResultPegDetailDTOS);
    }

    @Test
    public void 测试doTrans正常出金交易() {
        when(transTypeBiz.getTransTypeByTransTypeCode(any(String.class))).thenReturn(mockTransTypeD);
        when(transTypeMapper.selectByPrimaryKey(any(String.class))).thenReturn(mockTransTypeD);
        List<AcctTransResultPegDetailDTO> acctTransResultPegDetailDTOS = transBiz.doTrans(acctTransRequestPeg);
        Assert.assertNotNull(acctTransResultPegDetailDTOS);
    }

    @Test
    public void 测试账户不存在() {
        when(dtAcctInfoMapper.selectByPrimaryKeyForUpdate(any(String.class), any(String.class))).thenReturn(null);
        try {
            transBiz.doTrans(acctTransRequestPeg);
        } catch (BizException e) {
            System.out.println(e);
            Assert.assertEquals("200", e.getCode());
        }
    }

    @Test
    public void 测试Odin日切离线() {
        when(sysStatMapper.selectSysStat()).thenReturn(0);
        try {
            transBiz.doTrans(acctTransRequestPeg);
        } catch (BizException e) {
            System.out.println(e);
            Assert.assertEquals("2", e.getCode());
        }
    }

    @Test
    public void 测试账户状态为C关闭() {
        DtAcctInfo dtInfoC = mockDtAcctInfo;
        dtInfoC.setAcctStatus("C");
        when(dtAcctInfoMapper.selectByPrimaryKeyForUpdate(any(String.class), any(String.class))).thenReturn(dtInfoC);
        try {
            transBiz.doTrans(acctTransRequestPeg);
        } catch (BizException e) {
            System.out.println(e);
            Assert.assertEquals("201", e.getCode());
        }
    }

    @Test
    public void 测试账户状态为D销户() {
        DtAcctInfo dtInfoC = mockDtAcctInfo;
        dtInfoC.setAcctStatus("D");
        when(dtAcctInfoMapper.selectByPrimaryKeyForUpdate(any(String.class), any(String.class))).thenReturn(dtInfoC);
        try {
            transBiz.doTrans(acctTransRequestPeg);
        } catch (BizException e) {
            System.out.println(e);
            Assert.assertEquals("201", e.getCode());
        }
    }

    @Test
    public void 测试账户状态为F冻结() {
        DtAcctInfo dtInfoC = mockDtAcctInfo;
        dtInfoC.setAcctStatus("F");
        when(transTypeBiz.getTransTypeByTransTypeCode(any(String.class))).thenReturn(mockTransTypeD);
        when(transTypeMapper.selectByPrimaryKey(any(String.class))).thenReturn(mockTransTypeD);
        when(dtAcctInfoMapper.selectByPrimaryKeyForUpdate(any(String.class), any(String.class))).thenReturn(dtInfoC);
        try {
            transBiz.doTrans(acctTransRequestPeg);
        } catch (BizException e) {
            System.out.println(e);
            Assert.assertEquals("201", e.getCode());
        }
    }

    @Test
    public void 测试重复交易() {
        try {
            when(frtDtlLogMapper.insertList(anyList())).thenThrow(new DuplicateKeyException("重复交易"));
            transBiz.doTrans(acctTransRequestPeg);
        } catch (BizException e) {
            System.out.println(e);
            Assert.assertEquals("001", e.getCode());
        }
    }

    @Test
    public void 测试数据库异常() {
        try {
            when(frtDtlLogMapper.insertList(anyList())).thenThrow(Mockito.mock(DataAccessException.class));
            transBiz.doTrans(acctTransRequestPeg);
        } catch (BizException e) {
            System.out.println(e);
            Assert.assertEquals("098", e.getCode());
        }
    }

    @Test
    public void 测试未知异常() {
        try {
            Mockito.doThrow(new NullPointerException("test")).when(transTypeBiz).getTransTypeByTransTypeCode(anyString());
            List<AcctTransResultPegDetailDTO> acctTransResultPegDetailDTOS = transBiz.doTrans(acctTransRequestPeg);
        } catch (BizException e) {
            System.out.println(e);
            Assert.assertEquals("099", e.getCode());
        }
    }

    @Test
    public void 测试更新余额失败() {
        try {
            when(dtAcctInfoMapper.updateAvlBalByPrimaryKey(any(BigDecimal.class), anyString(), anyString(), anyString())).thenReturn(0);
            transBiz.doTrans(acctTransRequestPeg);
        } catch (BizException e) {
            System.out.println(e);
            Assert.assertEquals("200", e.getCode());
        }
    }


    @Test
    public void 测试交易获得重复交易() {
        FrtDtlLog frtDtlLog = new FrtDtlLog();
        frtDtlLog.setBgAcctDate("20180904");
        frtDtlLog.setBgSeqId("mockSeqId");
        frtDtlLog.setAcctBal("2333.3");
        when(frtDtlLogMapper.selectByPrimaryKey(anyString(), anyString(), anyString(), anyString())).thenReturn(frtDtlLog);
        transBiz.getDuplicateTrans(acctTransRequestPeg);
    }

    @Test
    public void 测试doTrans出金交易余额不足() {
        try {
            when(transTypeBiz.getTransTypeByTransTypeCode(any(String.class))).thenReturn(mockTransTypeD);
            when(transTypeMapper.selectByPrimaryKey(any(String.class))).thenReturn(mockTransTypeD);
            AcctTransRequestPeg acctTransRequestPegError = acctTransRequestPeg;
            List<AcctTransRequestDetailDTO> acctTransDetailListError = new ArrayList<>();
            AcctTransRequestDetailDTO dto = new AcctTransRequestDetailDTO();
            dto.setAcctType("BASEDT");
            dto.setBedpId("12");
            dto.setCustId("6666000000072283");
            dto.setFeeAmount("0.0");
            dto.setFrtDate("20180528");
            dto.setFrtSeqId(UUID.randomUUID().toString().substring(0, 8));
            dto.setSubAcctId("163670");
            dto.setTransAmt("99999999.00");
            dto.setTransName("T0提现");
            dto.setTransObj("aaa");
            dto.setTransType("2001");
            dto.setMerId("6666000000072283");
            acctTransDetailListError.add(dto);
            acctTransDetailListError.add(dto);
            acctTransRequestPegError.setAcctTransDetailList(acctTransDetailListError);
            transBiz.doTrans(acctTransRequestPeg);
        } catch (BizException e) {
            System.out.println(e);
            Assert.assertEquals("203", e.getCode());
        }
    }

    @Test
    public void 测试解冻异常返回() {
        try {
            doThrow(new UnFreezeException(UnFreezeExceptionEnum.UNFREEZE_TRANS_AVLBAL_ERROR)).when(unfreezeService).doUnfreeze(any(String.class), anyString(), anyList(), anyString());
            transBiz.doTrans(acctTransRequestPeg);
        } catch (BizException e) {
            System.out.println(e);
            Assert.assertEquals("804", e.getCode());
        }
    }

}