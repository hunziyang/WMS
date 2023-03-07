package com.yang.portal.wms.util;

import com.yang.portal.core.CoreConstant;
import org.apache.commons.lang3.ObjectUtils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static ZonedDateTime getNow(){
        return ZonedDateTime.now(ZoneId.of(CoreConstant.SERVER_ZONE));
    }

    public static String getNowDate(){
        return getNow().format(DATE_FORMATTER);
    }

    public static String getDate(ZonedDateTime zonedDateTime){
        if (ObjectUtils.isEmpty(zonedDateTime)){
            return getNowDate();
        }
        return zonedDateTime.format(DATE_FORMATTER);
    }

}
