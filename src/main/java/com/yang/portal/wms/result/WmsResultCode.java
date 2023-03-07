package com.yang.portal.wms.result;

import com.yang.portal.core.result.ResultCodeBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WmsResultCode implements ResultCodeBase {
    INSERT_FLOW_ERROR(701,"初始化库存中，新插入流水失败"),
    INIT_STOCK_AMOUNT_ERROR(702,"初始化库存不能为负数"),
    INIT_STOCK_ERROR(703,"初始化库存失败"),
    INIT_NUMBER_ERROR(704,"初始化订单编号失败"),
    UPDATE_STOCK_ERROR(705,"更新库存失败");
    private Integer code;
    private String msg;
}
