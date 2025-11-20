package com.enums.bms.purchaseOrders.approve;

public enum LBmsPoApprove {

    BMS_PO_LIST_PAGE("//span[text()='Purchase Orders BMS']"),
    ASN_LIST_PAGE("//span[text()='Advance Shipping Notes']"),
    DETAILS_PAGE("//button[@mattooltip='View Details']"),
    APPROVE_MESSAGE("//div[./h4[contains(text(),'Approvals')]]//span[contains(text(),'Approved')]"),
    VENDOR_EMAIL("//a[contains(text(),'Email')]//following-sibling::a[1]");
    private final String selector;

    //TODO Constructor
    LBmsPoApprove(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}