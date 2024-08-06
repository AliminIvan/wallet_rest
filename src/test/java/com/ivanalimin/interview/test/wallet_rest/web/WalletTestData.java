package com.ivanalimin.interview.test.wallet_rest.web;

import com.ivanalimin.interview.test.wallet_rest.model.Wallet;

import java.math.BigDecimal;
import java.util.UUID;

public class WalletTestData {
    public static final UUID UUID_1 = UUID.fromString("17de1345-03b3-4d74-9477-491149d9f2f8");
    public static final Wallet WALLET_1 = new Wallet(UUID_1, BigDecimal.valueOf(1000));
    public static final UUID UUID_2 = UUID.fromString("cf936b56-f9e1-42d6-85ed-0e1322674053");
    public static final UUID NOT_FOUND = UUID.fromString("cf936b56-f9e1-42d6-85ed-0e1322674088");
}
