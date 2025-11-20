package com.constants.requestforquotations;

public class LReadyForEvaluation {

    public static final String RFQ_NAVIGATION_BAR = "//span[contains(text(), 'Request For Quotations')]";
    public static final String READY_FOR_EVALUATION_BUTTON = "#btnReadyForEvalution";
    public static final String YES = ".bootbox-accept";

//TODO Constructor
    private LReadyForEvaluation(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '" + title + "')]";
    }
}