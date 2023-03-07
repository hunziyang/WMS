package com.yang.portal.wms.exception.stock;

import com.yang.portal.core.exception.BaseException;
import com.yang.portal.wms.result.WmsResultCode;

public class InitStockNumberException extends BaseException {

    public InitStockNumberException(){
        super(WmsResultCode.INIT_STOCK_AMOUNT_ERROR);
    }
}
