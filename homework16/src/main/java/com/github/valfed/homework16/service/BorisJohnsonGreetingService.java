package com.github.valfed.homework16.service;

import com.github.valfed.homework16.annotation.Bean;

@Bean("borisJohnsonGreeting")
public class BorisJohnsonGreetingService extends UkGreetingService {
    @Override
    public String getGreetingMessage() {
        return "Добрий день, everybody!";
    }
}
