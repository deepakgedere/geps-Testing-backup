package com.constants.freightforwarderrequests;

public class LFfrInvite {

    public static final String DN_NAVIGATION_BAR = "//*[contains(text(), 'Dispatch Notes')]";
    public static final String LIST_CONTAINER = "#listContainer tr td";
    public static final String DETAILS_BUTTON = ".btn-link";
    public static final String INVITE_VENDOR_BUTTON = "#addFrightForwordVendors";
    public static final String DROP_DOWN = "#select2-ffvendorId-container";
    public static final String SEARCH_FIELD = ".select2-search__field";
    public static final String SAVE_BUTTON = "#saveFrightForworderVendor";
    public static final String EMAIL_POP_UP = "#vendorSendMailBtnId";

//TODO Constructor
    private LFfrInvite(){
    }

    public static String getFreightForwarder(String id){
        return "//li[contains(text(), '"+ id +"')]";
    }
}