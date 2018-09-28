package com.itsm.frontend.core;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        context.registerShutdownHook();

        Runnable consoleMenu = (Runnable) context.getBean("ConsoleMenu");
        consoleMenu.run();

    }
}
