package com.example.minibank.dto;

import com.example.minibank.entity.Account;
import com.example.minibank.entity.Transaction;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class TransactionDTO {

    @NotNull(message = "Tipo é obrigatório")
    private Transaction.Type type;

    @NotNull
    @DecimalMin(value = "0.01",message = "Valor deve ser maior que zero")
    private BigDecimal amount;

    @Size(max = 255, message = "Limite máximo de caracteres")
    private  String description;

    @NotNull(message = "Conta origem é obrigatória para transferir")
    private Long sourceAccountId;

    private Long destinationAccountId;

    public TransactionDTO() {
    }

    public TransactionDTO(Transaction.Type type, BigDecimal amount, String description, Long sourceAccountId, Long destinationAccountId) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
    }

    public Transaction.Type getType() {
        return type;
    }

    public void setType(Transaction.Type type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public Long getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(Long destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }
}
