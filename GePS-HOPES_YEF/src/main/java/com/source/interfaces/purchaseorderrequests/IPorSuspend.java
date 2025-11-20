package com.source.interfaces.purchaseorderrequests;

public interface IPorSuspend {

    int suspend(String type, String purchaseType);
    int suspendPorEdit(String type, String purchaseType);
    int suspendRfqOrPrEdit(String type, String purchaseType);
}