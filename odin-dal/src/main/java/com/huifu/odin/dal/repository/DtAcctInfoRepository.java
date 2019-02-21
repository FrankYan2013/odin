package com.huifu.odin.dal.repository;


import com.huifu.odin.dal.entity.DtAcctInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author frank
 */
public interface DtAcctInfoRepository {

    List<DtAcctInfo> queryDtAcctInfo(@Param("dtAcctInfo") DtAcctInfo dtAcctInfo);


}
