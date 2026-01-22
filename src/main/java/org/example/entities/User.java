package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Long id;
    private String login;
    private List<Account> accountList;

    public User(Long id, String login, List<Account> accountList) {
        this.id = id;
        this.login = login;
        this.accountList = new ArrayList<>(accountList);
    }

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
}
