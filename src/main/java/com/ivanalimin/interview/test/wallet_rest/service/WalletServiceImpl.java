package com.ivanalimin.interview.test.wallet_rest.service;

import com.ivanalimin.interview.test.wallet_rest.dto.WalletDTO;
import com.ivanalimin.interview.test.wallet_rest.error.InsufficientFundsException;
import com.ivanalimin.interview.test.wallet_rest.error.NotFoundException;
import com.ivanalimin.interview.test.wallet_rest.model.Wallet;
import com.ivanalimin.interview.test.wallet_rest.repository.WalletRepository;
import com.ivanalimin.interview.test.wallet_rest.web.json.WalletMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WalletServiceImpl implements WalletService{
    private final WalletRepository repository;
    private final WalletMapper mapper;

    @Override
    public WalletDTO findById(UUID uuid) {
        return mapper.modelToDto(repository.findById(uuid).orElseThrow(() -> new NotFoundException(
                "Wallet with id=" + uuid + " not found in database")));
    }

    @Override
    @Transactional
    public void changeAmount(WalletDTO wallet) {
        Wallet walletForUpdate = repository.getForUpdate(wallet.getWalletId())
                .orElseThrow(() -> new NotFoundException(
                        "Wallet with id=" + wallet.getWalletId() + " not found in database"));
        BigDecimal newAmount = wallet.getAmount();
        BigDecimal dbAmount = walletForUpdate.getAmount();
        BigDecimal delta = null;
        switch (wallet.getOperationType()) {
            case DEPOSIT -> delta = dbAmount.add(newAmount);
            case WITHDRAW -> {
                if (newAmount.compareTo(dbAmount) > 0) {
                    throw new InsufficientFundsException("Insufficient funds for the operation");
                }
                delta = dbAmount.subtract(newAmount);
            }
        }
        walletForUpdate.setAmount(delta);
    }
}
