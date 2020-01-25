/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates;

import com.nick318.bitcoin.rates.controller.BitcoinRateController;
import com.nick318.bitcoin.rates.domain.Rate;
import com.nick318.bitcoin.rates.service.UserInputException;
import com.nick318.bitcoin.rates.service.UserInputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * This class is responsible for initialization of application and is an entry point.
 */
@Component
@RequiredArgsConstructor
@Profile("!test")
@Slf4j
public class StartUpHandler implements ApplicationListener<ApplicationStartedEvent> {

    private static final int THIRTY_DAYS = 30;
    private final UserInputService userInputService;
    private final BitcoinRateController bitcoinRateController;

    /**
     * When application is started the process is executed.
     * It gets currency from user and then make calls to the api for getting rates
     * Once rates is got the system shows them in console.
     */
    @SuppressWarnings({"InfiniteLoopStatement"})
    private void start() {
        try {
            while (true) {
                String currencyCode = userInputService.getCurrencyCode();
                Rate currentRate = bitcoinRateController.getCurrentRate(currencyCode);
                Rate highestRate = bitcoinRateController.getHighestRateByDays(currencyCode, THIRTY_DAYS);
                Rate lowestRate = bitcoinRateController.getLowestRateByDays(currencyCode, THIRTY_DAYS);
                Stream.of(currentRate, lowestRate, highestRate).forEach(userInputService::showRate);
            }
        } catch (ApiException e) {
            log.debug("Could not call api", e);
            userInputService.notifyAboutError(
                    "API for getting rates of bitcoin is not available, try it later, or press q for exit"
            );
            start();
        } catch (UserInputException e) {
            log.debug("Could not get console", e);
            userInputService.notifyAboutError(
                    "Cannot get console for inputs, try to run jar file. Program is finished."
            );
            throw e;
        } catch (Exception e) {
            log.debug("Unexpected error", e);
            userInputService.notifyAboutError("Unexpected error, see .log/spring.log for details");
            throw e;
        }
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        start();
    }
}
