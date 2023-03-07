package com.yang.portal.wms;

public interface Constant {

    interface Stock{
        String REDIS_PRODUCT_STOCK_LOCK = "PRODUCT_STOCK_LOCK_%d";
        String REDIS_PRODUCT_STOCK = "PRODUCT_STOCK_%d";
    }

    interface Number{
        String REDIS_NUMBER_LOCK = "NUMBER_LOCK_%s";
        String REDIS_NUMBER = "NUMBER_%s";
    }
}
