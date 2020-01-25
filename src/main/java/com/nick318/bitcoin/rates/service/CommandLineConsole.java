/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * Implementation of {@link RateConsole}.
 *
 * @author Nikita Salomatin
 */
@Service
public class CommandLineConsole implements RateConsole {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void write(Object object) {
        System.out.println(object);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
