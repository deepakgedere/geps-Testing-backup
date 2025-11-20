package com.enums.infra.requisitions.reject;

public enum LPrReject {

    REQUISITION_INFRA_LIST_PAGE("//span[text()='Purchase Requisitions Infra']"),
    SEARCH_PR_NUMBER("//input[contains(@class, 'form-control')]"),
    DETAILS_PAGE("//button[@mattooltip='view Details']"),
    REJECT_BUTTON("//button[not(@disabled)]//span[text()=' Reject ']"),
    REASON("//textarea[@autocomplete='off' and @autofocus and contains(@class, 'mat-input-element')]"),
    SUBMIT_BUTTON("//span[text()=' Submit ']"),
    REJECTED_MESSAGE("//a[contains(text(),'rejected requisition')]");

    private final String selector;

//TODO Constructor
    LPrReject(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}