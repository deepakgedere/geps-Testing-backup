package com.enums.bms.purchaseOrders.ackreject;

public enum LBmsAckReject {
    ACKNOWLEDGE_BUTTON("//following-sibling::tr[1]//button[@mattooltip='Acknowledge']"),
    SUBMIT_BUTTON("//span[(text()='Submit')]"),
    DO_NOT_ACCEPT_RADIO_BUTTON("//mat-radio-button//div[contains(text(),'Do not')]"),
    PO_LIST_NAV_BUTTON("//span[text()='Purchase Orders']"),
    ASN_LIST_PAGE("//span[text()='Advance Shipping Notes']"),
    ROW_STATUS("//td[2]//span");

    private final String selector;

    //TODO Constructor
    LBmsAckReject(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}
