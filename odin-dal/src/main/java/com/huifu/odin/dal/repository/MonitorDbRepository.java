package com.huifu.odin.dal.repository;


import org.apache.ibatis.annotations.Select;

/**
 * @author frank
 */
public interface MonitorDbRepository {

    @Select({
            "select 1 from dual"
    })
    String select1();


}
