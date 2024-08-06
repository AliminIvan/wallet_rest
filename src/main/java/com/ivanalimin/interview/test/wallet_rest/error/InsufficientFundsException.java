package com.ivanalimin.interview.test.wallet_rest.error;

public class InsufficientFundsException extends AppException{

    public InsufficientFundsException(String message) {
        super(message);
    }
}
