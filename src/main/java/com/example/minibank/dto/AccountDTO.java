package com.example.minibank.dto;

import com.example.minibank.entity.Account;
import com.example.minibank.entity.Client;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountDTO {

    @NotNull(message = "Tipo de conta é obrigatório")
    private Account.AccountType accountType;

    @NotNull(message = "Cliente é obrigatório")
    private Long clientId;

    public AccountDTO() {
    }

    public AccountDTO(Account.AccountType accountType, Long clientId) {
        this.accountType = accountType;
        this.clientId = clientId;
    }

    public Account.AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(Account.AccountType accountType) {
        this.accountType = accountType;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
