package com.yang.portal.wms.service.impl.product;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class ProductCreateRequest {

    @NotBlank(message = "name 不能为空")
    private String name;
    @NotBlank(message = "code 不能为空")
    private String code;
    @NotEmpty(message = "sku 不能为空")
    private List<@Valid SkuDetail> sku;
    @NotEmpty(message = "specification 不能为空")
    private List<@Valid SpecificationDetail> specification;


    @Data
    public static class SkuDetail {
        @NotBlank(message = "sku value 不能为空")
        private String value;
    }

    @Data
    public static class SpecificationDetail{
        @NotBlank(message = "specification name 不能为空")
        private String name;
    }
}
