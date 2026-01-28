package org.example.console.commands;

import org.example.console.OperationCommand;
import org.example.console.OperationType;
import org.example.services.UserService;
import org.springframework.stereotype.Component;

import javax.management.InstanceAlreadyExistsException;
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

        try {
            if (login == null || login.isEmpty()) throw new IllegalArgumentException("Login cannot be empty!");

            System.out.println("User created: " + userService.createUser(login));
        } catch (InstanceAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.USER_CREATE;
    }
}
