package org.example.entities;

import java.math.BigDecimal;

public class Account {
    private final Long id;
    private final Long userId;
    private int moneyAmount;

    public Account(Long id, Long userId, int moneyAmount) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = moneyAmount;
    }

    public Account(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
