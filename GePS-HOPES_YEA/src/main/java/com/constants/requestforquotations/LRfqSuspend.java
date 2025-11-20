package com.constants.requestforquotations;

public class LRfqSuspend {

    public static final String RFQ_NAVIGATION_BAR = "//span[contains(text(), 'Request For Quotations')]";
    public static final String SUSPEND_BUTTON = "#btnToSuspendRfq";
    public static final String REMARKS_INPUT = ".bootbox-input";
    public static final String YES = ".bootbox-accept";


//TODO Constructor
    private LRfqSuspend(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '" + title + "')]";
    }
}