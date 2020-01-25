/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.service;

import com.nick318.bitcoin.rates.domain.ComparableRate;
import com.nick318.bitcoin.rates.domain.Rate;
import com.nick318.bitcoin.rates.domain.RateType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for {@link BitcoinRateService}.
 *
 * @author Nikita Salomatin
 */
@RunWith(JUnit4.class)
public class BitcoinRateServiceImplTest {

    private BitcoinRateService bitcoinRateService = new BitcoinRateServiceImpl();

    @Test
    public void shouldCalculateLowestRate() {
        //given
        List<ComparableRate> comparableRate = getRates();
        //when
        Rate lowestRate = bitcoinRateService.getLowestRate(comparableRate);
        //then
        Assert.assertEquals("9.11", lowestRate.getValue());
        Assert.assertEquals(RateType.LOWEST, lowestRate.getType());
    }

    @Test
    public void shouldCalculateHighestRate() {
        //given
        List<ComparableRate> comparableRate = getRates();
        //when
        Rate lowestRate = bitcoinRateService.getHighestRate(comparableRate);
        //then
        Assert.assertEquals("9.14", lowestRate.getValue());
        Assert.assertEquals(RateType.HIGHEST, lowestRate.getType());
    }

    private List<ComparableRate> getRates() {
        return Arrays.asList(
                new ComparableRate().setValue(new BigDecimal(9.14).setScale(2, BigDecimal.ROUND_HALF_UP)),
                new ComparableRate().setValue(new BigDecimal(9.13).setScale(2, BigDecimal.ROUND_HALF_UP)),
                new ComparableRate().setValue(new BigDecimal(9.11).setScale(2, BigDecimal.ROUND_HALF_UP)),
                new ComparableRate().setValue(new BigDecimal(9.12).setScale(2, BigDecimal.ROUND_HALF_UP))
        );
    }
}