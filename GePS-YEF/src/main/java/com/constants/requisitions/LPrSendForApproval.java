package com.constants.requisitions;

public class LPrSendForApproval {

    public static final String SEND_FOR_APPROVAL_BUTTON = "#btnSendApproval";
    public static final String YES = "//button[contains(text(), 'Yes')]";

    //TODO Constructor
    private LPrSendForApproval(){
    }

    public static String getTitle(String title){
        String title1 = "//*[contains(text(), '" + title + "')]";
        return title1;
    }
}