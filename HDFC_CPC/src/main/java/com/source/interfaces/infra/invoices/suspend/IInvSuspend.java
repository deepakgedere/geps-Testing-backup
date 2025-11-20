package com.source.interfaces.invoices.poinvoices;

public interface IInvSuspend {

    int cancel(String referenceId, String transactionId, String uid);
}