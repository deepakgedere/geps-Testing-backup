package com.enums.infra.invoices.approve;

public enum LInvApprove {

    INVOICE_NAVIGATION_BAR("//a[@href='/OrderProcessing/Invoices']"),
    LIST_CONTAINER("#listContainer tr td"),
    INVOICE_SELECT(".btn btn-sm btn-link p-0 text-primary"),
    APPROVE_BUTTON("#btnApprove"),
    ACCEPT_BUTTON(".bootbox-accept"),
    UPDATE_FINANCE_FIELDS("#btnfinfields"),
    ACCOUNT_TYPE("#select2-accountTypeId-container"),
    DOCUMENT_TYPE("#select2-documentTypeId-container"),
    GENERAL_LEDGER("#select2-generalLedgerId-container"),
    SAVE_FINANCE_FIELDS("#updateFinanceFieldsDetails"),
    BANK_ACCOUNT("#select2-bankAccountId-container"),
    TEXT("#text"),
    TAX_CODE("#select2-taxCodeId-container");

    private final String selector;

//TODO Constructor
    LInvApprove(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }

    public static String getSelector(String selector) {
        return "//li[contains(text(), '" + selector + "')]";
    }
}