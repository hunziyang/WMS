package com.yang.portal.wms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("STOCK")
public class Stock extends BaseEntity{

    @TableField("PRODUCT_ID")
    private Long productId;

    @TableField("STOCK")
    private Long stock;

}
