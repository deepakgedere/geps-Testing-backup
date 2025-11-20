package com.enums.infra.invoices.suspend;

public enum LInvSuspend {

    INVOICE_NAVIGATION_BAR("//a[@href='/OrderProcessing/Invoices']"),
    LIST_CONTAINER("#listContainer tr"),
    INVOICE_SELECT(".btn btn-sm btn-link p-0 text-primary"),
    SUSPEND_BUTTON("#btnToSuspendInvoice"),
    REMARKS_INPUT(".bootbox-input"),
    ACCEPT_BUTTON(".bootbox-accept");

    private final String selector;

//TODO Constructor
    LInvSuspend(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}