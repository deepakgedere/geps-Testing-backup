package com.constants.requisitions;

public class LPrReject {

    public static final String REJECT_BUTTON = "#btnReject";
    public static final String REJECTED_REMARKS = ".bootbox-input";
    public static final String SUBMIT_BUTTON = ".bootbox-accept";

    //TODO Constructor
    private LPrReject(){
    }

    public static String getTitle(String title){
        String title1 = "//*[contains(text(), '" + title + "')]";
        return title1;
    }
}
