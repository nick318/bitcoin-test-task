/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.service;

import static org.mockito.Mockito.mock;

import com.nick318.bitcoin.rates.domain.Rate;
import com.nick318.bitcoin.rates.domain.RateType;
import com.nick318.bitcoin.rates.service.validation.CurrencyCodeValidator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

/**
 * Unit tests for {@link CommandLineInputService}.
 *
 * @author Nikita Salomatin
 */
@RunWith(JUnit4.class)
public class CommandLineInputServiceTest {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    private RateConsole rateConsole = mock(RateConsole.class);
    private CurrencyCodeValidator currencyCodeValidator = mock(CurrencyCodeValidator.class);
    private UserInputService userInputService = new CommandLineInputService(currencyCodeValidator, rateConsole);

    @Test
    public void shouldValidateUserInputCurrency() {
        //given
        final String usd = "USD";
        mockHappyPath(usd);
        //when
        String currencyCode = userInputService.getCurrencyCode();
        //then
        Assert.assertEquals(usd, currencyCode);
    }

    @Test
    public void shouldAskForRetypeForUnSupportedCurrency() {
        //given
        final String unSupportedCurrency = "unsupported currency";
        final String usd = "USD";
        Mockito.when(rateConsole.readLine())
                .thenReturn(unSupportedCurrency)
                .thenReturn(usd);
        Mockito.when(currencyCodeValidator.isValid(unSupportedCurrency)).thenReturn(false);
        Mockito.when(currencyCodeValidator.isValid(usd)).thenReturn(true);
        //when
        String currencyCode = userInputService.getCurrencyCode();
        //then
        Assert.assertEquals(usd, currencyCode);
        Mockito.verify(rateConsole).write("This currency code is not supported.");
    }

    @Test
    public void shouldExitOnQWithUnSupportedCurrency() {
        //given
        exit.expectSystemExitWithStatus(0);
        final String unSupportedCurrency = "unsupported currency";
        final String usd = "USD";
        Mockito.when(rateConsole.readLine())
                .thenReturn(unSupportedCurrency)
                .thenReturn("q");
        Mockito.when(currencyCodeValidator.isValid(unSupportedCurrency)).thenReturn(false);
        //when
        String currencyCode = userInputService.getCurrencyCode();
        //then
        Assert.fail("System should exit");
    }

    @Test
    public void shouldWriteInfoMessageBeforeGettingCurrency() {
        //given
        final String usd = "USD";
        mockHappyPath(usd);
        //when
        String currencyCode = userInputService.getCurrencyCode();
        //then
        Mockito.verify(rateConsole).write("Write a currency code you want to use: (USD, EUR, GBP, etc):");
    }

    @Test
    public void shouldShowRate() {
        //when
        userInputService.showRate(new Rate().setType(RateType.CURRENT).setValue("9.11"));
        //then
        Mockito.verify(rateConsole).write("CURRENT 9.11");
    }

    private void mockHappyPath(String currency) {
        Mockito.when(rateConsole.readLine()).thenReturn(currency);
        Mockito.when(currencyCodeValidator.isValid(currency)).thenReturn(true);
    }
}