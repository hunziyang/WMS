package com.yang.portal.wms.service.impl.stock.vo;

import com.yang.portal.core.utils.Pagination;
import com.yang.portal.wms.entity.DateRange;
import lombok.Data;

@Data
public class GetStockFlowVo {

    private Long productId;
    private String number;
    private String createBy;
    private DateRange createAt;
    private Pagination pagination =new Pagination();
    private Boolean orderByCreateAtASC;
}
