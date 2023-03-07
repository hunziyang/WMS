package com.yang.portal.wms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.portal.wms.service.impl.stock.StockFlowType;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("STOCK_FLOW")
public class StockFlow extends BaseEntity {

    @TableField("PRODUCT_ID")
    private Long productId;

    @TableField("NUMBER")
    private String number;

    @TableField("AMOUNT")
    private Long amount;

    @TableField("TYPE")
    private StockFlowType type;
}
