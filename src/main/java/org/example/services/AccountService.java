package org.example.services;

import org.example.entities.Account;
import org.example.entities.User;
import org.example.properties.AccountProperties;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {
    private final AccountProperties properties;
    private Long idCounter = 0L;

    public AccountService(AccountProperties properties) {
        this.properties = properties;
    }

    public Account createStartAccount(Long userId) {
        idCounter++;
        return new Account(idCounter, userId, properties.getDefaultAmount());
    }

    public Account createAdditionalAccount(User user) {
        idCounter++;
        Account account = new Account(idCounter, user.getId());
        user.addAccount(account);
        return account;
    }
}
