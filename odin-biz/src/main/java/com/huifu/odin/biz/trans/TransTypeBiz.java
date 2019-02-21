package com.huifu.odin.biz.trans;

import com.huifu.odin.dal.entity.TransType;
import com.huifu.odin.dal.mapper.TransTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author frank
 */
@Service
public class TransTypeBiz {

    public static HashMap<String, TransType> transTypeHashMap = new HashMap<>();


    @Autowired
    TransTypeMapper transTypeMapper;

    public TransType getTransTypeByTransTypeCode(String transTypeCode) {
        TransType transType = transTypeHashMap.get(transTypeCode);
        if (transType == null) {
            TransType transTypeInDB = transTypeMapper.selectByPrimaryKey(transTypeCode);
            if (transTypeInDB == null) {
                throw new TransException(TransExceptionEnum.TransTypeException);
            }
            transTypeHashMap.put(transTypeCode, transTypeInDB);
            return transTypeInDB;
        } else {
            return transType;
        }
    }


}
