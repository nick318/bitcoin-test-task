/*
 * Copyright (c) 2019.
 *
 * This file is under MIT license and was made as part of task.
 * author: Nikita Salomatin.
 */

package com.nick318.bitcoin.rates.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for controllers
 *
 * @author Nikita Salomatin
 */
@Configuration
public class ControllerConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}
