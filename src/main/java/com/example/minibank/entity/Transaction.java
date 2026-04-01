package com.example.minibank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    public  enum Type{
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Tipo é obrigatório")
    @Enumerated(EnumType.STRING)
    private Type type;

    @NotNull
    @DecimalMin(value = "0.01",message = "Valor deve ser maior que zero")
    private BigDecimal amount=BigDecimal.valueOf(0.00);

    @Size(max = 255, message = "Limite máximo de caracteres")
    private  String description;

    @Column(updatable = false)
    private LocalDateTime timestamp;

    @NotNull(message = "Conta de origem é obrigatório")
    @ManyToOne
    @JoinColumn(name = "source_account_id", nullable = false)
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn( name = "destination_account_id")
    private Account destinationAccount;

    public Transaction() {
    }

    public Transaction( Type type, BigDecimal amount, String description,  Account sourceAccount, Account destinationAccount) {

        this.type = type;
        this.amount = amount;
        this.description = description;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
    }

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

    @AssertTrue(message = "Transfer deve ter conta de destino")
    public boolean isValidTransfer() {
        if (type == Type.TRANSFER) {
            return destinationAccount != null;
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }
}
