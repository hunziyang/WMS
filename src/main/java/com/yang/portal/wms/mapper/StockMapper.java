package com.yang.portal.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.portal.security.core.jwt.JWTToken;
import com.yang.portal.wms.entity.Stock;
import org.apache.ibatis.annotations.Param;

import java.time.ZonedDateTime;

public interface StockMapper extends BaseMapper<Stock> {

    int updateStock(
            @Param("amount") long amount, @Param("productId") Long productId,
            @Param("isDelete") Boolean isDelete, @Param("jwtToken") JWTToken jwtToken, ZonedDateTime now);
}
