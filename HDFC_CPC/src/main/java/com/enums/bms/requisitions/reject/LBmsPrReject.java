package com.enums.bms.requisitions.reject;

public enum LBmsPrReject {
    REQUISITION_BMS("//span[text()='Purchase Requisitions Bms']"),

    SEARCH_PR_NUMBER("//input[contains(@class, 'form-control')]"),
    DETAILS_PAGE("//button[@mattooltip='view Details']"),
    REJECT_BUTTON("//button[not(@disabled)]//span[text()=' Reject ']"),
    REASON("//textarea[@autocomplete='off' and @autofocus and contains(@class, 'mat-input-element')]"),
    SUBMIT_BUTTON("//span[text()=' Submit ']"),
    REJECTED_MESSAGE("//a[contains(text(),'rejected requisition')]");

    private final String selector;

//TODO Constructor
    LBmsPrReject(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}