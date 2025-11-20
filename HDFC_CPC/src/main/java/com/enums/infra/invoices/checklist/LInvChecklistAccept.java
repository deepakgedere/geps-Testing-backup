package com.enums.infra.invoices.checklist;

public enum LInvChecklistAccept {

    INVOICE_NAVIGATION_BAR("//a[@href='/OrderProcessing/Invoices']"),
    LIST_CONTAINER("#listContainer tr"),
    INVOICE_SELECT(".btn btn-sm btn-link p-0 text-primary"),
    CHECKLIST_BUTTON("//*[contains(text(), 'Check List')]"),
    SELECT_ALL_CHECKBOXES("#selctAllId"),
    ACCEPT_CHECKLIST_BUTTON("#acceptCheckListId"),
    ACCEPT_BUTTON(".bootbox-accept");

    private final String selector;

//TODO Constructor
    LInvChecklistAccept(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}