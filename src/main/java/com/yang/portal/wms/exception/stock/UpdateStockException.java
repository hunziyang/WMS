package com.yang.portal.wms.exception.stock;

import com.yang.portal.core.exception.BaseException;
import com.yang.portal.wms.result.WmsResultCode;

public class UpdateStockException extends BaseException {
    public UpdateStockException(){
        super(WmsResultCode.UPDATE_STOCK_ERROR);
    }
}
