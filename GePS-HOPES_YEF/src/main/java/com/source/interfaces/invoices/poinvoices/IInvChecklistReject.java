package com.source.interfaces.invoices.poinvoices;

public interface IInvChecklistReject {

    int reject(String referenceId, String transactionId, String uid, String type);
}