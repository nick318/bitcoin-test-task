/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.service.validation;

import com.nick318.bitcoin.rates.ApiException;

/**
 * Validator to check whether input currency is supported by API or not.
 *
 * @author Nikita Salomatin
 */
public interface CurrencyCodeValidator {
    /**
     * Calls api to check whether input currency code is supported or not.
     *
     * @param currencyCode input currency code to check
     * @return true if correct, otherwise false
     * @throws ApiException if could not get response from api
     */
    boolean isValid(String currencyCode) throws ApiException;
}
