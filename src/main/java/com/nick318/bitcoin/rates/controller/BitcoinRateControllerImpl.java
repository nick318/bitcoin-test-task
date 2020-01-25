/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.controller;

import com.jayway.jsonpath.JsonPath;
import com.nick318.bitcoin.rates.ApiException;
import com.nick318.bitcoin.rates.domain.ComparableRate;
import com.nick318.bitcoin.rates.domain.Rate;
import com.nick318.bitcoin.rates.domain.RateType;
import com.nick318.bitcoin.rates.service.BitcoinRateService;
import com.nick318.bitcoin.rates.service.DateFormatterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link BitcoinRateController}.
 *
 * @author Nikita Salomatin
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BitcoinRateControllerImpl implements BitcoinRateController {

    private final RestTemplate restTemplate;
    private final BitcoinRateService bitcoinRateService;
    private final DateFormatterService dateFormatterService;

    @Override
    public Rate getCurrentRate(String currencyCode) {
        try {
            String response = restTemplate.getForObject(
                    URI.create(String.format("https://api.coindesk.com/v1/bpi/currentPrice/%s.json", currencyCode)),
                    String.class
            );
            String rateValue = JsonPath.parse(response).read(String.format("$.bpi.%s.rate_float", currencyCode)).toString();
            return new Rate().setType(RateType.CURRENT).setValue(rateValue);
        } catch (RestClientException e) {
            throw new ApiException("Could not get current rate", e);
        }
    }

    @Override
    public Rate getLowestRateByDays(String currencyCode, int days) {
        return bitcoinRateService.getLowestRate(getRateList(currencyCode, days));
    }

    @Override
    public Rate getHighestRateByDays(String currencyCode, int days) {
        return bitcoinRateService.getHighestRate(getRateList(currencyCode, days));
    }

    private List<ComparableRate> mapToRateList(List<Double> rateValues) {
        return rateValues.stream()
                .map(value -> new ComparableRate().setValue(new BigDecimal(String.valueOf(value))))
                .collect(Collectors.toList());
    }

    private List<ComparableRate> getRateList(String currencyCode, int days) {
        try {
            String uri = String.format(
                    "https://api.coindesk.com/v1/bpi/historical/close.json?start=%s&end=%s&currency=%s",
                    dateFormatterService.getStartDate(days, Instant.now()),
                    dateFormatterService.getEndDate(Instant.now()),
                    currencyCode
            );
            String response = restTemplate.getForObject(
                    URI.create(uri),
                    String.class
            );
            List<Double> rateValue = JsonPath.parse(response).read("$.bpi.*");
            return mapToRateList(rateValue);
        } catch (RestClientException e) {
            throw new ApiException("Could not get rate list", e);
        }
    }
}
