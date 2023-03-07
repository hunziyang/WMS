package com.yang.portal.wms.service.impl.product.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductInsertVo {

    @NotBlank(message = "name 不能为空")
    private String name;

    @NotBlank(message = "code 不能为空")
    private String code;
    private String description;
}
