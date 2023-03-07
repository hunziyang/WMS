package com.yang.portal.wms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yang.portal.core.annotation.YangController;
import com.yang.portal.core.result.Result;
import com.yang.portal.security.core.jwt.JWTToken;
import com.yang.portal.wms.entity.StockFlow;
import com.yang.portal.wms.service.StockService;
import com.yang.portal.wms.service.impl.stock.vo.GetStockFlowVo;
import com.yang.portal.wms.service.impl.stock.vo.InsertFlowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@YangController("/stock")
public class StockController {


    @Autowired
    private StockService stockService;

    @PostMapping
    public Result insertFlow(@Validated @RequestBody InsertFlowVo insertFlowVo, JWTToken jwtToken){
        stockService.insertFlow(insertFlowVo,jwtToken);
        return Result.success();
    }

    @GetMapping("/getStockFlow")
    public Result getStockFlow(GetStockFlowVo getStockFlowVo){
        IPage<StockFlow> stockFlow = stockService.getStockFlow(getStockFlowVo);
        return Result.success(stockFlow);
    }
}
