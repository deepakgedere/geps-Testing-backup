
package com.enums.infra.purchaseorders.amend;

public enum LPoAmend {
    PO_LIST_PAGE_INFRA_AMEND("//span[text()='Purchase Orders INFRA']"),
    DETAILS_PAGE("//button[@mattooltip='View Details']"),
    AMEND_PO_BUTTON("//button[text()='Amend PO']"),
    AMEND_PO_REASON("//input[@formcontrolname='suspendRemark']"),
    CLICKING_AMEND_PO_BUTTON("//span[text()=' Amend PO ']"),
    OK_APPLICABLE_RATECONTRACT("//span[text()='OK']"),
    AMEND_PO_CONFIRMATION("//span[text()=' Yes ']"),
    ROW_STATUS("//td[@data-label='Status']//span");
    private final String selector;

    //TODO Constructor
    LPoAmend(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}