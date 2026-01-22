package org.example.console.commands;

import org.example.console.OperationCommand;
import org.example.console.OperationType;
import org.example.entities.User;
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
        for (User user : userService.showAllUsers()) {
            System.out.println(user);
        }
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.SHOW_ALL_USERS;
    }
}
