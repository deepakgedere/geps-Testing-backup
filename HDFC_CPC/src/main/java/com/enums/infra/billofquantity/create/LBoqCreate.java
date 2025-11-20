package com.enums.infra.billofquantity.create;

public enum LBoqCreate {

    DETAILS_PAGE("//button[@mattooltip='view Details']"),
    LOCATION_ADMIN_LOCATOR("//tr[.//td[contains(text(),'Location Admin')]]//span[2]"),
    REQUISITION_INFRA_CREATE_BOQ("//span[text()='Purchase Requisitions Infra']"),
    CREATE_BOQ_BUTTON("//span[text()=' Create BOQ ']"),
    ITEMCATEGORY_LOCATOR("//td[contains(@class,'mat-column-ItemCategory')]"),
    NORMAL_ITEMCATEGORY_LOCATOR("//td[contains(@class,'mat-column-itemCategory')]"),
    ITEM_CHECKBOX("//mat-checkbox"),
    ITEM_QUANTITY("//input[@formcontrolname='quantity']"),
    SAVE_VENDOR_ADDRESS("//app-normal-boq-vendor-address-selection//span[text()='Save']"),
    ADD_BOQ_ITEM("//span[text()='Add BOQs ']"),
    BRANCH_CARPET_AREA("//input[@mattooltip='Enter branch carpet area']"),
    APPROVAL_SEARCH_BOQ("input[placeholder='Search by Employee Code, Employee Name, Email']"),
    APPLY_APPROVAL_CREATE_BOQ("//span[text()=' Apply Approvers ']"),
    BOQ_APPROVALPENDING_STATUS("//span[contains(text(),'BOQApprovalPending')]"),
    APPROVAL_ROWS("//app-approval-editor//tbody//tr/td[3]"),
    APPROVAL_ROW_CONTENT("//mat-card"),
    APPROVAL_ROW_SELECTAPPROVERB_BUTTON("//following-sibling::td[1]/button"),
    BOQCREATED_STATUS("//span[contains(text(),'BOQCreated')]"),
    BOQ_SUBMIT_BUTTON("//span[contains(text(),'Create Submit')]"),
    PR_CREATE_BUTTON_BOQ("//span[text()=' Submit ']"),
    YES_BUTTON_BOQ("//span[text()=' Yes ']");

    private final String selector;

//TODO Constructor
    LBoqCreate(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }

    public static String getSelector(String selector) {
        return "//th[.//span[normalize-space(text())='" + selector.trim() + "']]//mat-radio-button";
    }

    public static String getItemRow(String itemCategoryName) {
        return "//tr[td[div[contains(text(),'" + itemCategoryName + "')]]]";
    }

    public static String vendorAddressRadioButton(String vendorAddress) {
        return "//tr[td[contains(text(),'" + vendorAddress + "')]]//mat-radio-button";
    }

    public static String getSelector1(String selector) {
        return "//*[contains(text(), '" + selector + " ')]";
    }
}