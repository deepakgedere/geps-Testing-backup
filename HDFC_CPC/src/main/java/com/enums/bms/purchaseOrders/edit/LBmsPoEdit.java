package com.enums.bms.purchaseOrders.edit;

public enum LBmsPoEdit {

    BMS_PO_LIST_PAGE("//span[text()='Purchase Orders BMS']"),
    EDIT_PAGE("//button[@mattooltip='Edit Purchase Order']"),
    DETAILS_PAGE("//button[@mattooltip='View Details']"),
    APPLY_APPROVERS("//span[contains(text(),'Apply Approvers')]"),
    UPDATE_BUTTON("//span[contains(text(),'Update')]"),
    APPROVAL_SEARCH_BOQ("input[placeholder='Search by Employee Code, Employee Name, Email']"),
    YES_BUTTON_BOQ("//span[text()=' Yes ']"),
    PENDING_STATUS("//div[./h4[contains(text(),'Approvals')]]//span[contains(text(),'Pending')]"),
    APPROVAL_ROWS("//app-approval-editor//tbody//tr/td[3]"),
    APPOVER_ROW_CONTENT("//mat-card"),
    APPROVER_ROW_SELECT_APPROVER_BUTTON("//following-sibling::td[1]/button")
    ;
    private final String selector;

    //TODO Constructor
    LBmsPoEdit(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}