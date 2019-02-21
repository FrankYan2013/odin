package com.huifu.odin.biz.trans;

import com.alibaba.fastjson.JSON;
import com.huifu.odin.dal.entity.TransType;
import com.huifu.odin.dal.mapper.TransTypeMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransTypeBizTest {

    @InjectMocks
    TransTypeBiz transTypeBiz;

    @Mock
    TransTypeMapper transTypeMapper;

    TransType mockTransType;

    @Before
    public void setUp() throws Exception {
        mockTransType = new TransType();
        mockTransType.setTransDesc("mockTransDesc");
        mockTransType.setDcFlag("C");
        mockTransType.setTransType("mockTransType");
        when(transTypeMapper.selectByPrimaryKey(anyString())).thenReturn(mockTransType);
    }

    @Test
    public void 测试获取交易类型正常() {
        TransType transType = transTypeBiz.getTransTypeByTransTypeCode("test");
        System.out.println(JSON.toJSONString(transType));
        Assert.assertEquals(mockTransType.getTransType(), transType.getTransType());
    }

    @Test
    public void 测试获取交易类型正常走缓存() {
        TransType transType = transTypeBiz.getTransTypeByTransTypeCode("test");
        System.out.println(JSON.toJSONString(transType));
        Assert.assertEquals(mockTransType.getTransType(), transType.getTransType());
    }

    @Test
    public void 测试获取交易类型不存在() {
        when(transTypeMapper.selectByPrimaryKey(anyString())).thenReturn(null);
        try {
            TransType transType = transTypeBiz.getTransTypeByTransTypeCode("test2");
            System.out.println(JSON.toJSONString(transType));
        } catch (TransException e) {
            System.out.println(e);
            Assert.assertEquals(e.getCode(), "091");
        }
    }
}