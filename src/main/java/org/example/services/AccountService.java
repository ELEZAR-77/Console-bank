package org.example.services;

import org.example.entities.Account;
import org.example.entities.User;
import org.example.properties.AccountProperties;
import org.example.transactional.TransactionalHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AccountService {
    private final AccountProperties properties;
    private final TransactionalHandler transactionalHandler;
    private final SessionFactory sessionFactory;

    public AccountService(AccountProperties properties, TransactionalHandler transactionalHandler, SessionFactory sessionFactory) {
        this.properties = properties;
        this.transactionalHandler = transactionalHandler;
        this.sessionFactory = sessionFactory;
    }

    public Account createStartAccount(User user) {
        return new Account(properties.getDefaultAmount(), user);
    }

    public Account createAdditionalAccount(User user) {
        return transactionalHandler.executeTransactional(session -> {
            var account = new Account(0, user);
            session.persist(account);
            return account;
        });
    }

    public void closeAccount(Long accId) {
            List<Account> accounts = getAccountList();

            if (accounts.size() == 1) {
                throw new IllegalArgumentException("You can't close a single account: " + findByAccountId(accId));
            }

            Account accountToClose = findByAccountId(accId);

            if (accounts.size() > 1) {
                Account targetAccount = accounts.stream()
                        .filter(a -> !a.getId().equals(accId))
                        .findFirst()
                        .orElseThrow();

                transactionalHandler.executeTransactional(session -> {
                    var managedTarget = session.merge(targetAccount);

                    managedTarget.setMoneyAmount(
                            managedTarget.getMoneyAmount() + accountToClose.getMoneyAmount()
                    );
                    session.remove(accountToClose);
                });
            }
    }

    public void withdrawMoney(Account account, int amount) {
        if (amount > account.getMoneyAmount()) {
            throw new IllegalArgumentException(
                    "There are insufficient funds in the account!\nYour amount: " + account.getMoneyAmount()
            );
        }

        transactionalHandler.executeTransactional(session -> {
            var managedAccount = session.merge(account);
            managedAccount.setMoneyAmount(managedAccount.getMoneyAmount() - amount);
            System.out.println("The transaction was successful!\nThe amount in your account is: " + managedAccount.getMoneyAmount());
        });
    }

    public void deposit(Account account, int amount) {
        transactionalHandler.executeTransactional(session -> {
            var managedAccount = session.merge(account);
            managedAccount.setMoneyAmount(managedAccount.getMoneyAmount() + amount);
        });
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

        int totalAmount = !Objects.equals(account1.getUser().getId(), account2.getUser().getId())
                ? (int) (amount - amount * properties.getTransferCommission())
                : amount;

        transactionalHandler.executeTransactional(session -> {
            var manageFrom = session.merge(account1);
            var manageTo = session.merge(account2);

            manageFrom.setMoneyAmount(manageFrom.getMoneyAmount() - totalAmount);
            manageTo.setMoneyAmount(manageTo.getMoneyAmount() + totalAmount);
        });
    }

    public List<Account> getAccountList() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT a FROM Account a", Account.class)
                    .list();
        }
    }

    public Account findByAccountId(Long id) {
        return transactionalHandler.executeTransactional(session -> {
            return session.find(Account.class, id);
        });
    }
}