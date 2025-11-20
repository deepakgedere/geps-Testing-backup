package com.enums.bms.requisitions.approve;

public enum LBmsPrApprove {

    REQUISITION_BMS("//span[text()='Purchase Requisitions Bms']"),
    DETAILS_PAGE("//button[@mattooltip='view Details']"),
    APPROVE_PR_BUTTON("//button[not(@disabled)]//span[text()=' Approve ']"),
    REASON_APPROVE("//textarea[@autocomplete='off' and @autofocus and contains(@class, 'mat-input-element')]"),
    SUBMIT_APPROVE("//span[text()=' Submit ']"),
    CREATE_PO("//span[text()=' Create PO ']"),
    UPDATE_YES("//span[text()=' Yes ']"),
    CPC_COORDINATOR("//span[strong[contains(text(),'CPC Coordinator')]]");

    private final String selector;

//TODO Constructor
    LBmsPrApprove(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }

    public static String getSelector(String mailId) {
        return "//*[contains(text(), '" + mailId + "')]";
    }

    public static String approverStatus(String approverMailId) {
        String approverEmailId = approverMailId.substring(0,10);
        return "(//app-approval//div//li/div[.//span[contains(text(),'" + approverEmailId +"')]])/span[contains(text(),'Approved')]";
    }

    public static String po_created(String trnNumber) {
        String x = trnNumber.split("TR-")[1].trim();
        return "//td[contains(text(),'" + x + "')]";
    }
}