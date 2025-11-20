package com.constants.requestforquotations;

public class LRfqEdit {

    public static final String RFQ_NAVIGATION_BAR = "//span[contains(text(), 'Request For Quotations')]";
    public static final String EDIT_BUTTON = "#btnEditRfq";
    public static final String UPDATE_BUTTON = "#btnUpdate";
    public static final String REMARKS_POP_UP = ".bootbox-input";
    public static final String REMARKS = "Updated";
    public static final String ACCEPT_REMARKS_POP_UP = ".bootbox-accept";


    private LRfqEdit(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '"+ title +"')]";
    }
}