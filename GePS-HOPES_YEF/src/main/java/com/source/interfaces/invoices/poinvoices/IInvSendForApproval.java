package com.source.interfaces.invoices.poinvoices;

public interface IInvSendForApproval {
    int sendForApproval(String referenceId, String transactionId, String uid, String type);
}