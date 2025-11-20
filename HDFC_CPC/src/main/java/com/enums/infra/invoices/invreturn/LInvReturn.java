package com.enums.infra.invoices.invreturn;

public enum LInvReturn {

    INVOICE_NAVIGATION_BAR("//a[@href='/OrderProcessing/Invoices']"),
    LIST_CONTAINER("#listContainer tr"),
    INVOICE_SELECT(".btn btn-sm btn-link p-0 text-primary"),
    RETURN_BUTTON("#btnReturn"),
    EMAIL_POP_UP_BUTTON("#vendorSendMailBtnId"),
    REMARKS_INPUT(".bootbox-input"),
    ACCEPT_BUTTON(".bootbox-accept");

    private final String selector;

//TODO Constructor
    LInvReturn(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}