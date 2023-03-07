package com.yang.portal.wms.service.impl.stock;

import com.yang.portal.core.CoreConstant;
import com.yang.portal.security.core.jwt.JWTToken;
import com.yang.portal.wms.entity.Stock;
import com.yang.portal.wms.entity.StockFlow;
import com.yang.portal.wms.exception.stock.UpdateStockException;
import com.yang.portal.wms.mapper.StockFlowMapper;
import com.yang.portal.wms.mapper.StockMapper;
import com.yang.portal.wms.service.StockSyncService;
import com.yang.portal.wms.service.impl.stock.vo.InsertFlowVo;
import com.yang.portal.wms.util.DateUtil;
import com.yang.portal.wms.util.GenerationNumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class StockSyncServiceImpl implements StockSyncService {

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockFlowMapper stockFlowMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String NUMBER_PREFIX = "T";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initStockData(InsertFlowVo insertFlowVo, long amount, String productStockKey, JWTToken jwtToken) {
        insertStockFlow(insertFlowVo, jwtToken);
        Stock stock = new Stock()
                .setStock(amount)
                .setProductId(insertFlowVo.getProductId());
        stock.setCreatedId(jwtToken.getUserPrincipal().getUserId())
                .setCreatedBy(jwtToken.getUserPrincipal().getNickName());
        stockMapper.insert(stock);
        redisTemplate.opsForValue().set(productStockKey, amount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStockData(InsertFlowVo insertFlowVo, String productStockKey, long amount, JWTToken jwtToken) {
        insertStockFlow(insertFlowVo, jwtToken);
        int num = stockMapper.updateStock(amount, insertFlowVo.getProductId(), CoreConstant.IS_DELETE, jwtToken, DateUtil.getNow());
        if (num == 0) {
            log.warn("{} update stock err", insertFlowVo.getProductId());
            throw new UpdateStockException();
        }
        redisTemplate.opsForValue().increment(productStockKey, amount);
    }

    private void insertStockFlow(InsertFlowVo insertFlowVo, JWTToken jwtToken) {
        StockFlow stockFlow = new StockFlow();
        BeanUtils.copyProperties(insertFlowVo, stockFlow);
        stockFlow.setNumber(GenerationNumberUtil.getNumber(NUMBER_PREFIX))
                .setCreatedId(jwtToken.getUserPrincipal().getUserId())
                .setCreatedBy(jwtToken.getUserPrincipal().getNickName());
        stockFlowMapper.insert(stockFlow);
    }
}
