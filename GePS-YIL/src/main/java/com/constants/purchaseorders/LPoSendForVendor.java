package com.constants.purchaseorders;

public class LPoSendForVendor {

    public static final String PO_NAVIGATION_BAR = "//*[contains(text(), 'Purchase Orders')]";
    public static final String SEND_FOR_VENDOR_BUTTON = "#btnSendPO";
    public static final String EMAIL_POP_UP = "#vendorSendMailBtnId";

//TODO Constructor
    private LPoSendForVendor(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '"+ title +"')]";
    }
}