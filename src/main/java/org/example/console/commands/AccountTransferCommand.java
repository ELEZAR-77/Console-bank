package org.example.console.commands;

import org.example.console.OperationCommand;
import org.example.console.OperationType;
import org.example.entities.Account;
import org.example.services.AccountService;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Scanner;

@Component
public class AccountTransferCommand implements OperationCommand {
    public final AccountService accountService;
    public final Scanner scanner;

    public AccountTransferCommand(AccountService accountService, Scanner scanner) {
        this.accountService = accountService;
        this.scanner = scanner;
    }


    @Override
    public void execute() {
        System.out.println("Enter source account ID:");
        String inputSourceID = scanner.nextLine();

        System.out.println("Enter target account ID:");
        String inputTargetID = scanner.nextLine();

        System.out.println("Enter amount to transfer:");
        String inputAmount = scanner.nextLine();

        try {
            if (inputSourceID == null || inputSourceID.trim().isEmpty()) throw new IllegalArgumentException("Source account ID cannot be empty");
            if (inputTargetID == null || inputTargetID.trim().isEmpty()) throw new IllegalArgumentException("Target account ID cannot be empty");
            if (inputAmount == null || inputAmount.trim().isEmpty()) throw new IllegalArgumentException("Amount cannot be empty");

            Long sourceID = Long.parseLong(inputSourceID);
            Long targetID = Long.parseLong(inputTargetID);
            int amount = Integer.parseInt(inputAmount);

            Account account1 = accountService.findByAccountId(sourceID);
            Account account2 = accountService.findByAccountId(targetID);

            accountService.transfer(account1, account2, amount);

            System.out.println("Amount " + amount + " transferred from account ID " + sourceID + " to account ID " + targetID + ".");
        }catch (NumberFormatException e) {
            System.out.println("Please enter a numerical ID or amount");
        } catch (IllegalArgumentException | NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.ACCOUNT_TRANSFER;
    }
}
