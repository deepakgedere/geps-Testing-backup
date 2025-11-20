package com.enums.infra.invoices.edit;

public enum LInvEdit {

    INVOICE_NAVIGATION_BAR("//a[@href='/VendorPortal/Invoices']"),
    LIST_CONTAINER("#listContainer tr"),
    INVOICE_SELECT(".btn btn-sm btn-link p-0 text-primary"),
    EDIT_BUTTON("#btnEdit"),
    POP_UP_ACCEPT("#btnCreate"),
    ACCEPT_BUTTON(".bootbox-accept");

    private final String selector;

//TODO Constructor
    LInvEdit(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}