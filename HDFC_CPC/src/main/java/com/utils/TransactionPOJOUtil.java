package com.utils;

public class TransactionPOJOUtil {

    String transactionId;

//TODO Constructor
    public TransactionPOJOUtil(String transactionId) {
        this.transactionId = transactionId;
    }

//TODO Getters are used to serialize it in Json file
    public String getTransactionId() {
        return transactionId;
    }
}