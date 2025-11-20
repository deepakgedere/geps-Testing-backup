package com.enums.bms.requisitions.edit;

public enum LBmsPrEdit {
    REQUISITION_BMS("//span[text()='Purchase Requisitions Bms']"),
    SEARCH_PR_NUMBER_EDIT("//input[contains(@class, 'form-control')]"),
    PR_EDIT_BUTTON("//button[@mattooltip='Edit Purchase Requisition']"),
    EDIT_NEXT_BUTTON("//span[text()=' Next ']"),
    EDIT_NEXT_BUTTON_ITEM("(//span[@class='mat-button-wrapper' and normalize-space(text())='Next']/mat-icon[@role='img' and text()='arrow_forward'])[2]"),
    EDIT_NEXT_BUTTON_COSTCODE("(//span[@class='mat-button-wrapper' and normalize-space(text())='Next']/mat-icon[@role='img' and text()='arrow_forward'])[3]");

    private final String selector;

    //TODO Constructor
    LBmsPrEdit(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}
