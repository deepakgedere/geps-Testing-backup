package com.enums.infra.invoices.sendforapproval;

public enum LInvSendForApproval {

    INVOICE_NAVIGATION_BAR("//a[@href='/OrderProcessing/Invoices']"),
    LIST_CONTAINER("#listContainer tr"),
    INVOICE_SELECT(".btn btn-sm btn-link p-0 text-primary"),
    SEND_FOR_APPROVAL_BUTTON("#btnSendApproval"),
    ACCEPT_BUTTON(".bootbox-accept");

    private final String selector;

//TODO Constructor
    LInvSendForApproval(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}