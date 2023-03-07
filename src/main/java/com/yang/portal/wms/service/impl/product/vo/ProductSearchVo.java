package com.yang.portal.wms.service.impl.product.vo;

import com.yang.portal.wms.entity.DateRange;
import com.yang.portal.wms.util.EsSearchCriteria;
import lombok.Data;

@Data
public class ProductSearchVo extends EsSearchCriteria {

    private String content;

    private DateRange createTime;
}
