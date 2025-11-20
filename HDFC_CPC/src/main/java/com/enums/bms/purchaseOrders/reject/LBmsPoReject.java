package com.enums.bms.purchaseOrders.reject;

public enum LBmsPoReject {

    BMS_PO_LIST_PAGE("//span[text()='Purchase Orders BMS']"),
    DETAILS_PAGE("//button[@mattooltip='View Details']"),
    REJECTED_MESSAGE("//a[contains(text(),'Rejected PO')]");

    private final String selector;

    //TODO Constructor
    LBmsPoReject(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}