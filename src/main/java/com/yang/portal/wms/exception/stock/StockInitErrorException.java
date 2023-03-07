package com.yang.portal.wms.exception.stock;

import com.yang.portal.core.exception.BaseException;
import com.yang.portal.wms.result.WmsResultCode;

public class StockInitErrorException extends BaseException {

    public StockInitErrorException(){
        super(WmsResultCode.INIT_STOCK_ERROR);
    }
}
