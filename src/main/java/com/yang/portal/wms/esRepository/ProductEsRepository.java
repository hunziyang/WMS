package com.yang.portal.wms.esRepository;

import com.yang.portal.wms.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEsRepository extends ElasticsearchRepository<Product, Long> {

    Product findOneByCodeAndIsDelete(String code,Boolean isDelete);
}
