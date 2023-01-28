package com.github.valfed.homework16.service;

import com.github.valfed.homework16.annotation.Bean;

@Bean
public class UkGreetingService implements GreetingService {
    @Override
    public String getGreetingMessage() {
        return "Hello!";
    }
}
