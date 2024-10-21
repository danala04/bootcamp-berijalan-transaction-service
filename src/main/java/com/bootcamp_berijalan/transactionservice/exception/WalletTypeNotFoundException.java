package com.bootcamp_berijalan.transactionservice.exception;

public class WalletTypeNotFoundException extends RuntimeException {
    public WalletTypeNotFoundException(String message) {
        super(message);
    }
}
