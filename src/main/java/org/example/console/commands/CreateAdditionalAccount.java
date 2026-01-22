package org.example.console.commands;

import org.example.console.OperationCommand;
import org.example.console.OperationType;
import org.example.entities.Account;
import org.example.entities.User;
import org.example.services.AccountService;
import org.example.services.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateAdditionalAccount implements OperationCommand {
    private final AccountService accountService;
    private final UserService userService;
    private final Scanner scanner;

    public CreateAdditionalAccount(AccountService accountService, UserService userService, Scanner scanner) {
        this.accountService = accountService;
        this.userService = userService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter the user id for which to create an account: ");
        Long userId = Long.parseLong(scanner.nextLine());

        User user = userService.getUserById(userId);
        Account account = accountService.createAdditionalAccount(user);

        System.out.println("New account created with ID:" + account.getId() + " for user: " + user.getLogin());
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.ACCOUNT_CREATE;
    }
}
