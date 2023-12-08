package com.yang.portal.wms.service;

import com.yang.portal.security.core.jwt.JWTToken;
import com.yang.portal.wms.service.impl.product.ProductCreateRequest;
import com.yang.portal.wms.service.impl.product.ProductSearchRequest;
import com.yang.portal.wms.service.impl.product.ProductSearchResponse;
import com.yang.portal.wms.service.impl.product.ProductUpdateRequest;

import java.util.List;

public interface ProductService {
    void insertProduct(ProductCreateRequest request, JWTToken jwtToken);

    void updateProduct(Long id, ProductUpdateRequest request, JWTToken jwtToken);

    void updateProductName(Long id, ProductUpdateRequest request, JWTToken jwtToken);

    List<ProductSearchResponse> search(ProductSearchRequest request);

    void updateVision(Long id, ProductUpdateRequest request);
}
