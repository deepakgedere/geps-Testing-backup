package com.constants.requestforquotations;

public class LQuoRequote {

    public static final String RFQ_NAVIGATION_BAR_BUTTON = "//span[contains(text(), 'Request For Quotations')]";
    public static final String REQUOTE_BUTTON = "//a[contains(text(), ' Requote')]";
    public static final String REMARKS = "Regret";
    public static final String ACCEPT_REMARKS_POP_UP = ".bootbox-accept";
    public static final String EMAIL_POP_UP = "#vendorSendMailBtnId";
    public static final String REQUOTE_EDIT_BUTTON = "#btnEditQuote";
    public static final String UPDATE_BUTTON = "#btnUpdate";

    private LQuoRequote(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '"+ title +"')]";
    }
}