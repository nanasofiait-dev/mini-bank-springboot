package com.example.minibank.dto;

import com.example.minibank.entity.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountResponseDTO {
    private Long id;
    private String iban;
    private BigDecimal balance;
    private Account.AccountType accountType;
    private Boolean active;
    private LocalDateTime createdAt;
    private Long clientId;

    public AccountResponseDTO() {
    }

    public AccountResponseDTO(Long id, String iban, BigDecimal balance, Account.AccountType accountType, Boolean active, LocalDateTime createdAt, Long clientId) {
        this.id = id;
        this.iban = iban;
        this.balance = balance;
        this.accountType = accountType;
        this.active = active;
        this.createdAt = createdAt;
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Account.AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(Account.AccountType accountType) {
        this.accountType = accountType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
