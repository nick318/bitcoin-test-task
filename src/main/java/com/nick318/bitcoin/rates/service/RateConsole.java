/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.service;

/**
 * Rate console is abstraction of operation system console.
 *
 * @author Nikita Salomatin
 */
public interface RateConsole {

    /**
     * Writes input object into console
     *
     * @param object input object
     */
    void write(Object object);

    /**
     * Reads line from console.
     *
     * @return line from console
     */
    String readLine();
}
