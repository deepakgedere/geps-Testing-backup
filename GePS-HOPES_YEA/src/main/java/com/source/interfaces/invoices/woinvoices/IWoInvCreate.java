package com.source.interfaces.invoices.woinvoices;

public interface IWoInvCreate {

    void create();
    double gst();
    void ifSgdEnable(double finalGSTPercentage);
    int invoiceCreate();
}