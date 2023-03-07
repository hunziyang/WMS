package com.yang.portal.wms.exception.stock;

import com.yang.portal.core.exception.BaseException;
import com.yang.portal.wms.result.WmsResultCode;

public class InsertFlowException extends BaseException {

    public InsertFlowException(){
        super(WmsResultCode.INSERT_FLOW_ERROR);
    }
}
