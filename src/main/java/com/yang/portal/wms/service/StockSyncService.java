package com.yang.portal.wms.service;

import com.yang.portal.security.core.jwt.JWTToken;
import com.yang.portal.wms.service.impl.stock.vo.InsertFlowVo;

public interface StockSyncService {
    void initStockData(InsertFlowVo insertFlowVo, long amount, String productStockKey, JWTToken jwtToken);

    void updateStockData(InsertFlowVo insertFlowVo, String productStockKey, long amount, JWTToken jwtToken);
}
