
package com.enums.bms.purchaseOrders.amend;

public enum LBmsPoAmend {
    BMS_PO_LIST_PAGE("//span[text()='Purchase Orders BMS']"),
    DETAILS_PAGE("//button[@mattooltip='View Details']"),
    AMEND_PO_BUTTON("//button[text()='Amend PO']"),
    AMEND_PO_REASON("//input[@formcontrolname='suspendRemark']"),
    CLICKING_AMEND_PO_BUTTON("//span[text()=' Amend PO ']"),
    OK_APPLICABLE_RATECONTRACT("//span[text()='OK']"),
    AMEND_PO_CONFIRMATION("//span[text()=' Yes ']"),
    ROW_STATUS("//td[@data-label='Status']//span");
    private final String selector;

    //TODO Constructor
    LBmsPoAmend(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}