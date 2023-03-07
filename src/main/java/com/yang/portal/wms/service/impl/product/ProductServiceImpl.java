package com.yang.portal.wms.service.impl.product;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.portal.core.CoreConstant;
import com.yang.portal.security.core.jwt.JWTToken;
import com.yang.portal.wms.entity.Product;
import com.yang.portal.wms.mapper.ProductMapper;
import com.yang.portal.wms.service.ProductService;
import com.yang.portal.wms.service.impl.product.vo.ProductInsertVo;
import com.yang.portal.wms.service.impl.product.vo.ProductSearchVo;
import com.yang.portal.wms.service.impl.product.vo.ProductUpsertVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void insertProduct(ProductInsertVo productInsertVo, JWTToken jwtToken) {
        Product product = new Product();
        BeanUtils.copyProperties(productInsertVo, product);
        product.setCreatedBy(jwtToken.getUserPrincipal().getNickName())
                .setCreatedId(jwtToken.getUserPrincipal().getUserId());
        productMapper.insert(product);
    }

    @Override
    public void upsertProduct(Long id, ProductUpsertVo productUpsertVo, JWTToken jwtToken) {
        Product product = productMapper.selectById(id);
        Optional.of(productUpsertVo.getName()).ifPresent(product::setName);
        Optional.of(productUpsertVo.getDescription()).ifPresent(product::setDescription);
        product.setUpdatedBy(jwtToken.getUserPrincipal().getNickName())
                .setUpdatedId(jwtToken.getUserPrincipal().getUserId());
        productMapper.updateById(product);
    }

    @Override
    public IPage<Product> search(ProductSearchVo productSearchVo) {

        PageRequest pageRequest = productSearchVo.buildPageRequest();
        BoolQueryBuilder builder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("IS_DELETE", false));
        if (StringUtils.isNotBlank(productSearchVo.getContent())){
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(productSearchVo.getContent())
                    .field("CODE", 3.0f)
                    .field("NAME", 2.0f)
                    .field("DESCRIPTION", AbstractQueryBuilder.DEFAULT_BOOST);
            builder.must(multiMatchQueryBuilder);
        }
        Optional.ofNullable(productSearchVo.getCreateTime()).ifPresent(dateRange -> {
            RangeQueryBuilder createdTime = QueryBuilders.rangeQuery("CREATED_TIME");
            if (ObjectUtils.isNotEmpty(dateRange.getStartDate())) {
                createdTime.gte(dateRange.getStartDate());
            }
            if (ObjectUtils.isNotEmpty(dateRange.getEndDate())) {
                createdTime.lte(dateRange.getEndDate());
            }
            builder.must(createdTime);
        });
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .withPageable(pageRequest);
        List<SearchHit<Product>> searchHits = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), Product.class).getSearchHits();
        List<Product> collect = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        IPage<Product> productPage = new Page<>(
                pageRequest.getPageNumber() + 1,
                pageRequest.getPageSize(),
                elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), Product.class).getTotalHits()
        );
        productPage.setRecords(collect);
        return productPage;
    }

    @Override
    public void delete(Long id, JWTToken jwtToken) {
        LambdaUpdateWrapper<Product> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Product::getId, id)
                .eq(Product::getIsDelete, CoreConstant.IS_DELETE)
                .set(Product::getIsDelete, !CoreConstant.IS_DELETE)
                .set(Product::getUpdatedId, jwtToken.getUserPrincipal().getUserId())
                .set(Product::getUpdatedBy, jwtToken.getUserPrincipal().getNickName())
                .set(Product::getUpdatedTime, LocalDateTime.now());
        productMapper.delete(lambdaUpdateWrapper);
    }
}
