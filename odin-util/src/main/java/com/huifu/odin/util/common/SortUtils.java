package com.huifu.odin.util.common;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author frank
 */
public class SortUtils {

    /**
     * 正序
     */
    public static final String SORT_ORDER_ASC = "asc";

    /**
     * 逆序
     */
    public static final String SORT_ORDER_DESC = "desc";

    public static final String SORT_DC_FLAG = "dcFlag";

    public static final String SORT_CUST_ID = "custId";

    public static final String SORT_SUB_ACCT_ID = "subAcctId";

    public static final String SORT_DC_FLAG_PYXIS_KEY = "sort.dcFlag.switch";

    public static final boolean SORT_DC_FLAG_PYXIS_DEFAULT_VALUE = false;

    public static void sortList(List list, ArrayList<String> sortFields, String sortOrder) {
        if (sortFields == null || sortFields.size() <= 0) {
            return;
        }

        ArrayList sorts = new ArrayList();

        Comparator c = ComparableComparator.getInstance();
        c = ComparatorUtils.nullLowComparator(c);
        if (StringUtils.equals(sortOrder, SORT_ORDER_DESC)) {
            c = ComparatorUtils.reversedComparator(c);
        }

        String sortField = null;
        for (int i = 0; i < sortFields.size(); i++) {
            sortField = sortFields.get(i);
            if (StringUtils.isNotEmpty(sortField)) {
                sorts.add(new BeanComparator(sortField, c));
            }
        }

        ComparatorChain multiSort = new ComparatorChain(sorts);

        Collections.sort(list, multiSort);
    }
}
