package org.example.console.commands;

import org.example.console.OperationCommand;
import org.example.console.OperationType;
import org.example.services.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateUserCommand implements OperationCommand {
    private final UserService userService;
    private final Scanner scanner;

    public CreateUserCommand(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }


    @Override
    public void execute() {
        System.out.println("Enter login: ");
        String login = scanner.nextLine();

        System.out.println("User created: " + userService.createUser(login));
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.USER_CREATE;
    }
}
