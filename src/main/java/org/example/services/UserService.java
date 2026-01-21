package org.example.services;

import org.example.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    User user = new User();
    private final AccountService accountService;

    public UserService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void createUser() {

    }
}
