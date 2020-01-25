/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.service;

import com.nick318.bitcoin.rates.domain.Rate;

/**
 * This services contains logic of manipulating with user.
 *
 * @author Nikita Salomatin
 */
public interface UserInputService {

    /**
     * Takes currency code from user in any way.
     *
     * @return currency code
     * @throws UserInputException if no interaction with user is available
     */
    String getCurrencyCode() throws UserInputException;

    /**
     * Show given rate to user.
     *
     * @param rate given rate
     */
    void showRate(Rate rate);

    /**
     * Notifies about any error.
     *
     * @param message to notify
     */
    void notifyAboutError(String message);
}
