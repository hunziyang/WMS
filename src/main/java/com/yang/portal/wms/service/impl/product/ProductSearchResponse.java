package com.yang.portal.wms.service.impl.product;

import com.yang.portal.wms.entity.Product;
import com.yang.portal.wms.entity.Sku;
import com.yang.portal.wms.entity.Specification;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ProductSearchResponse extends Product {

    private List<Sku> skus;
    private List<Specification> specifications;
}
