package org.example.console.commands;

import org.example.console.OperationCommand;
import org.example.console.OperationType;
import org.example.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class ShowUsersCommand implements OperationCommand {
    private final UserService userService;

    public ShowUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute() {
        System.out.println("chlen");
        System.out.println(userService.showAllUsers());
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.SHOW_ALL_USERS;
    }
}
