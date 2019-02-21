package com.huifu.odin.dal.mapper;

import com.huifu.odin.dal.entity.TransType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TransTypeMapper {
    @Delete({
        "delete from TRANS_TYPE",
        "where TRANS_TYPE = #{transType,jdbcType=CHAR}"
    })
    int deleteByPrimaryKey(String transType);

    @Insert({
        "insert into TRANS_TYPE (TRANS_TYPE, DC_FLAG, ",
        "TRANS_DESC)",
        "values (#{transType,jdbcType=CHAR}, #{dcFlag,jdbcType=CHAR}, ",
        "#{transDesc,jdbcType=VARCHAR})"
    })
    int insert(TransType record);

    int insertSelective(TransType record);

    @Select({
        "select",
        "TRANS_TYPE, DC_FLAG, TRANS_DESC",
        "from TRANS_TYPE",
        "where TRANS_TYPE = #{transType,jdbcType=CHAR}"
    })
    @ResultMap("com.huifu.odin.dal.mapper.TransTypeMapper.BaseResultMap")
    TransType selectByPrimaryKey(String transType);

    int updateByPrimaryKeySelective(TransType record);

    @Update({
        "update TRANS_TYPE",
        "set DC_FLAG = #{dcFlag,jdbcType=CHAR},",
          "TRANS_DESC = #{transDesc,jdbcType=VARCHAR}",
        "where TRANS_TYPE = #{transType,jdbcType=CHAR}"
    })
    int updateByPrimaryKey(TransType record);
}