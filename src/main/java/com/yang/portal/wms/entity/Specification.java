package com.yang.portal.wms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.portal.core.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@TableName("SPECIFICATION")
@Data
@Accessors(chain = true)
public class Specification extends BaseEntity {

    private Long productId;
    private String name;
    private Integer uniqueId;

}
