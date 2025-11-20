package com.enums.bms.purchaseOrders.create;

public enum LBmsPoCreate {
    REQUISITION_BMS("//span[text()='Purchase Requisitions Bms']"),
    DETAILS_PAGE("//button[@mattooltip='view Details']"),
    PO_CREATE_BUTTON("//span[contains(text(),'Create PO')]"),
    POCREATE_STATUS("//span[contains(text(),'POCreated')]");

    private final String selector;

    //TODO Constructor
    LBmsPoCreate(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }

    public static String getSelector(String mailId) {
        return "//*[contains(text(), '" + mailId + "')]";
    }

}
