package com.source.interfaces.infra.purchaseorder.reject;

public interface IPoReject {
    void poApproverLogin();
    void poDetails(String trnId);
    void reject();
}
