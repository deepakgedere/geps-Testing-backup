package com.enums.bms.requisitions.selectVendor;

public enum LBmsSelectVendor {
    REQUISITION_BMS("//span[text()='Purchase Requisitions Bms']"),
    DETAILS_PAGE("//button[@mattooltip='view Details']"),
    ADD_VENDOR_BUTTON("//button//span[contains(text(),'Add Vendor')]"),
    SELECT_VENDOR_LABEL("//mat-label[text()='Select Vendors']"),
    SEARCH_VENDOR("//input[@placeholder='Select Vendor']"),
    SEARCH_PR_NUMBER_EDIT("//input[contains(@class, 'form-control')]"),
    PR_EDIT_BUTTON("//button[@mattooltip='Edit Purchase Requisition']"),
    EDIT_NEXT_BUTTON("//span[text()=' Next ']"),
    EDIT_NEXT_BUTTON_ITEM("(//span[@class='mat-button-wrapper' and normalize-space(text())='Next']/mat-icon[@role='img' and text()='arrow_forward'])[2]"),
    EDIT_NEXT_BUTTON_COSTCODE("(//span[@class='mat-button-wrapper' and normalize-space(text())='Next']/mat-icon[@role='img' and text()='arrow_forward'])[3]");

    private final String selector;

    //TODO Constructor
    LBmsSelectVendor(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }

    public static String returnSpanContainsText(String text) {
        return "//span[contains(text(), '" + text + "')]";
    }
}
