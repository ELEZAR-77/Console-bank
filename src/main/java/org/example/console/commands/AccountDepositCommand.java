package org.example.console.commands;

import org.example.console.OperationCommand;
import org.example.console.OperationType;
import org.example.entities.Account;
import org.example.services.AccountService;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Scanner;

@Component
public class AccountDepositCommand implements OperationCommand {
    private final AccountService accountService;
    private final Scanner scanner;

    public AccountDepositCommand(AccountService accountService, Scanner scanner) {
        this.accountService = accountService;
        this.scanner = scanner;
    }


    @Override
    public void execute() {
        System.out.println("Enter account ID:");
        String inputId = scanner.nextLine();

        System.out.println("Enter amount to deposit: ");
        String inputAmount = scanner.nextLine();

        try {
            if (inputId == null || inputId.trim().isEmpty()) throw new IllegalArgumentException("Account ID cannot be empty");
            if (inputAmount == null || inputAmount.trim().isEmpty()) throw new IllegalArgumentException("Amount field cannot be empty");

            Long accId = Long.parseLong(inputId);
            int amount = Integer.parseInt(inputAmount);

            Account account = accountService.findByAccountId(accId);
            accountService.deposit(account, amount);

            System.out.println("Amount " + amount + " deposited to account ID:" + accId);

        } catch (NumberFormatException e) {
            System.out.println("Please enter a numerical ID or amount");
        } catch (IllegalArgumentException | NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.ACCOUNT_DEPOSIT;
    }
}
