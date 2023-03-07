package com.yang.portal.wms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yang.portal.security.core.jwt.JWTToken;
import com.yang.portal.wms.entity.Product;
import com.yang.portal.wms.service.impl.product.vo.ProductInsertVo;
import com.yang.portal.wms.service.impl.product.vo.ProductSearchVo;
import com.yang.portal.wms.service.impl.product.vo.ProductUpsertVo;

/**
 * 商品表(Product)表服务接口
 *
 * @author makejava
 * @since 2023-02-19 21:29:48
 */
public interface ProductService {

    void insertProduct(ProductInsertVo productInsertVo, JWTToken jwtToken);

    void upsertProduct(Long id, ProductUpsertVo productUpsertVo, JWTToken jwtToken);

    IPage<Product> search(ProductSearchVo content);

    void delete(Long id, JWTToken jwtToken);
}
