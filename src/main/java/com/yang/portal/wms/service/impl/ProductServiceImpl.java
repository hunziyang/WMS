package com.yang.portal.wms.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yang.portal.security.core.jwt.JWTToken;
import com.yang.portal.wms.entity.Product;
import com.yang.portal.wms.entity.Sku;
import com.yang.portal.wms.entity.Specification;
import com.yang.portal.wms.mapper.ProductMapper;
import com.yang.portal.wms.mapper.SkuMapper;
import com.yang.portal.wms.mapper.SpecificationMapper;
import com.yang.portal.wms.service.ProductService;
import com.yang.portal.wms.service.impl.product.ProductCreateRequest;
import com.yang.portal.wms.service.impl.product.ProductSearchRequest;
import com.yang.portal.wms.service.impl.product.ProductSearchResponse;
import com.yang.portal.wms.service.impl.product.ProductUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertProduct(ProductCreateRequest request, JWTToken jwtToken) {
        Product product = new Product().setName(request.getName()).setCode(request.getCode());
        product.setCreatedId(jwtToken.getUserPrincipal().getUserId())
                        .setCreatedBy(jwtToken.getUserPrincipal().getNickName());
        productMapper.insert(product);
        request.getSku().forEach(skuDetail -> {
            Sku sku = new Sku().setProductId(product.getId()).setSku(IdUtil.simpleUUID()).setValue(skuDetail.getValue());
            sku.setCreatedId(jwtToken.getUserPrincipal().getUserId())
                    .setCreatedBy(jwtToken.getUserPrincipal().getNickName());
            skuMapper.insert(sku);
        });
        request.getSpecification().forEach(specificationDetail -> {
            Specification specification = new Specification().setProductId(product.getId()).setName(specificationDetail.getName());
            specification.setCreatedId(jwtToken.getUserPrincipal().getUserId())
                    .setCreatedBy(jwtToken.getUserPrincipal().getNickName());
            specificationMapper.insert(specification);
        });
    }

    @Override
    public void updateProduct(Long id, ProductUpdateRequest request, JWTToken jwtToken) {
        Product product = new Product().setCode(request.getCode()).setName(request.getName());
        product.setUpdatedId(jwtToken.getUserPrincipal().getUserId())
                .setUpdatedBy(jwtToken.getUserPrincipal().getNickName())
                        .setId(id);
        productMapper.updateById(product);
    }

    @Override
    public void updateProductName(Long id, ProductUpdateRequest request, JWTToken jwtToken) {
        LambdaUpdateWrapper<Product> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Product::getId,id)
                .eq(Product::getIsDelete,false)
                .set(Product::getName,request.getName())
                .set(Product::getCode,request.getCode());
        productMapper.update(lambdaUpdateWrapper);
    }

    @Override
    public List<ProductSearchResponse> search(ProductSearchRequest request) {
        return productMapper.search(request,request.getPagination().getOffset(),request.getPagination().getPageSize());
    }

    @Override
    public void updateVision(Long id, ProductUpdateRequest request) {
        Product product = productMapper.selectById(id);
        product.setCode(request.getCode())
                .setName(request.getName());
        productMapper.updateById(product);
    }
}
