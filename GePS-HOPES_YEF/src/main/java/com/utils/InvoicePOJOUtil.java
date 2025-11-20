package com.utils;

public class InvoicePOJOUtil {

    String referenceId;
    String transactionId;
    String uid;
    String type;

//TODO Constructor
    public InvoicePOJOUtil(String referenceId, String transactionId, String uid, String type) {
        this.referenceId = referenceId;
        this.transactionId = transactionId;
        this.uid = uid;
        this.type = type;
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

    public String getType() {
        return type;
    }
}