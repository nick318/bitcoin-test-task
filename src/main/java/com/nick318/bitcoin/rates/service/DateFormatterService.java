/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.service;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

/**
 * This class formats date by yyyy-MM-dd format
 */
@Service
public class DateFormatterService {

    /**
     * Get string date from instant formatted by yyyy-MM-dd
     *
     * @param instant time to format date from.
     * @return string date
     */
    public String getEndDate(Instant instant) {
        return new DateFormatter("yyyy-MM-dd").print(Date.from(instant), Locale.ENGLISH);
    }

    /**
     * Get string date from instant minus days formatted by yyyy-MM-dd
     * @param days days to minus from instant
     * @param now instant to format date from
     * @return string date
     */
    public String getStartDate(int days, Instant now) {
        Date date = Date.from(now.minus(Duration.ofDays(days)));
        return new DateFormatter("yyyy-MM-dd").print(date, Locale.ENGLISH);
    }
}
