package com.utils;

public class InvoicePOJOUtil {

    String referenceId;
    String transactionId;
    String uid;

//TODO Constructor
    public InvoicePOJOUtil(String referenceId, String transactionId, String uid) {
        this.referenceId = referenceId;
        this.transactionId = transactionId;
        this.uid = uid;
    }

//TODO Getters are used to serialize it in Json file
    public String getReferenceId() {
        return referenceId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getUid() {
        return uid;
    }
}