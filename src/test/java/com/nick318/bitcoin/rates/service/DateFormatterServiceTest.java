/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.Instant;

/**
 * Unit tests for {@link DateFormatterService}.
 *
 * @author Nikita Salomatin
 */
@RunWith(JUnit4.class)
public class DateFormatterServiceTest {

    private DateFormatterService service = new DateFormatterService();

    @Test
    public void startDateShouldGetStringDateWithMinusDays() {
        //given
        int daysToSubtract = 30;
        //when
        String startDate = service.getStartDate(daysToSubtract, Instant.parse("2007-12-31T10:15:30.00Z"));
        //then
        Assert.assertEquals("2007-12-01", startDate);
    }

    @Test
    public void endDateShouldReturnStringDate() {
        //when
        String endDate = service.getEndDate(Instant.parse("2007-12-31T10:15:30.00Z"));
        //then
        Assert.assertEquals("2007-12-31", endDate);
    }
}