package com.constants.orderschedules;

public class LOsApprove {

    public static final String PO_NAVIGATION_BAR = "//*[contains(text(), 'Purchase Orders')]";
    public static final String LIST_CONTAINER = "#listContainer tr td";
    public static final String DETAILS_BUTTON = ".btn-link";
    public static final String VIEW_ORDER_SCHEDULE__BUTTON = "#BtnToApproveOS";
    public static final String APPROVE_BUTTON = "#btnApprove";
    public static final String ACCEPT_BUTTON = ".bootbox-accept";

 //TODO Constructor
    private LOsApprove(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '"+ title +"')]";
    }
}