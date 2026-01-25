package org.example.services;

import org.example.entities.Account;
import org.example.entities.User;
import org.example.properties.AccountProperties;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
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
            List<Account> accounts = user.getAccountList();

            if (accounts.size() == 1) {
                throw new IllegalArgumentException("You can't close a single account: " + findByAccountId(accId));
            }

            Account accountToClose = accounts.stream()
                    .filter(a -> a.getId().equals(accId))
                    .findFirst()
                    .orElse(null);

            if (accountToClose == null){
                continue;
            }

            if (accounts.size() > 1) {
                Account targetAccount = accounts.stream()
                        .filter(a -> !a.getId().equals(accId))
                        .findFirst()
                        .orElseThrow();

                targetAccount.setMoneyAmount(accountToClose.getMoneyAmount());
            }

            accounts.remove(accountToClose);
            return;
        }

        throw new NoSuchElementException("No such account exist");
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

    public void withdrawMoney(Account account, int amount) {
        if (amount > account.getMoneyAmount()) {
            throw new IllegalArgumentException(
                    "There are insufficient funds in the account!\nYour amount: " + account.getMoneyAmount()
            );
        }

        account.setMoneyAmount(account.getMoneyAmount() - amount);
        System.out.println("The transaction was successful!\nThe amount in your account is: " + account.getMoneyAmount());
    }

    public void deposit(Account account, int amount) {
        account.setMoneyAmount(account.getMoneyAmount() + amount);
    }

    public void transfer(Account account1, Account account2, int amount) {
        if (amount > account1.getMoneyAmount()) {
            throw new IllegalArgumentException(
                    "There are insufficient funds in the account!\nYour amount: " + account1.getMoneyAmount()
            );
        }
        if (account1.getMoneyAmount() <= 0) {
            throw new IllegalArgumentException(
                    "Cannot transfer not positive amount: " + account1.getMoneyAmount()
            );
        }

        int totalAmount = account1.getUserId() != account2.getUserId()
                ? (int) (amount - amount * properties.getTransferCommission())
                : amount;
        account1.setMoneyAmount(account1.getMoneyAmount() - totalAmount);
        account2.setMoneyAmount(account2.getMoneyAmount() + totalAmount);
    }

    public Account findByAccountId(Long id) {
        return userRepository.findAll().stream()
                .flatMap(u -> u.getAccountList().stream())
                .filter(a -> a.getId().equals(id))
                .findFirst().orElseThrow(() -> new NoSuchElementException("Account not found"));
    }

}
