/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.service.validation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import com.nick318.bitcoin.rates.ApiException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Tests
 *
 * @author Nikita Salomatin
 */
@RunWith(JUnit4.class)
public class CurrencyCodeValidatorImplTest {
    private RestTemplate restTemplate = mock(RestTemplate.class);
    private CurrencyCodeValidator currencyCodeValidator = new CurrencyCodeValidatorImpl(restTemplate);

    @Test
    public void shouldReturnTrueForValidCurrency() {
        //given
        Mockito.when(restTemplate.getForObject(any(URI.class), any())).thenReturn(mockCurrentResponse());
        //when
        boolean isValid = currencyCodeValidator.isValid("AED");
        //then
        Assert.assertTrue(isValid);
    }

    @Test
    public void shouldReturnFalseForInvalidCurrency() {
        //given
        Mockito.when(restTemplate.getForObject(any(URI.class), any())).thenReturn(mockCurrentResponse());
        //when
        boolean isValid = currencyCodeValidator.isValid("G12");
        //then
        Assert.assertFalse(isValid);
    }

    @Test(expected = ApiException.class)
    public void shouldWrapRestExceptionToApi() {
        //given
        Mockito.when(restTemplate.getForObject(any(URI.class), any())).thenThrow(RestClientException.class);
        //when
        currencyCodeValidator.isValid("G12");
        //then
        //expect api exception to be thrown
    }

    private String mockCurrentResponse() {
        return "[\n" +
                "    {\n" +
                "        \"currency\": \"AED\",\n" +
                "        \"country\": \"United Arab Emirates Dirham\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"currency\": \"AFN\",\n" +
                "        \"country\": \"Afghan Afghani\"\n" +
                "    }" +
                "]";
    }
}