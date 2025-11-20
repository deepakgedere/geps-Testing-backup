package com.source.interfaces.bms.purchaseOrders.approve;

public interface IBmsPoApprove {
    void poApproverLogin();
    void poDetails(String trnId);
    void approve();
}
