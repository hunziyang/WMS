package com.yang.portal.wms.service.impl.stock;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.portal.security.core.jwt.JWTToken;
import com.yang.portal.wms.Constant;
import com.yang.portal.wms.entity.Stock;
import com.yang.portal.wms.entity.StockFlow;
import com.yang.portal.wms.exception.stock.InsertFlowException;
import com.yang.portal.wms.exception.stock.StockInitErrorException;
import com.yang.portal.wms.mapper.StockFlowMapper;
import com.yang.portal.wms.mapper.StockMapper;
import com.yang.portal.wms.service.StockService;
import com.yang.portal.wms.service.StockSyncService;
import com.yang.portal.wms.service.impl.stock.vo.GetStockFlowVo;
import com.yang.portal.wms.service.impl.stock.vo.InsertFlowVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class StockServiceImpl implements StockService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StockSyncService stockSyncService;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockFlowMapper stockFlowMapper;

    @Override
    public void insertFlow(InsertFlowVo insertFlowVo, JWTToken jwtToken) {
        String productStockLock = String.format(Constant.Stock.REDIS_PRODUCT_STOCK_LOCK, insertFlowVo.getProductId());
        if (redisTemplate.hasKey(productStockLock)) {
            log.warn("{},stock initializing", insertFlowVo.getProductId());
            throw new InsertFlowException();
        }
        long amount = StockFlowType.INCREASE.equals(insertFlowVo.getType()) ?
                insertFlowVo.getAmount() : -insertFlowVo.getAmount();
        String productStockKey = String.format(Constant.Stock.REDIS_PRODUCT_STOCK, insertFlowVo.getProductId());
        if (!redisTemplate.hasKey(productStockKey)) {
            try {
                Boolean absent = redisTemplate.opsForValue().setIfAbsent(productStockLock, true, 30L, TimeUnit.MINUTES);
                if (!absent) {
                    log.warn("{},stock initializing", insertFlowVo.getProductId());
                    throw new InsertFlowException();
                }
                if (ObjectUtils.isEmpty(getStock(insertFlowVo))) {
                    initStockData(insertFlowVo, amount, productStockKey, jwtToken);
                    return;
                } else {
                    if (!redisTemplate.hasKey(productStockKey)) {
                        log.warn("{},stock exist but redis not exists", insertFlowVo.getProductId());
                        throw new InsertFlowException();
                    }
                }
            } catch (Exception e) {
                log.warn("init stock err", insertFlowVo.getProductId(), e);
                throw e;
            } finally {
                redisTemplate.delete(productStockLock);
            }
        }
        stockSyncService.updateStockData(insertFlowVo, productStockKey, amount, jwtToken);
    }

    /**
     * 当库存为第一次插入时
     *
     * @param insertFlowVo
     * @param amount
     * @param productStockKey
     * @param jwtToken
     */
    private void initStockData(InsertFlowVo insertFlowVo, long amount, String productStockKey, JWTToken jwtToken) {
        if (amount < 0) {
            log.warn("{},stock amount is negative number", insertFlowVo.getProductId());
            throw new InsertFlowException();
        }
        try {
            stockSyncService.initStockData(insertFlowVo, amount, productStockKey, jwtToken);
        } catch (Exception e) {
            log.warn("{},stock init error", insertFlowVo.getProductId(), e);
            throw new StockInitErrorException();
        }
    }

    private Stock getStock(InsertFlowVo insertFlowVo) {
        LambdaQueryWrapper<Stock> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Stock::getProductId, insertFlowVo.getProductId());
        return stockMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public IPage<StockFlow> getStockFlow(GetStockFlowVo getStockFlowVo) {
        List<StockFlow> stockFlow = stockFlowMapper.getStockFlow(getStockFlowVo, getStockFlowVo.getPagination().getOffset(), getStockFlowVo.getPagination().getPageSize());
        IPage<StockFlow> stockFlowIPage = new Page<>(
                getStockFlowVo.getPagination().getPageNum().longValue(),
                getStockFlowVo.getPagination().getPageSize().longValue());
        stockFlowIPage.setRecords(stockFlow);
        return stockFlowIPage;
    }
}
