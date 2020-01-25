/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.controller;

import com.nick318.bitcoin.rates.ApiException;
import com.nick318.bitcoin.rates.domain.Rate;

/**
 * Bitcoin controller for getting rates
 *
 * @author Nikita Salomatin
 */
public interface BitcoinRateController {

    /**
     * Get current rate from api
     *
     * @param currencyCode input currency code
     * @return current rate of bitcoin to input currency
     * @throws ApiException if could not get response from api
     */
    Rate getCurrentRate(String currencyCode) throws ApiException;

    /**
     * Get lowest rate from api, take statistic from (current date - days) to current date.
     *
     * @param currencyCode input currency code
     * @param days         days to subtract date to
     * @return lowest rate by taken statistic
     * @throws ApiException if could not get response from api
     */
    Rate getLowestRateByDays(String currencyCode, int days) throws ApiException;

    /**
     * Get highest rate from api, take statistic from (current date - days) to current date.
     *
     * @param currencyCode input currency code
     * @param days         days to subtract date to
     * @return highest rate by taken statistic
     * @throws ApiException if could not get response from api
     */
    Rate getHighestRateByDays(String currencyCode, int days) throws ApiException;
}
