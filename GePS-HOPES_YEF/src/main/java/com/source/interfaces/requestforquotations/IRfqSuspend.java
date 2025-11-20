package com.source.interfaces.requestforquotations;

public interface IRfqSuspend {

    int suspendRfqEdit(String type);
    int suspendPREdit(String type, String purchaseType);
}