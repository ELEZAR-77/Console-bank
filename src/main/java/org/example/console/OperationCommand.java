package org.example.console;

public interface OperationCommand {
    void execute();
    OperationType getOperationType();
}
