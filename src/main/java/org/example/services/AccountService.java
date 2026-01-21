package org.example.services;

import org.example.entities.Account;
import org.example.properties.AccountProperties;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {
    private final AccountProperties properties;

    public AccountService(AccountProperties properties) {
        this.properties = properties;
    }

    public Account startAccount(Long userId) {
        Long idCounter = 0L;
        idCounter++;
        return new Account(idCounter, userId, properties.getDefaultAmount());
    }
}
