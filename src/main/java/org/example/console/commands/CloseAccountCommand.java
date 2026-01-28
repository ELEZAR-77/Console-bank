package org.example.console.commands;

import org.example.console.OperationCommand;
import org.example.console.OperationType;
import org.example.services.AccountService;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Scanner;

@Component
public class CloseAccountCommand implements OperationCommand {
    private final AccountService accountService;
    private final Scanner scanner;

    public CloseAccountCommand(AccountService accountService, Scanner scanner) {
        this.accountService = accountService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter account ID to close: ");
        String input = scanner.nextLine();

        try {
            if (input == null || input.trim().isEmpty()) throw new IllegalArgumentException();

            Long accId = Long.parseLong(input);

            accountService.closeAccount(accId);
            System.out.println("Account with ID " + accId + " to closed.");

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number");
        } catch (IllegalArgumentException | NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.ACCOUNT_CLOSE;
    }
}
