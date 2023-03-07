package com.yang.portal.wms.entity;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class DateRange {

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;
}
