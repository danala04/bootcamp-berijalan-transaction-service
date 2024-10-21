package com.bootcamp_berijalan.transactionservice.exception;

public class TransactionTypeNotFoundException extends RuntimeException {
    public TransactionTypeNotFoundException(String message) {
        super(message);
    }
}
