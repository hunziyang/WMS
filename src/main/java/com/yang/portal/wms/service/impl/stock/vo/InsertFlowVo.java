package com.yang.portal.wms.service.impl.stock.vo;

import com.yang.portal.wms.service.impl.stock.StockFlowType;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class InsertFlowVo {

    private Long productId;
    private StockFlowType type;
    @Min(1)
    private Long amount;
}
