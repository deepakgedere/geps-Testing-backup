package com.enums.infra.purchaseorders.reject;

public enum LPoReject {

    PO_LIST_PAGE("//span[text()='Purchase Orders INFRA']"),
    DETAILS_PAGE("//button[@mattooltip='View Details']"),
    REJECTED_MESSAGE("//a[contains(text(),'Rejected PO')]");

    private final String selector;

    //TODO Constructor
    LPoReject(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}