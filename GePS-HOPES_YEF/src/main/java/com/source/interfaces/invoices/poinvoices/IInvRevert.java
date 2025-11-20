package com.source.interfaces.invoices.poinvoices;

public interface IInvRevert {

    int revert(String referenceId, String transactionId, String uid, String type);
}