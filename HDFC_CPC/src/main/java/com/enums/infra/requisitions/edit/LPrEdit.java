package com.enums.infra.requisitions.edit;

public enum LPrEdit {

    REQUISITION_INFRA_LIST_PAGE_EDIT("//span[text()='Purchase Requisitions Infra']"),
    SEARCH_PR_NUMBER_EDIT("//input[contains(@class, 'form-control')]"),
    PR_EDIT_BUTTON("//button[@mattooltip='Edit Purchase Requisition']"),
    EDIT_NEXT_BUTTON("//span[text()=' Next ']"),
    EDIT_NEXT_BUTTON_ITEM("(//span[@class='mat-button-wrapper' and normalize-space(text())='Next']/mat-icon[@role='img' and text()='arrow_forward'])[2]"),
    EDIT_NEXT_BUTTON_COSTCODE("(//span[@class='mat-button-wrapper' and normalize-space(text())='Next']/mat-icon[@role='img' and text()='arrow_forward'])[3]");

    private final String selector;

//TODO Constructor
    LPrEdit(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}