/**
 *
 */
package com.huifu.odin.dal.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * @author anxiang.liu_c
 */
public interface SequenceMapper {

    @Select(
            "select FRZ_LOG_SEQ.NEXTVAL from DUAL"
    )
    String getFrzLogSeq();

}
