/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.service;

import com.nick318.bitcoin.rates.domain.ComparableRate;
import com.nick318.bitcoin.rates.domain.Rate;

import java.util.List;

/**
 * Bitcoin service for calculation highest and lowest rate based on statistic.
 *
 * @author Nikita Salomatin
 */
public interface BitcoinRateService {

    /**
     * Get lowest rate
     *
     * @param comparableRate statistic
     * @return lowest rate
     * @throws IllegalArgumentException if statistic is empty
     */
    Rate getLowestRate(List<ComparableRate> comparableRate) throws IllegalArgumentException;

    /**
     * Get highest rate
     *
     * @param comparableRate statistic
     * @return highest rate
     * @throws IllegalArgumentException if statistic is empty
     */
    Rate getHighestRate(List<ComparableRate> comparableRate) throws IllegalArgumentException;
}
