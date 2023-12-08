package com.yang.portal.wms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.portal.core.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@TableName("SKU")
@Data
@Accessors(chain = true)
public class Sku extends BaseEntity {

    private Long productId;
    private String sku;
    private String value;
    private Integer uniqueId;
}
