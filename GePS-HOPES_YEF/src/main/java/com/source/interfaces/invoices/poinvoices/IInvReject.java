package com.source.interfaces.invoices.poinvoices;

public interface IInvReject {

    int reject(String referenceId, String transactionId, String uid, String type);
}