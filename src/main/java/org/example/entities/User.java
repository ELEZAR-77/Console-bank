package org.example.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private final Long id;
    private final String login;
    private final List<Account> accountList;

    public User(Long id, String login, List<Account> accountList) {
        this.id = id;
        this.login = login;
        this.accountList = new ArrayList<>(accountList);
    }


    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }


    public List<Account> getAccountList() {
        return accountList;
    }

    public void addAccount(Account account) {
        accountList.add(account);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", accountList=" + accountList +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(getLogin(), user.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getLogin());
    }
}
