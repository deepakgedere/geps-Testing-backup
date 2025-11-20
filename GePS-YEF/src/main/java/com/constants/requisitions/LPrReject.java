package com.constants.requisitions;

public class LPrReject {

    public static final String REJECT_BUTTON = "#btnReject";
    public static final String REMARKS = ".bootbox-input";
    public static final String YES = ".bootbox-accept";

    //TODO Constructor
    private LPrReject(){
    }

    public static String getTitle(String title){
        String title1 = "//*[contains(text(), '" + title + "')]";
        return title1;
    }
}
