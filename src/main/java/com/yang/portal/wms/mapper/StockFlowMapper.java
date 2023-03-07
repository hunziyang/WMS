package com.yang.portal.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.portal.wms.entity.StockFlow;
import com.yang.portal.wms.service.impl.stock.vo.GetStockFlowVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockFlowMapper extends BaseMapper<StockFlow> {
    List<StockFlow> getStockFlow(@Param("query") GetStockFlowVo getStockFlowVo,@Param("start") Integer start,@Param("end") Integer end);
}
