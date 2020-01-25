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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link BitcoinRateService}.
 *
 * @author Nikita Salomatin
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BitcoinRateServiceImpl implements BitcoinRateService {

    @Override
    public Rate getLowestRate(List<ComparableRate> comparableRate) {
        return comparableRate
                .stream()
                .min(ComparableRate::compareTo)
                .map(compareRate -> this.mapToRate(compareRate, RateType.LOWEST))
                .orElseThrow(() -> new IllegalArgumentException("Rates is empty, could not find minimum rate"));
    }

    @Override
    public Rate getHighestRate(List<ComparableRate> comparableRate) {
        return comparableRate
                .stream()
                .max(ComparableRate::compareTo)
                .map(compareRate -> this.mapToRate(compareRate, RateType.HIGHEST))
                .orElseThrow(() -> new IllegalArgumentException("Rates is empty, could not find maximum rate"));
    }

    private Rate mapToRate(ComparableRate compareRate, RateType rateType) {
        return new Rate().setType(rateType).setValue(compareRate.getValue().toString());
    }
}
