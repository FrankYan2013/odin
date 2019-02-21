package com.huifu.odin.dal.mapper;

import com.huifu.odin.dal.entity.FrzLog;
import com.huifu.odin.dal.entity.FrzLogKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface FrzLogMapper {
    int deleteByPrimaryKey(FrzLogKey key);

    int insert(FrzLog record);

    int insertSelective(FrzLog record);

    FrzLog selectByPrimaryKey(FrzLogKey key);

    int updateByPrimaryKeySelective(FrzLog record);

    int updateByPrimaryKey(FrzLog record);

    FrzLog queryByUniqueKey(@Param("acctDate") String acctDate, @Param("acctSeqId") String acctSeqId);

    @Select({
            "select to_char(systimestamp,'yyyyMMddhh24missff3') from dual"
    })
    String selectDbDateTime();
}