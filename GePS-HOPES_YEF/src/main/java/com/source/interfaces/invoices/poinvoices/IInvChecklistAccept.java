package com.source.interfaces.invoices.poinvoices;

public interface IInvChecklistAccept {

    int accept(String referenceId, String transactionId, String uid, String type);
}