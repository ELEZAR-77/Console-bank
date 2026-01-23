package org.example.console.commands;

import org.example.console.OperationCommand;
import org.example.console.OperationType;

public class AccountWithdrawCommand implements OperationCommand {
    @Override
    public void execute() {

    }

    @Override
    public OperationType getOperationType() {
        return OperationType.ACCOUNT_WITHDRAW;
    }
}
