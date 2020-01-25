/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ComparableRate implements Comparable<ComparableRate> {
    private BigDecimal value;

    @Override
    public int compareTo(ComparableRate o) {
        return value.compareTo(o.getValue());
    }
}
