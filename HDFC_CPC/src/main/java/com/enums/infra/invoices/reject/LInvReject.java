package com.enums.infra.invoices.reject;

public enum LInvReject {

    INVOICE_NAVIGATION_BAR("//a[@href='/OrderProcessing/Invoices']"),
    LIST_CONTAINER("#listContainer tr"),
    INVOICE_SELECT(".btn btn-sm btn-link p-0 text-primary"),
    REJECT_BUTTON("#btnReject"),
    REMARKS_INPUT(".bootbox-input"),
    ACCEPT_BUTTON(".bootbox-accept");

    private final String selector;

//TODO Constructor
    LInvReject(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}