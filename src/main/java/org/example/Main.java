package org.example;

import org.example.console.OperationsConsoleListener;
import org.example.properties.AccountProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.example");

        OperationsConsoleListener listener = context.getBean(OperationsConsoleListener.class);

        listener.run();
    }
}