package com.constants.requisitions;

public class LPrApprove {

    public static final String APPROVE = "#btnApprove";
    public static final String APPROVE_REMARKS = "//input[@class=\"bootbox-input bootbox-input-text form-control\"]";
    public static final String SUBMIT_BUTTON = ".bootbox-accept";

//TODO Constructor
    private LPrApprove(){
    }

    public static String getTitle(String title){
        String getTitle = "//*[contains(text(), '" + title + "')]";
        return  getTitle;
    }
}