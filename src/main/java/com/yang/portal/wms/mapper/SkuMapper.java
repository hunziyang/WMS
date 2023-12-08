package com.yang.portal.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.portal.wms.entity.Sku;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SkuMapper extends BaseMapper<Sku> {

    List<Sku> getSkuByProductId(@Param("productId") Long productId);
}
