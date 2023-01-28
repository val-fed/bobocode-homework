package com.github.valfed.homework16;

import com.github.valfed.homework16.context.ApplicationContext;
import com.github.valfed.homework16.context.ApplicationContextImpl;
import com.github.valfed.homework16.service.UaGreetingService;
import com.github.valfed.homework16.service.UkGreetingService;

public class Demo {
    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContextImpl("com.github.valfed.homework16");

        System.out.println("uaGreetingService: " + context.getBean(UaGreetingService.class).getGreetingMessage());
        System.out.println("borisJohnsonGreeting: " + context.getBean("borisJohnsonGreeting", UkGreetingService.class).getGreetingMessage());

        System.out.println("----");
        context.getAllBeans(UkGreetingService.class).forEach((key, value) -> System.out.println(key + ": " + value.getGreetingMessage()));
    }
}
