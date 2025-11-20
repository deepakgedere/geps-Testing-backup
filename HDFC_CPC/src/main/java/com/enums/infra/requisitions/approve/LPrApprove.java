package com.enums.infra.requisitions.approve;

public enum LPrApprove {

    REQUISITION_INFRA_LIST_PAGE_APPROVE("//span[text()='Purchase Requisitions Infra']"),
    SEARCH_PR_NUMBER_APPROVE("//input[contains(@class, 'form-control')]"),
    DETAILS_PAGE("//button[@mattooltip='view Details']"),
    DETAILS_PAGE_APPROVE("//mat-icon[text()='details']"),
    APPROVE_PR_BUTTON("//button[not(@disabled)]//span[text()=' Approve ']"),
    REASON_APPROVE("//textarea[@autocomplete='off' and @autofocus and contains(@class, 'mat-input-element')]"),
    SUBMIT_APPROVE("//span[text()=' Submit ']"),
    CREATE_PO("//span[text()=' Create PO ']"),
    UPDATE_YES("//span[text()=' Yes ']"),
    CPC_COORDINATOR("//span[strong[contains(text(),'CPC Coordinator')]]");

    private final String selector;

//TODO Constructor
    LPrApprove(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }

    public static String getSelector(String mailId) {
        return "//*[contains(text(), '" + mailId + "')]";
    }

    public static String approverStatus(int approverCount) {
        return "(//app-approval//div//li/div[./span])[" + approverCount + "]/span[2]";
    }
}