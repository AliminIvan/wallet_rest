package com.ivanalimin.interview.test.wallet_rest.web;

import com.ivanalimin.interview.test.wallet_rest.error.InsufficientFundsException;
import com.ivanalimin.interview.test.wallet_rest.error.NotFoundException;
import com.ivanalimin.interview.test.wallet_rest.model.Wallet;
import com.ivanalimin.interview.test.wallet_rest.repository.WalletRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping(value = WalletController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class WalletController {
    static final String REST_URL = "/api/v1";
    @Autowired
    private WalletRepository repository;

    @GetMapping("/wallets/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Wallet get(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        return repository.findById(uuid).orElseThrow(() -> new NotFoundException(
                "Wallet with id=" + id + " not found in database"));
    }

    @PostMapping(value = "/wallet", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeAmount(@Valid @RequestBody Wallet wallet) {
        Wallet walletForUpdate = repository.getForUpdate(wallet.getWalletId())
                .orElseThrow(() -> new NotFoundException(
                        "Wallet with id=" + wallet.getWalletId() + " not found in database"));
        BigDecimal newAmount = wallet.getAmount();
        BigDecimal dbAmount = walletForUpdate.getAmount();
        switch (wallet.getOperationType()) {
            case DEPOSIT -> walletForUpdate.setAmount(dbAmount.add(newAmount));
            case WITHDRAW -> {
                if (newAmount.compareTo(dbAmount) > 0) {
                    throw new InsufficientFundsException("Insufficient funds for the operation");
                }
                walletForUpdate.setAmount(dbAmount.subtract(newAmount));
            }
        }
    }
}
