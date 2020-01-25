/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import com.nick318.bitcoin.rates.ApiException;
import com.nick318.bitcoin.rates.domain.ComparableRate;
import com.nick318.bitcoin.rates.domain.Rate;
import com.nick318.bitcoin.rates.domain.RateType;
import com.nick318.bitcoin.rates.service.BitcoinRateService;
import com.nick318.bitcoin.rates.service.DateFormatterService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;

/**
 * Unit tests for {@link BitcoinRateControllerImpl}
 *
 * @author Nikita Salomatin
 */
@RunWith(JUnit4.class)
public class BitcoinRateControllerImplTest {
    private RestTemplate restTemplate = mock(RestTemplate.class);
    private BitcoinRateService bitcoinRateService = mock(BitcoinRateService.class);
    private DateFormatterService dateFormatterService = mock(DateFormatterService.class);
    private BitcoinRateController bitcoinRateController =
            new BitcoinRateControllerImpl(restTemplate, bitcoinRateService, dateFormatterService);

    @Test
    public void shouldMapResponseToCurrentRate() {
        //given
        Mockito.when(restTemplate.getForObject(any(URI.class), any())).thenReturn(mockCurrentResponse());
        //when
        Rate rate = bitcoinRateController.getCurrentRate("USD");
        //then
        Assert.assertEquals(RateType.CURRENT, rate.getType());
        Assert.assertEquals("10449.345", rate.getValue());
    }

    @Test(expected = ApiException.class)
    public void shouldWrapRestExceptionToApi() {
        //given
        Mockito.when(restTemplate.getForObject(any(URI.class), any()))
                .thenThrow(RestClientException.class);
        //when
        bitcoinRateController.getCurrentRate("USD");
        //expect api exception to be thrown
    }

    @Test(expected = ApiException.class)
    public void shouldWrapRestExceptionToApiForHighestRate() {
        //given
        Mockito.when(restTemplate.getForObject(any(URI.class), any()))
                .thenThrow(RestClientException.class);
        //when
        bitcoinRateController.getHighestRateByDays("USD", 30);
        //expect api exception to be thrown
    }

    @Test(expected = ApiException.class)
    public void shouldWrapRestExceptionToApiForLowestRate() {
        //given
        Mockito.when(restTemplate.getForObject(any(URI.class), any()))
                .thenThrow(RestClientException.class);
        //when
        bitcoinRateController.getLowestRateByDays("USD", 30);
        //expect api exception to be thrown
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldMapRateListAndCallService() {
        //given
        Mockito.when(restTemplate.getForObject(any(URI.class), any())).thenReturn(mockRateList());
        //when
        bitcoinRateController.getHighestRateByDays("USD", 30);
        //then
        ArgumentCaptor<ArrayList<ComparableRate>> serviceCaptor = ArgumentCaptor.forClass(ArrayList.class);
        Mockito.verify(bitcoinRateService).getHighestRate(serviceCaptor.capture());
        Assert.assertEquals(3, serviceCaptor.getValue().size());
        Assert.assertEquals(new BigDecimal("9692.7067"), serviceCaptor.getValue().get(0).getValue());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldMapRateListAndCallServiceForLowestRate() {
        //given
        Mockito.when(restTemplate.getForObject(any(URI.class), any())).thenReturn(mockRateList());
        //when
        bitcoinRateController.getLowestRateByDays("USD", 30);
        //then
        ArgumentCaptor<ArrayList<ComparableRate>> serviceCaptor = ArgumentCaptor.forClass(ArrayList.class);
        Mockito.verify(bitcoinRateService).getLowestRate(serviceCaptor.capture());
        Assert.assertEquals(3, serviceCaptor.getValue().size());
        Assert.assertEquals(new BigDecimal("9692.7067"), serviceCaptor.getValue().get(0).getValue());
    }

    private String mockRateList() {
        return "{\n" +
                "    \"bpi\": {\n" +
                "        \"2019-07-17\": 9692.7067,\n" +
                "        \"2019-07-18\": 10636.91,\n" +
                "        \"2019-07-19\": 10526.3917,\n" +
                "    },\n" +
                "    \"disclaimer\": \"This data was produced from the CoinDesk Bitcoin Price Index. BPI value data returned as USD.\",\n" +
                "    \"time\": {\n" +
                "        \"updated\": \"Aug 17, 2019 00:03:00 UTC\",\n" +
                "        \"updatedISO\": \"2019-08-17T00:03:00+00:00\"\n" +
                "    }\n" +
                "}";
    }

    private String mockCurrentResponse() {
        return "{\n" +
                "    \"time\": {\n" +
                "        \"updated\": \"Aug 17, 2019 09:53:00 UTC\",\n" +
                "        \"updatedISO\": \"2019-08-17T09:53:00+00:00\",\n" +
                "        \"updateduk\": \"Aug 17, 2019 at 10:53 BST\"\n" +
                "    },\n" +
                "    \"disclaimer\": \"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\n" +
                "    \"bpi\": {\n" +
                "        \"USD\": {\n" +
                "            \"code\": \"USD\",\n" +
                "            \"rate\": \"10,449.3450\",\n" +
                "            \"description\": \"United States Dollar\",\n" +
                "            \"rate_float\": 10449.345\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }
}