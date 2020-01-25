/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.service;

/**
 * Exception to show problems with input of user, e.g. console is not available
 *
 * @author Nikita Salomatin
 */
public class UserInputException extends RuntimeException {
    UserInputException(String message) {
        super(message);
    }
}
