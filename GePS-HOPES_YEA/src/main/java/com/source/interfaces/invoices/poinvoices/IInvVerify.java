package com.source.interfaces.invoices.poinvoices;

public interface IInvVerify {

    int verify(String referenceId, String transactionId, String uid);
}