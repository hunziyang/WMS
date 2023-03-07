package com.yang.portal.wms.exception.stock;

import com.yang.portal.core.exception.BaseException;
import com.yang.portal.wms.result.WmsResultCode;

public class InitNumberErrException extends BaseException {

    public InitNumberErrException(){
        super(WmsResultCode.INIT_NUMBER_ERROR);
    }
}
