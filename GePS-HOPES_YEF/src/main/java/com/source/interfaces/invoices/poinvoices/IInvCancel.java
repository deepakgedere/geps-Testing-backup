package com.source.interfaces.invoices.poinvoices;

public interface IInvCancel {

    int cancel(String referenceId, String transactionId, String uid, String type);
}