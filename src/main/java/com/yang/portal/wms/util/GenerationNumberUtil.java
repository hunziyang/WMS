package com.yang.portal.wms.util;

import com.yang.portal.core.utils.SpringUtil;
import com.yang.portal.wms.Constant;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.time.ZonedDateTime;

public class GenerationNumberUtil {

    private static final RedisTemplate redisTemplate = SpringUtil.getBean(RedisTemplate.class);
    private static final int NUMBER_SUFFIX_SIZE = 7;

    public static String getNumber(String numberPrefix) {
        ZonedDateTime now = DateUtil.getNow();
        String date = DateUtil.getDate(now);
        String redisNumberKey = String.format(Constant.Number.REDIS_NUMBER, date);
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong(redisNumberKey,redisTemplate.getConnectionFactory());
        long amount = redisAtomicLong.incrementAndGet();
        if (amount == 1){
            ZonedDateTime expireTime = now.plusDays(1L).withHour(0).withMinute(10).withSecond(0);
            redisAtomicLong.expireAt(expireTime.toInstant());
        }
        String number = String.valueOf(amount);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < NUMBER_SUFFIX_SIZE - number.length(); i++) {
            stringBuffer.append("0");
        }
        return String.format("%s%s%s%s", numberPrefix, date, stringBuffer, number);
    }
}
