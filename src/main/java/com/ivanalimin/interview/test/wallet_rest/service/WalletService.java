package com.ivanalimin.interview.test.wallet_rest.service;

import com.ivanalimin.interview.test.wallet_rest.dto.WalletDTO;

import java.util.UUID;

public interface WalletService {
    WalletDTO findById(UUID uuid);
    void changeAmount(WalletDTO wallet);
}
