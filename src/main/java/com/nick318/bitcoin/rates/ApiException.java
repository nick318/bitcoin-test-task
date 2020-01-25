/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates;

/**
 * Api exception is thrown when api is not available
 *
 * @author Nikita Salomatin
 */
public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Exception e) {
        super(message, e);
    }
}
