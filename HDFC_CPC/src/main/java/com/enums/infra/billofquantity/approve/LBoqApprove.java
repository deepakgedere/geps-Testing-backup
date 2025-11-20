package com.enums.infra.billofquantity.approve;

public enum LBoqApprove {

    REQUISITION_INFRA_LIST_PAGE_BOQ_APPROVE("//span[text()='Purchase Requisitions Infra']"),
    APPROVE_PR_BUTTON("//button[not(@disabled)]//span[text()=' Approve ']"),
    REASON_APPROVE("//textarea[@autocomplete='off' and @autofocus and contains(@class, 'mat-input-element')]"),
    SUBMIT_APPROVE("//span[text()=' Submit ']"),
    CREATE_PO("//span[text()=' Create PO ']"),
    DETAILS_PAGE("//button[@mattooltip='view Details']"),
    ;

    private final String selector;

//TODO Constructor
    LBoqApprove(String selector) {
        this.selector = selector;
    }

    public static String approverStatus(int currentApproverCount) {
        return "((//div[@id='boqpanel']//li)[" + currentApproverCount + "]//span)[2]";
    }

    public static String po_created(String trnNumber) {
        String x = trnNumber.split("TR-")[1].trim();
        return "//td[contains(text(),'" + x + "')]";
    }

    public String getSelector() {
        return selector;
    }
}