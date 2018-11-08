package com.itsm.frontend.core;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        context.registerShutdownHook();

        Runnable consoleGreeter = (Runnable) context.getBean("ConsoleGreeter");
        consoleGreeter.run();

    }
}
