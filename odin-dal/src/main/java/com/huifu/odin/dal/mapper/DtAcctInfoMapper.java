package com.huifu.odin.dal.mapper;

import com.huifu.odin.dal.entity.DtAcctInfo;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

public interface DtAcctInfoMapper {
    @Delete({
            "delete from DT_ACCT_INFO",
            "where CUST_ID = #{custId,jdbcType=VARCHAR}",
            "and SUB_ACCT_ID = #{subAcctId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(@Param("custId") String custId, @Param("subAcctId") String subAcctId);

    @Insert({
            "insert into DT_ACCT_INFO (CUST_ID, SUB_ACCT_ID, ",
            "CAP_TYPE, CURY_TYPE, ",
            "ACCT_TYPE, ACCT_NAME, ",
            "CUST_INFO, ACCT_STATUS, ",
            "AVL_BAL, FRZ_BAL, ",
            "ACCT_BAL, LAST_UPD_DATE, ",
            "LAST_AVL_BAL, LAST_FRZ_BAL, ",
            "TODAY_AVL_BAL, TODAY_FRZ_BAL, ",
            "INT_BAL, LAST_INT_DATE, ",
            "SYS_ID, BDEP_ID, OPEN_DATE, ",
            "OPEN_TIME)",
            "values (#{custId,jdbcType=VARCHAR}, #{subAcctId,jdbcType=VARCHAR}, ",
            "#{capType,jdbcType=VARCHAR}, #{curyType,jdbcType=VARCHAR}, ",
            "#{acctType,jdbcType=VARCHAR}, #{acctName,jdbcType=VARCHAR}, ",
            "#{custInfo,jdbcType=VARCHAR}, #{acctStatus,jdbcType=CHAR}, ",
            "#{avlBal,jdbcType=DECIMAL}, #{frzBal,jdbcType=DECIMAL}, ",
            "#{acctBal,jdbcType=DECIMAL}, #{lastUpdDate,jdbcType=CHAR}, ",
            "#{lastAvlBal,jdbcType=DECIMAL}, #{lastFrzBal,jdbcType=DECIMAL}, ",
            "#{todayAvlBal,jdbcType=DECIMAL}, #{todayFrzBal,jdbcType=DECIMAL}, ",
            "#{intBal,jdbcType=DECIMAL}, #{lastIntDate,jdbcType=CHAR}, ",
            "#{sysId,jdbcType=VARCHAR}, #{bdepId,jdbcType=VARCHAR}, #{openDate,jdbcType=CHAR}, ",
            "#{openTime,jdbcType=CHAR})"
    })
    int insert(DtAcctInfo record);

    int insertSelective(DtAcctInfo record);

    @Select({
            "select",
            "CUST_ID, SUB_ACCT_ID, CAP_TYPE, CURY_TYPE, ACCT_TYPE, ACCT_NAME, CUST_INFO, ",
            "ACCT_STATUS, AVL_BAL, FRZ_BAL, ACCT_BAL, LAST_UPD_DATE, LAST_AVL_BAL, LAST_FRZ_BAL, ",
            "TODAY_AVL_BAL, TODAY_FRZ_BAL, INT_BAL, LAST_INT_DATE, SYS_ID, BDEP_ID, OPEN_DATE, ",
            "OPEN_TIME",
            "from DT_ACCT_INFO",
            "where CUST_ID = #{custId,jdbcType=VARCHAR}",
            "and SUB_ACCT_ID = #{subAcctId,jdbcType=VARCHAR}"
    })
    @ResultMap("com.huifu.odin.dal.mapper.DtAcctInfoMapper.BaseResultMap")
    DtAcctInfo selectByPrimaryKey(@Param("custId") String custId, @Param("subAcctId") String subAcctId);


    @Select({
            "select",
            "CUST_ID, SUB_ACCT_ID, CAP_TYPE, CURY_TYPE, ACCT_TYPE, ACCT_NAME, CUST_INFO, ",
            "ACCT_STATUS, AVL_BAL, FRZ_BAL, ACCT_BAL, LAST_UPD_DATE, LAST_AVL_BAL, LAST_FRZ_BAL, ",
            "TODAY_AVL_BAL, TODAY_FRZ_BAL, INT_BAL, LAST_INT_DATE, SYS_ID, BDEP_ID, OPEN_DATE, ",
            "OPEN_TIME",
            "from DT_ACCT_INFO",
            "where CUST_ID = #{custId,jdbcType=VARCHAR}",
            "and SUB_ACCT_ID = #{subAcctId,jdbcType=VARCHAR} for update"
    })
    @ResultMap("com.huifu.odin.dal.mapper.DtAcctInfoMapper.BaseResultMap")
    DtAcctInfo selectByPrimaryKeyForUpdate(@Param("custId") String custId, @Param("subAcctId") String subAcctId);

    int updateByPrimaryKeySelective(DtAcctInfo record);

    @Update({
            "update DT_ACCT_INFO",
            "set CAP_TYPE = #{capType,jdbcType=VARCHAR},",
            "CURY_TYPE = #{curyType,jdbcType=VARCHAR},",
            "ACCT_TYPE = #{acctType,jdbcType=VARCHAR},",
            "ACCT_NAME = #{acctName,jdbcType=VARCHAR},",
            "CUST_INFO = #{custInfo,jdbcType=VARCHAR},",
            "ACCT_STATUS = #{acctStatus,jdbcType=CHAR},",
            "AVL_BAL = #{avlBal,jdbcType=DECIMAL},",
            "FRZ_BAL = #{frzBal,jdbcType=DECIMAL},",
            "ACCT_BAL = #{acctBal,jdbcType=DECIMAL},",
            "LAST_UPD_DATE = #{lastUpdDate,jdbcType=CHAR},",
            "LAST_AVL_BAL = #{lastAvlBal,jdbcType=DECIMAL},",
            "LAST_FRZ_BAL = #{lastFrzBal,jdbcType=DECIMAL},",
            "TODAY_AVL_BAL = #{todayAvlBal,jdbcType=DECIMAL},",
            "TODAY_FRZ_BAL = #{todayFrzBal,jdbcType=DECIMAL},",
            "INT_BAL = #{intBal,jdbcType=DECIMAL},",
            "LAST_INT_DATE = #{lastIntDate,jdbcType=CHAR},",
            "SYS_ID = #{sysId,jdbcType=VARCHAR},",
            "BDEP_ID = #{bdepId,jdbcType=VARCHAR},",
            "OPEN_DATE = #{openDate,jdbcType=CHAR},",
            "OPEN_TIME = #{openTime,jdbcType=CHAR}",
            "where CUST_ID = #{custId,jdbcType=VARCHAR}",
            "and SUB_ACCT_ID = #{subAcctId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(DtAcctInfo record);

    @Update({
            "update DT_ACCT_INFO",
            "set AVL_BAL = AVL_BAL - #{transAmt,jdbcType=DECIMAL} - #{feeAmt,jdbcType=DECIMAL}, ",
            "ACCT_BAL = ACCT_BAL - #{transAmt,jdbcType=DECIMAL} - #{feeAmt,jdbcType=DECIMAL}, ",
            "LAST_UPD_DATE = #{lastUpdateDate,jdbcType=VARCHAR}",
            "where CUST_ID = #{custId,jdbcType=VARCHAR}",
            "and SUB_ACCT_ID = #{acctId,jdbcType=VARCHAR}"
    })
    int updateDAvlBalByPrimaryKey(@Param("transAmt") BigDecimal transAmt, @Param("feeAmt") BigDecimal feeAmt, @Param("custId") String custId, @Param("acctId") String acctId, @Param("lastUpdateDate") String lastUpdateDate);

    @Update({
            "update DT_ACCT_INFO",
            "set AVL_BAL = AVL_BAL + #{transAmt,jdbcType=DECIMAL} - #{feeAmt,jdbcType=DECIMAL}, ",
            "ACCT_BAL = ACCT_BAL + #{transAmt,jdbcType=DECIMAL} - #{feeAmt,jdbcType=DECIMAL}, ",
            "LAST_UPD_DATE = #{lastUpdateDate,jdbcType=VARCHAR}",
            "where CUST_ID = #{custId,jdbcType=VARCHAR}",
            "and SUB_ACCT_ID = #{acctId,jdbcType=VARCHAR}"
    })
    int updateCAvlBalByPrimaryKey(@Param("transAmt") BigDecimal transAmt, @Param("feeAmt") BigDecimal feeAmt, @Param("custId") String custId, @Param("acctId") String acctId, @Param("lastUpdateDate") String lastUpdateDate);

    @Update({
            "update DT_ACCT_INFO",
            "set AVL_BAL = AVL_BAL + #{transAmt,jdbcType=DECIMAL}, ",
            "ACCT_BAL = ACCT_BAL + #{transAmt,jdbcType=DECIMAL}, ",
            "LAST_UPD_DATE = #{lastUpdateDate,jdbcType=VARCHAR}",
            "where CUST_ID = #{custId,jdbcType=VARCHAR}",
            "and SUB_ACCT_ID = #{acctId,jdbcType=VARCHAR}",
            "and (select count(1) from SYS_STAT where SYS_STAT = 'O' and rownum = 1) = 1"
    })
    int updateAvlBalByPrimaryKey(@Param("transAmt") BigDecimal transAmt, @Param("custId") String custId, @Param("acctId") String acctId, @Param("lastUpdateDate") String lastUpdateDate);

    @Update({
            "update DT_ACCT_INFO",
            "set AVL_BAL = AVL_BAL + #{transAmt,jdbcType=DECIMAL}, ",
            "FRZ_BAL = FRZ_BAL - #{transAmt,jdbcType=DECIMAL}, ",
            "LAST_UPD_DATE = #{lastUpdateDate,jdbcType=VARCHAR}",
            "where CUST_ID = #{custId,jdbcType=VARCHAR}",
            "and SUB_ACCT_ID = #{acctId,jdbcType=VARCHAR}",
            "and (select count(1) from SYS_STAT where SYS_STAT = 'O' and rownum = 1) = 1"
    })
    int updateUnfreezeAvlBalByPrimaryKey(@Param("transAmt") BigDecimal transAmt, @Param("custId") String custId, @Param("acctId") String acctId, @Param("lastUpdateDate") String lastUpdateDate);

    @Update({
            "update DT_ACCT_INFO",
            "set FRZ_BAL = FRZ_BAL + #{transAmt,jdbcType=DECIMAL}, ",
            "AVL_BAL = AVL_BAL - #{transAmt,jdbcType=DECIMAL}, ",
            "LAST_UPD_DATE = #{lastUpdateDate,jdbcType=VARCHAR}",
            "where CUST_ID = #{custId,jdbcType=VARCHAR}",
            "and SUB_ACCT_ID = #{acctId,jdbcType=VARCHAR}",
            "and (select count(1) from SYS_STAT where SYS_STAT = 'O' and rownum = 1) = 1"
    })
    int updateFreezeAvlBalByPrimaryKey(@Param("transAmt") BigDecimal transAmt, @Param("custId") String custId, @Param("acctId") String acctId, @Param("lastUpdateDate") String lastUpdateDate);


    List<DtAcctInfo> queryDtAcctInfo(@Param("acctInfo") DtAcctInfo acctInfo);
}