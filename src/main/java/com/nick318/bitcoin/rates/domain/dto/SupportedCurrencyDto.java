/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.domain.dto;

import lombok.Data;

@Data
public class SupportedCurrencyDto {
    private String currency;
    private String country;
}
