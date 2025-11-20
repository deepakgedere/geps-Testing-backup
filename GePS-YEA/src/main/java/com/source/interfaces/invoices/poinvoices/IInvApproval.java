package com.source.interfaces.invoices.poinvoices;

public interface IInvApproval {

    int approval(String referenceId, String transactionId, String uid);
}