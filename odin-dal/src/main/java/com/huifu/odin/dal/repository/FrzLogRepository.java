package com.huifu.odin.dal.repository;


import com.huifu.odin.dal.entity.FrzLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author frank
 */
public interface FrzLogRepository {

    @Update({
            "update FRZ_LOG",
            "set FRZ_STAT = 'U',",
            "UNFRZ_SEQ = #{unfreezeSeqId,jdbcType=CHAR},",
            "UNFRZ_DATE = #{unfreezeDate,jdbcType=CHAR} ",
            "where ACCT_DATE = #{acctDate,jdbcType=CHAR}",
            "and ACCT_SEQ_ID = #{acctSeqId,jdbcType=VARCHAR}",
            "and FRZ_STAT = 'F'"
    })
    int updateFrzStatF2U(@Param("acctDate") String acctDate, @Param("acctSeqId") String acctSeqId, @Param("unfreezeSeqId") String unfreezeSeqId, @Param("unfreezeDate") String unfreezeDate);


    @Select({
            "select",
            "ACCT_DATE, ACCT_SEQ_ID, CUST_ID, SUB_ACCT_ID, TRANS_TYPE, FRZ_STAT, TRANS_AMT, FRZ_CODE,",
            "SYS_TIME, UNFRZ_DATE, UNFRZ_SEQ, TRANS_NAME, TRANS_OBJ, FRT_TXN_ID1, FRT_TXN_ID2,",
            "FRT_TXN_ID3, SYS_ID, FRT_DATE, FRT_SEQ_ID",
            "from FRZ_LOG ",
            "where ACCT_DATE = #{acctDate,jdbcType=CHAR}",
            "and ACCT_SEQ_ID = #{acctSeqId,jdbcType=VARCHAR}",
            "and TRANS_TYPE = '9001'",
            "and FRZ_CODE = #{frzCode,jdbcType=VARCHAR}"
    })
    @ResultMap("com.huifu.odin.dal.mapper.FrzLogMapper.BaseResultMap")
    FrzLog queryOriginalFrzLog(@Param("acctDate") String acctDate, @Param("acctSeqId") String acctSeqId, @Param("frzCode") String frzCode);

}
