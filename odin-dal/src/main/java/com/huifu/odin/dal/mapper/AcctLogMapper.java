package com.huifu.odin.dal.mapper;

/**
 * 汇付天下有限公司
 * <p>
 * Copyright (c) 2006-2012 ChinaPnR,Inc.All Rights Reserved.
 */

import com.huifu.odin.dal.entity.AcctLog;
import com.huifu.odin.dal.entity.QueryAcctLogParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AcctLogMapper {

    @Delete({
            "delete from ACCT_LOG",
            "where ACCT_DATE = #{acctDate,jdbcType=CHAR}",
            "and ACCT_SEQ_ID = #{acctSeqId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(@Param("acctDate") String acctDate, @Param("acctSeqId") String acctSeqId);

    @Insert({
            "insert into ACCT_LOG (ACCT_DATE, ACCT_SEQ_ID, ",
            "TRANS_TYPE, CUST_ID, ",
            "SUB_ACCT_ID, ACCT_TYPE, ",
            "ACCT_NAME, CUST_INFO, ",
            "SYS_TIME, DC_FLAG, TRANS_AMT, ",
            "FEE_AMT, ACCT_BAL, ",
            "CUST_ID2, SYS_ID, ",
            "BDEP_ID, TRANS_NAME, ",
            "TRANS_OBJ, FRT_TXN_ID1, ",
            "FRT_TXN_ID2, FRT_TXN_ID3, ",
            "FRT_DATE, FRT_SEQ_ID)",
            "values (#{acctDate,jdbcType=CHAR}, #{acctSeqId,jdbcType=VARCHAR}, ",
            "#{transType,jdbcType=CHAR}, #{custId,jdbcType=VARCHAR}, ",
            "#{subAcctId,jdbcType=VARCHAR}, #{acctType,jdbcType=VARCHAR}, ",
            "#{acctName,jdbcType=VARCHAR}, #{custInfo,jdbcType=VARCHAR}, ",
            "#{sysTime,jdbcType=CHAR}, #{dcFlag,jdbcType=CHAR}, #{transAmt,jdbcType=VARCHAR}, ",
            "#{feeAmt,jdbcType=VARCHAR}, #{acctBal,jdbcType=VARCHAR}, ",
            "#{custId2,jdbcType=VARCHAR}, #{sysId,jdbcType=VARCHAR}, ",
            "#{bdepId,jdbcType=VARCHAR}, #{transName,jdbcType=VARCHAR}, ",
            "#{transObj,jdbcType=VARCHAR}, #{frtTxnId1,jdbcType=VARCHAR}, ",
            "#{frtTxnId2,jdbcType=VARCHAR}, #{frtTxnId3,jdbcType=VARCHAR}, ",
            "#{frtDate,jdbcType=CHAR}, #{frtSeqId,jdbcType=VARCHAR})"
    })
    @SelectKey(statement = "select lpad(ACCT_LOG_SEQ.nextval,10,0) id from dual", keyProperty = "acctSeqId", before = true, resultType = String.class)
    int insert(AcctLog record);

    int insertSelective(AcctLog record);

    @Select({
            "select",
            "ACCT_DATE, ACCT_SEQ_ID, TRANS_TYPE, CUST_ID, SUB_ACCT_ID, ACCT_TYPE, ACCT_NAME, ",
            "CUST_INFO, SYS_TIME, DC_FLAG, TRANS_AMT, FEE_AMT, ACCT_BAL, CUST_ID2, SYS_ID, ",
            "BDEP_ID, TRANS_NAME, TRANS_OBJ, FRT_TXN_ID1, FRT_TXN_ID2, FRT_TXN_ID3, FRT_DATE, ",
            "FRT_SEQ_ID",
            "from ACCT_LOG",
            "where ACCT_DATE = #{acctDate,jdbcType=CHAR}",
            "and ACCT_SEQ_ID = #{acctSeqId,jdbcType=VARCHAR}"
    })
    @ResultMap("com.huifu.odin.dal.mapper.AcctLogMapper.BaseResultMap")
    AcctLog selectByPrimaryKey(@Param("acctDate") String acctDate, @Param("acctSeqId") String acctSeqId);

    int updateByPrimaryKeySelective(AcctLog record);

    @Update({
            "update ACCT_LOG",
            "set TRANS_TYPE = #{transType,jdbcType=CHAR},",
            "CUST_ID = #{custId,jdbcType=VARCHAR},",
            "SUB_ACCT_ID = #{subAcctId,jdbcType=VARCHAR},",
            "ACCT_TYPE = #{acctType,jdbcType=VARCHAR},",
            "ACCT_NAME = #{acctName,jdbcType=VARCHAR},",
            "CUST_INFO = #{custInfo,jdbcType=VARCHAR},",
            "SYS_TIME = #{sysTime,jdbcType=CHAR},",
            "DC_FLAG = #{dcFlag,jdbcType=CHAR},",
            "TRANS_AMT = #{transAmt,jdbcType=VARCHAR},",
            "FEE_AMT = #{feeAmt,jdbcType=VARCHAR},",
            "ACCT_BAL = #{acctBal,jdbcType=VARCHAR},",
            "CUST_ID2 = #{custId2,jdbcType=VARCHAR},",
            "SYS_ID = #{sysId,jdbcType=VARCHAR},",
            "BDEP_ID = #{bdepId,jdbcType=VARCHAR},",
            "TRANS_NAME = #{transName,jdbcType=VARCHAR},",
            "TRANS_OBJ = #{transObj,jdbcType=VARCHAR},",
            "FRT_TXN_ID1 = #{frtTxnId1,jdbcType=VARCHAR},",
            "FRT_TXN_ID2 = #{frtTxnId2,jdbcType=VARCHAR},",
            "FRT_TXN_ID3 = #{frtTxnId3,jdbcType=VARCHAR},",
            "FRT_DATE = #{frtDate,jdbcType=CHAR},",
            "FRT_SEQ_ID = #{frtSeqId,jdbcType=VARCHAR}",
            "where ACCT_DATE = #{acctDate,jdbcType=CHAR}",
            "and ACCT_SEQ_ID = #{acctSeqId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(AcctLog record);


    @Select({
            "select lpad(ACCT_LOG_SEQ.nextval,10,0) acct_log_seq from dual connect by rownum <= #{num,jdbcType=INTEGER}"
    })
    List<String> selectSeqByNum(@Param("num") int num);


    @Select({
            "select to_char(systimestamp,'yyyyMMddhh24missff3') from dual"
    })
    String selectDbDateTime();

    List<AcctLog> selectAcctLogByPage(@Param("queryAcctLogParam") QueryAcctLogParam queryAcctLogParam);

    int insertList(@Param("records") List<AcctLog> records);
}
