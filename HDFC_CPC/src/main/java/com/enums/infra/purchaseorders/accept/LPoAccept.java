package com.enums.infra.purchaseorders.accept;

public enum LPoAccept {

    PO_LIST_NAV_BUTTON("//span[text()='Purchase Orders']"),
    ASN_LIST_PAGE("//span[text()='Advance Shipping Notes']"),
    ACKNOWLEDGE_BUTTON("//following-sibling::tr[1]//button[@mattooltip='Acknowledge']"),
    SUBMIT_BUTTON("//span[(text()='Submit')]"),
    ROW_STATUS("//td[2]//span");

    private final String selector;

    //TODO Constructor
    LPoAccept(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}
