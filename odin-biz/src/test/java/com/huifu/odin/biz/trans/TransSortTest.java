package com.huifu.odin.biz.trans;

import com.huifu.odin.facade.service.trans.AcctTransRequestDetailDTO;
import com.huifu.odin.util.common.SortUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class TransSortTest {

    List<AcctTransRequestDetailDTO> acctTransDetailList = new ArrayList<AcctTransRequestDetailDTO>();

    @Before
    public void 准备数据() {
        AcctTransRequestDetailDTO dto = new AcctTransRequestDetailDTO();
        dto.setCustId("000000000000286");
        dto.setSubAcctId("115");
        acctTransDetailList.add(dto);

        AcctTransRequestDetailDTO dto2 = new AcctTransRequestDetailDTO();
        dto2.setCustId("000000000000286");
        dto2.setSubAcctId("113");
        acctTransDetailList.add(dto2);

        AcctTransRequestDetailDTO dto3 = new AcctTransRequestDetailDTO();
        dto3.setCustId("000000000000285");
        dto3.setSubAcctId("114");
        acctTransDetailList.add(dto3);

        AcctTransRequestDetailDTO dto4 = new AcctTransRequestDetailDTO();
        dto4.setCustId("000000000000288");
        dto4.setSubAcctId("115");
        acctTransDetailList.add(dto4);
    }

    @Test
    public void 测试排序() {

        for (AcctTransRequestDetailDTO acctTransRequestDetailDTO : acctTransDetailList) {
            System.out.println(acctTransRequestDetailDTO.getCustId());
            System.out.println(acctTransRequestDetailDTO.getSubAcctId());
        }
        ArrayList<String> sortFields = new ArrayList<>();
        sortFields.add(SortUtils.SORT_CUST_ID);
        SortUtils.sortList(acctTransDetailList, sortFields, SortUtils.SORT_ORDER_ASC);

        for (AcctTransRequestDetailDTO acctTransRequestDetailDTO : acctTransDetailList) {
            System.out.println(acctTransRequestDetailDTO.getCustId());
            System.out.println(acctTransRequestDetailDTO.getSubAcctId());
        }

    }


}
