package com.constants.requestforquotations;

public class LCeCreate {

    public static final String RFQ_NAVIGATION_BAR = "//span[contains(text(), 'Request For Quotations')]";
    public static final String CREATE_BUTTON = "#btnCreateCE";
    public static final String SELECTION_CRITERIA = "select[onchange='isSelect(event)']";
    public static final String OPTION = "Yes";
    public static final String SUBMIT_BUTTON = "#btnSubmitCE";
    public static final String ACCEPT_BUTTON = ".bootbox-accept";

//TODO Constructor
    private LCeCreate(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '"+ title +"')]";
    }
}