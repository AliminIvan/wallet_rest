package com.ivanalimin.interview.test.wallet_rest.error;

import org.springframework.lang.NonNull;

public class AppException extends RuntimeException{
    public AppException(@NonNull String message) {
        super(message);
    }
}
