package com.yang.portal.wms.mapper;

import com.yang.portal.core.mybatis.YangMapper;
import com.yang.portal.wms.entity.Specification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SpecificationMapper extends YangMapper<Specification> {

    List<String> getSpecificationByProductId(@Param("productId") Long productId);
}
