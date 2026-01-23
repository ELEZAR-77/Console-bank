package org.example.services;

import org.example.entities.Account;
import org.example.entities.User;
import org.example.properties.AccountProperties;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountProperties properties;
    private final UserRepository userRepository;
    private Long idCounter = 0L;

    public AccountService(AccountProperties properties, UserRepository userRepository) {
        this.properties = properties;
        this.userRepository = userRepository;
    }

    public Account createStartAccount(Long userId) {
        idCounter = findFirstFreeAccount();
        return new Account(idCounter, userId, properties.getDefaultAmount());
    }

    public Account createAdditionalAccount(User user) {
        idCounter = findFirstFreeAccount();

        Account account = new Account(idCounter, user.getId());
        user.addAccount(account);
        return account;
    }

    public void closeAccount(Long accId) {
        for (User user : userRepository.findAll()) {
            boolean removed = user.getAccountList()
                    .removeIf(a -> a.getId().equals(accId));
            if (removed) {
                return;
            }
        }

        throw new NoSuchElementException();
    }

    public Long findFirstFreeAccount() {
        Set<Long> usedIds = userRepository.findAll().stream()
                .flatMap(u -> u.getAccountList().stream())
                .map(Account::getId)
                .collect(Collectors.toSet());

        Long id = 1L;

        while (usedIds.contains(id)) {
            id++;
        }
        return id;
    }

    public void withdrawMoney(Account account, int money) {
        if (money > account.getMoneyAmount()) {
            System.out.println("There are insufficient funds in the account!\nYour amount: " + account.getMoneyAmount());
            return;
        }

        account.setMoneyAmount(account.getMoneyAmount() - money);
        System.out.println("The transaction was successful!\nThe amount in your account is: " + account.getMoneyAmount());
    }

    public Account findByAccountId(Long id) {
        return userRepository.findAll().stream()
                .flatMap(u -> u.getAccountList().stream())
                .filter(a -> a.getId().equals(id))
                .findFirst().orElseThrow(() -> new NoSuchElementException("Account not found"));
    }

}
