package com.constants.requisitions;

public class LPrBuyerManagerSuspend {

    public static final String SUSPEND_BUTTON = "#btnSuspend";
    public static final String REMARKS = ".bootbox-input";
    public static final String YES = ".bootbox-accept";

//TODO Constructor
    private LPrBuyerManagerSuspend(){
    }

    public static String getTitle(String title){
        String title1 = "//*[contains(text(), '" + title + "')]";
        return title1;
    }
}
