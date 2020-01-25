/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.service;

import com.nick318.bitcoin.rates.domain.Rate;
import com.nick318.bitcoin.rates.service.validation.CurrencyCodeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link UserInputService}.
 *
 * @author Nikita Salomatin
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CommandLineInputService implements UserInputService, ApplicationListener<ApplicationStartedEvent> {

    private final CurrencyCodeValidator currencyCodeValidator;
    private final RateConsole console;

    @Override
    public String getCurrencyCode() throws UserInputException {
        String currencyCode = getCurrencyFromConsole();
        stopIfExit(currencyCode);
        while (!currencyCodeValidator.isValid(currencyCode)) {
            stopIfExit(currencyCode);
            console.write("This currency code is not supported.");
            currencyCode = getCurrencyFromConsole();
        }
        return currencyCode;
    }

    @Override
    public void showRate(Rate rate) {
        console.write(rate.getType() + " " + rate.getValue());
    }

    @Override
    public void notifyAboutError(String message) {
        console.write(message);
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        console.write("Hi, this is a bitcoin rate service.");
        console.write("Write q for exit.");
    }

    private void stopIfExit(String currencyCode) {
        if ("q".equals(currencyCode)) {
            console.write("FINISHED....");
            System.exit(0);
        }
    }

    private String getCurrencyFromConsole() throws UserInputException {
        console.write("Write a currency code you want to use: (USD, EUR, GBP, etc):");
        return console.readLine();
    }
}
