package com.yang.portal.wms;

import com.yang.portal.core.annotation.YangApplication;
import com.yang.portal.core.utils.SpringUtil;

@YangApplication
public class WMSAppliction {

    public static void main(String[] args) {
        SpringUtil.run(WMSAppliction.class, args);
    }
}
