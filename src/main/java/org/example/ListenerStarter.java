package org.example;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.example.console.OperationsConsoleListener;
import org.springframework.stereotype.Component;

@Component
public class ListenerStarter {
    private final OperationsConsoleListener operationsConsoleListener;
    private Thread consoleListenerThread;


    public ListenerStarter(OperationsConsoleListener operationsConsoleListener) {
        this.operationsConsoleListener = operationsConsoleListener;
    }


    @PostConstruct
    public void postConstruct() {
        this.consoleListenerThread = new Thread(operationsConsoleListener);
        consoleListenerThread.start();
    }

    @PreDestroy
    public void preDestroy() {
        consoleListenerThread.interrupt();
        System.out.println("End Console listener...");
    }
}
