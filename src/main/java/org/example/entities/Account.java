package org.example.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "money_amount")
    private int moneyAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Account(int moneyAmount, User user) {
        this.moneyAmount = moneyAmount;
        this.user = user;
    }

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
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
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
