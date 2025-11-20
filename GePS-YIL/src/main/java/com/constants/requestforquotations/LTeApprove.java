package com.constants.requestforquotations;

public class LTeApprove {
    public static final String RFQ_NAVIGATION_BAR = "//span[contains(text(), 'Request For Quotations')]";
    public static final String YES = ".bootbox-accept";
    public static final String REMARKS_INPUT_LOCATOR = ".bootbox-input";
    public static final String MY_APPROVALS = "//span[contains(text(), 'My Approvals')]";
    public static final String APPROVE_BUTTON = "#btnApprove";

    //TODO Constructor
    private LTeApprove(){
    }

    public static String getTitle(String title){
        return "//*[contains(text(), '" + "TE : " + title + "')]";
    }

    public static String getTeApprover(String approver){
        return "//li[contains(text(), '"+ approver +"')]";
    }
}

