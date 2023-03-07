package com.yang.portal.wms.controller;

import com.yang.portal.core.annotation.YangController;
import com.yang.portal.core.result.Result;
import com.yang.portal.security.core.jwt.JWTToken;
import com.yang.portal.wms.service.ProductService;
import com.yang.portal.wms.service.impl.product.vo.ProductInsertVo;
import com.yang.portal.wms.service.impl.product.vo.ProductSearchVo;
import com.yang.portal.wms.service.impl.product.vo.ProductUpsertVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 商品表(Product)表控制层
 *
 * @author makejava
 * @since 2023-02-19 21:29:45
 */
@YangController("/product")
@Api("商品")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("创建商品")
    @PostMapping("/insert")
    @PreAuthorize("hasPermission('product','insert')")
    public Result insertProduct(@RequestBody ProductInsertVo productInsertVo, JWTToken jwtToken) {
        productService.insertProduct(productInsertVo, jwtToken);
        return Result.success();
    }

    @ApiOperation("更新商品")
    @PutMapping("/{id}/upsert")
    @PreAuthorize("hasPermission('product','upsert')")
    public Result upsertProduct(@PathVariable("id") Long id, @RequestBody ProductUpsertVo productUpsertVo, JWTToken jwtToken) {
        productService.upsertProduct(id, productUpsertVo, jwtToken);
        return Result.success();
    }

    @ApiOperation("根据姓名、编码、描述进行查询")
    @GetMapping("/search")
    @PreAuthorize("hasPermission('product','select')")
    public Result search(ProductSearchVo productSearchVo){
        return Result.success(productService.search(productSearchVo));
    }

    @ApiOperation("删除商品")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission('product','delete')")
    public Result delete(@PathVariable("id") Long id, JWTToken jwtToken){
        productService.delete(id,jwtToken);
        return Result.success();
    }

}

