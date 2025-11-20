package com.source.interfaces.bms.purchaseOrders.reject;

public interface IBmsPoReject {
    void poApproverLogin();
    void poDetails(String trnId);
    void reject();
}
