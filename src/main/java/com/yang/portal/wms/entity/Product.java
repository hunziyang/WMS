package com.yang.portal.wms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.portal.core.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品(Product)实体类
 *
 * @author makejava
 * @since 2023-11-16 21:12:18
 */
@TableName("PRODUCT")
@Data
@Accessors(chain = true)
public class Product extends BaseEntity {
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品编码
     */
    private String code;
    /**
     * 唯一ID
     */
    private Integer uniqueId;
}

