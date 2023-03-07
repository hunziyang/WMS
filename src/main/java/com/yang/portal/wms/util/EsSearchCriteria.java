package com.yang.portal.wms.util;

import com.yang.portal.core.CoreConstant;
import com.yang.portal.core.utils.Pagination;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Data
public class EsSearchCriteria {

    protected Pagination pagination = new Pagination();

    protected Sort buildSort(){
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        pagination.getOrderBy().forEach(orderBy -> {
            orderBy = orderBy.replaceFirst("^\\+", "");
            if (orderBy.startsWith(CoreConstant.Pagination.DESC_SYMBOL)) {
                orders.add(Sort.Order.desc(orderBy.replaceFirst("^-", "")));
            } else {
                orders.add(Sort.Order.asc(orderBy));
            }
        });

        return Sort.by(orders);
    }

    public PageRequest buildPageRequest() {
        if (ObjectUtils.isEmpty(pagination.getOrderBy())){
            return PageRequest.of(pagination.getPageNum()-1, pagination.getPageSize());
        }
        return PageRequest.of(pagination.getPageNum() -1, pagination.getPageSize(),buildSort());
    }
}
