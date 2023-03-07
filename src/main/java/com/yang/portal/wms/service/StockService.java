package com.yang.portal.wms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yang.portal.security.core.jwt.JWTToken;
import com.yang.portal.wms.entity.StockFlow;
import com.yang.portal.wms.service.impl.stock.vo.GetStockFlowVo;
import com.yang.portal.wms.service.impl.stock.vo.InsertFlowVo;

public interface StockService {
    void insertFlow(InsertFlowVo insertFlowVo, JWTToken jwtToken);

    IPage<StockFlow> getStockFlow(GetStockFlowVo getStockFlowVo);
}
