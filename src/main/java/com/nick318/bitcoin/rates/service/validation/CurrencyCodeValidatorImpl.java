/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.service.validation;

import com.jayway.jsonpath.JsonPath;
import com.nick318.bitcoin.rates.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * Implementation of {@link CurrencyCodeValidator}
 *
 * @author Nikita Salomatin
 */
@Service
@RequiredArgsConstructor
public class CurrencyCodeValidatorImpl implements CurrencyCodeValidator {

    private final RestTemplate restTemplate;

    @Override
    public boolean isValid(String currencyCode) {
        try {
            String uri = "https://api.coindesk.com/v1/bpi/supported-currencies.json";
            String response = restTemplate.getForObject(URI.create(uri), String.class);
            List<String> supportedCurrencies = JsonPath.parse(response).read("$.*.currency");
            return supportedCurrencies.stream()
                    .anyMatch(supportedCurrency -> supportedCurrency.equals(currencyCode));
        } catch (RestClientException e) {
            throw new ApiException("Could not get supported currencies", e);
        }
    }
}
