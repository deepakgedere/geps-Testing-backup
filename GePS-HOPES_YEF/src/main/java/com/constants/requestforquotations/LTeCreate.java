package com.constants.requestforquotations;

public class LTeCreate {

    public static final String RFQ_NAVIGATION_BAR = "//span[contains(text(), 'Request For Quotations')]";
    public static final String TE_CREATE_BUTTON = "#btnCreateTE";
    public static final String VENDOR_SELECT_CHECKBOX = ".border-primary";
    public static final String CREATE_TECHNICAL_EVALUATION_BUTTON = "#btnCreate";
    public static final String SEND_FOR_APPROVAL = "#btnSendApproval";
    public static final String APPROVER_SELECT = ".select2-selection--single";
    public static final String SEARCH_FIELD = ".select2-search__field";
    public static final String SAVE_APPROVER = "#saveApproverAssign";
    public static final String YES = ".bootbox-accept";
    public static final String APPROVE_BUTTON = "#btnApprove";
    public static final String MY_APPROVALS = "//span[contains(text(), 'My Approvals')]";


//TODO Constructor
    private LTeCreate(){
    }

    public static String getTitle(String title){
        return "//*[contains(text(), '" + title + "')]";
    }

    public static String getTeApprover(String approver){
        return "//li[contains(text(), '"+ approver +"')]";
    }
}