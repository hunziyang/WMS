package com.yang.portal.wms.controller;

import com.yang.portal.core.result.Result;
import com.yang.portal.security.annotation.AnonymousController;
import com.yang.portal.security.core.jwt.JWTToken;
import com.yang.portal.wms.service.ProductService;
import com.yang.portal.wms.service.impl.product.ProductCreateRequest;
import com.yang.portal.wms.service.impl.product.ProductSearchRequest;
import com.yang.portal.wms.service.impl.product.ProductUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@AnonymousController("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public Result insertProduct(@Validated @RequestBody ProductCreateRequest request, @ApiIgnore JWTToken jwtToken) {
        productService.insertProduct(request, jwtToken);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result updateProduct(@PathVariable Long id, @Validated @RequestBody ProductUpdateRequest request, @ApiIgnore JWTToken jwtToken) {
        productService.updateProduct(id, request, jwtToken);
        return Result.success();
    }

    @PutMapping("/{id}/update")
    public Result updateProductName(@PathVariable Long id, @Validated @RequestBody ProductUpdateRequest request, @ApiIgnore JWTToken jwtToken) {
        productService.updateProductName(id, request, jwtToken);
        return Result.success();
    }

    @GetMapping()
    public Result search(ProductSearchRequest request) {
        return Result.success(productService.search(request));
    }

    @PutMapping("/{id}/updateVision")
    public Result updateVision(@PathVariable Long id, @Validated @RequestBody ProductUpdateRequest request) {
        productService.updateVision(id,request);
        return Result.success();
    }
}
