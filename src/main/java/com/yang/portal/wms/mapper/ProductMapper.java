package com.yang.portal.wms.mapper;

import com.yang.portal.core.mybatis.YangMapper;
import com.yang.portal.wms.entity.Product;
import com.yang.portal.wms.service.impl.product.ProductSearchRequest;
import com.yang.portal.wms.service.impl.product.ProductSearchResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品(Product)表数据库访问层
 *
 * @author makejava
 * @since 2023-11-16 21:10:54
 */
public interface ProductMapper extends YangMapper<Product> {
    List<ProductSearchResponse> search(@Param("query") ProductSearchRequest request, @Param("start") Integer start, @Param("end") Integer end);
}

