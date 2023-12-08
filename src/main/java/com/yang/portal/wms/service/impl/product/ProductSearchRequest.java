package com.yang.portal.wms.service.impl.product;

import com.yang.portal.core.utils.Pagination;
import lombok.Data;

@Data
public class ProductSearchRequest {

    private String name;
    private String code;
    private Boolean orderByCreatedTimeDesc = true;
    private Pagination pagination = new Pagination();
}
