package com.ivanalimin.interview.test.wallet_rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "wallet_id")
    private UUID walletId;
    @Column(name = "amount")
    @Min(0)
    private BigDecimal amount;
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private OperationType operationType;

    public Wallet() {
    }

    public Wallet(UUID walletId, BigDecimal amount) {
        this.walletId = walletId;
        this.amount = amount;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "walletId=" + walletId +
                ", amount=" + amount +
                '}';
    }
}
