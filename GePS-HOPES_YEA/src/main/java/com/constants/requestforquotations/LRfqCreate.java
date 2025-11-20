package com.constants.requestforquotations;

public class LRfqCreate {

    public static final String CREATE_RFQ_BUTTON = "#btnCreateRFQ";
    public static final String NOTES = "#notes";
    public static final String CREATE_BUTTON = "#btnCreate";
    public static final String YES_BUTTON = "//button[contains(text(),'Yes')]";

//TODO Constructor
    private LRfqCreate(){
    }

    public static String getTitle(String title){
        return "//*[contains(text(), '" + title + "')]";
    }
}