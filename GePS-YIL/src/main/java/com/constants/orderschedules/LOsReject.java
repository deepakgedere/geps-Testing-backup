package com.constants.orderschedules;

public class LOsReject {

    public static final String PO_NAVIGATION_BAR = "//*[contains(text(), 'Purchase Orders')]";
    public static final String VIEW_ORDER_SCHEDULE__BUTTON = "#BtnToApproveOS";
    public static final String REJECT_BUTTON = "#btnReject";
    public static final String REMARKS_INPUT = ".bootbox-input";
    public static final String ACCEPT_BUTTON = ".bootbox-accept";

//TODO Constructor
    private LOsReject(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '"+ title +"')]";
    }
}