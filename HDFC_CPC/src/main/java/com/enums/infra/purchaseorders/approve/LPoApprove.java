package com.enums.infra.purchaseorders.approve;

public enum LPoApprove {

    PO_LIST_PAGE("//span[text()='Purchase Orders INFRA']"),
    ASN_LIST_PAGE("//span[text()='Advance Shipping Notes']"),
    DETAILS_PAGE("//button[@mattooltip='View Details']"),
    APPROVE_MESSAGE("//div[./h4[contains(text(),'Approvals')]]//span[contains(text(),'Approved')]"),
    VENDOR_EMAIL("//a[contains(text(),'Email')]//following-sibling::a[1]");
    private final String selector;

    //TODO Constructor
    LPoApprove(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}