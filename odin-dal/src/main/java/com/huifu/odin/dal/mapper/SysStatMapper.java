package com.huifu.odin.dal.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * @author frank
 */
public interface SysStatMapper {

    @Select({
            "select count(1) from SYS_STAT where SYS_STAT = 'O' and rownum = 1"
    })
    int selectSysStat();
}