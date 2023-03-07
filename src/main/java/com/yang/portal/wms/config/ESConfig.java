package com.yang.portal.wms.config;

import com.yang.portal.core.CoreConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Configuration
@SuppressWarnings("all")
public class ESConfig extends ElasticsearchConfigurationSupport {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        return new ElasticsearchCustomConversions(Arrays.asList(StringToZoneDateTimeConvert.INSTANCE, ZonedDateTimeToStringConvert.INSTANCE));
    }

    @ReadingConverter
    enum StringToZoneDateTimeConvert implements Converter<String, ZonedDateTime> {
        INSTANCE;

        @Override
        public ZonedDateTime convert(String source) {
            return LocalDateTime.parse(source, dateTimeFormatter).atZone(ZoneId.of(CoreConstant.SERVER_ZONE));
        }
    }

    @WritingConverter
    enum ZonedDateTimeToStringConvert implements Converter<ZonedDateTime, String> {
        INSTANCE;

        @Override
        public String convert(ZonedDateTime source) {
            return dateTimeFormatter.format(source);
        }
    }


}
