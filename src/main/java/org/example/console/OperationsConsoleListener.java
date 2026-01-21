package org.example.console;

import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class OperationsConsoleListener implements Runnable{

    private final Map<OperationType, OperationCommand> commandMap;
    private final Scanner scanner;

    public OperationsConsoleListener(List<OperationCommand> commands, Scanner scanner) {
        this.commandMap = new EnumMap<>(OperationType.class);
        this.scanner = scanner;

        commands.forEach(command -> commandMap.put(command.getOperationType(), command));
    }

    private void printMenu() {
        System.out.println("Please enter one of operation type: ");
        for (OperationType type : OperationType.values()) {
            System.out.println("-" + type);
        }
    }

    @Override
    public void run() {
        while (true) {
            printMenu();

            String input = scanner.nextLine();

            try {
                OperationType type = OperationType.valueOf(input.toUpperCase());

                OperationCommand command = commandMap.get(type);

                if(command == null) {
                    System.out.println("Command not implemented");
                    continue;
                }

                command.execute();
            } catch (IllegalArgumentException e) {
                System.out.println("Unknown operation");
            }
        }
    }
}
