package com.source.interfaces.purchaseorderrequests;

public interface IPorApprove {

    void savePorAprovers(String type, String purchaseType);
    int approve(String type, String purchaseType);
}