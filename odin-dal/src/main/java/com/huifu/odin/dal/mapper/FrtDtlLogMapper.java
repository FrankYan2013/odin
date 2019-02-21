package com.huifu.odin.dal.mapper;

import com.huifu.odin.dal.entity.FrtDtlLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FrtDtlLogMapper {
    @Delete({
            "delete from FRT_DTL_LOG",
            "where FRT_DATE = #{frtDate,jdbcType=CHAR}",
            "and FRT_SEQ_ID = #{frtSeqId,jdbcType=VARCHAR}",
            "and SYS_ID = #{sysId,jdbcType=VARCHAR}",
            "and TRANS_TYPE = #{transType,jdbcType=CHAR}"
    })
    int deleteByPrimaryKey(@Param("frtDate") String frtDate, @Param("frtSeqId") String frtSeqId, @Param("sysId") String sysId, @Param("transType") String transType);

    @Insert({
            "insert into FRT_DTL_LOG (FRT_DATE, FRT_SEQ_ID, ",
            "SYS_ID, TRANS_TYPE, REQ_SEQ_ID, ",
            "CUST_ID, SUB_ACCT_ID, ",
            "ACCT_TYPE, TRANS_AMT, ",
            "FEE_AMT, CUST_ID2, ",
            "BDEP_ID, TRANS_NAME, ",
            "TRANS_OBJ, FRT_TXN_ID1, ",
            "FRT_TXN_ID2, FRT_TXN_ID3, ",
            "BG_ACCT_DATE, BG_SEQ_ID, ",
            "ACCT_BAL)",
            "values (#{frtDate,jdbcType=CHAR}, #{frtSeqId,jdbcType=VARCHAR}, ",
            "#{sysId,jdbcType=VARCHAR}, #{transType,jdbcType=CHAR}, #{reqSeqId,jdbcType=VARCHAR}, ",
            "#{custId,jdbcType=VARCHAR}, #{subAcctId,jdbcType=VARCHAR}, ",
            "#{acctType,jdbcType=VARCHAR}, #{transAmt,jdbcType=VARCHAR}, ",
            "#{feeAmt,jdbcType=VARCHAR}, #{custId2,jdbcType=VARCHAR}, ",
            "#{bdepId,jdbcType=VARCHAR}, #{transName,jdbcType=VARCHAR}, ",
            "#{transObj,jdbcType=VARCHAR}, #{frtTxnId1,jdbcType=VARCHAR}, ",
            "#{frtTxnId2,jdbcType=VARCHAR}, #{frtTxnId3,jdbcType=VARCHAR}, ",
            "#{bgAcctDate,jdbcType=CHAR}, #{bgSeqId,jdbcType=VARCHAR}, ",
            "#{acctBal,jdbcType=VARCHAR})"
    })
    int insert(FrtDtlLog record);

    int insertSelective(FrtDtlLog record);

    @Select({
            "select",
            "FRT_DATE, FRT_SEQ_ID, SYS_ID, TRANS_TYPE, REQ_SEQ_ID, CUST_ID, SUB_ACCT_ID, ",
            "ACCT_TYPE, TRANS_AMT, FEE_AMT, CUST_ID2, BDEP_ID, TRANS_NAME, TRANS_OBJ, FRT_TXN_ID1, ",
            "FRT_TXN_ID2, FRT_TXN_ID3, BG_ACCT_DATE, BG_SEQ_ID, ACCT_BAL",
            "from FRT_DTL_LOG",
            "where FRT_DATE = #{frtDate,jdbcType=CHAR}",
            "and FRT_SEQ_ID = #{frtSeqId,jdbcType=VARCHAR}",
            "and SYS_ID = #{sysId,jdbcType=VARCHAR}",
            "and TRANS_TYPE = #{transType,jdbcType=CHAR}"
    })
    @ResultMap("com.huifu.odin.dal.mapper.FrtDtlLogMapper.BaseResultMap")
    FrtDtlLog selectByPrimaryKey(@Param("frtDate") String frtDate, @Param("frtSeqId") String frtSeqId, @Param("sysId") String sysId, @Param("transType") String transType);

    int updateByPrimaryKeySelective(FrtDtlLog record);

    @Update({
            "update FRT_DTL_LOG",
            "set REQ_SEQ_ID = #{reqSeqId,jdbcType=VARCHAR},",
            "CUST_ID = #{custId,jdbcType=VARCHAR},",
            "SUB_ACCT_ID = #{subAcctId,jdbcType=VARCHAR},",
            "ACCT_TYPE = #{acctType,jdbcType=VARCHAR},",
            "TRANS_AMT = #{transAmt,jdbcType=VARCHAR},",
            "FEE_AMT = #{feeAmt,jdbcType=VARCHAR},",
            "CUST_ID2 = #{custId2,jdbcType=VARCHAR},",
            "BDEP_ID = #{bdepId,jdbcType=VARCHAR},",
            "TRANS_NAME = #{transName,jdbcType=VARCHAR},",
            "TRANS_OBJ = #{transObj,jdbcType=VARCHAR},",
            "FRT_TXN_ID1 = #{frtTxnId1,jdbcType=VARCHAR},",
            "FRT_TXN_ID2 = #{frtTxnId2,jdbcType=VARCHAR},",
            "FRT_TXN_ID3 = #{frtTxnId3,jdbcType=VARCHAR},",
            "BG_ACCT_DATE = #{bgAcctDate,jdbcType=CHAR},",
            "BG_SEQ_ID = #{bgSeqId,jdbcType=VARCHAR},",
            "ACCT_BAL = #{acctBal,jdbcType=VARCHAR}",
            "where FRT_DATE = #{frtDate,jdbcType=CHAR}",
            "and FRT_SEQ_ID = #{frtSeqId,jdbcType=VARCHAR}",
            "and SYS_ID = #{sysId,jdbcType=VARCHAR}",
            "and TRANS_TYPE = #{transType,jdbcType=CHAR}"
    })
    int updateByPrimaryKey(FrtDtlLog record);

    int insertList(@Param("records") List<FrtDtlLog> frtDtlLogs);
}