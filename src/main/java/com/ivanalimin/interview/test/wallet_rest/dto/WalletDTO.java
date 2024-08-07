package com.ivanalimin.interview.test.wallet_rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ivanalimin.interview.test.wallet_rest.model.OperationType;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDTO {
    private UUID walletId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OperationType operationType;
    @Min(0)
    private BigDecimal amount;
}
