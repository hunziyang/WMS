package com.yang.portal.wms.service.impl.product.vo;

import com.yang.portal.wms.entity.DateRange;
import com.yang.portal.wms.util.EsSearchCriteria;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductMultiSearchVo extends EsSearchCriteria {

    @NotBlank(message = "content 不能为空")
    private String content;
    private DateRange createTime;
}
