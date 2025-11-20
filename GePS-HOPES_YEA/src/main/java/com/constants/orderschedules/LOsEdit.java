package com.constants.orderschedules;

public class LOsEdit {

    public static final String OS_NAVIGATION_BAR = "//*[contains(text(), 'Order Schedules')]";
    public static final String LIST_CONTAINER = "#listContainer tr td";
    public static final String DETAILS_BUTTON = ".btn-link";
    public static final String EDIT_BUTTON = "#btnEdit";
    public static final String UPDATE_BUTTON = "#btnUpdate";
    public static final String ACCEPT_BUTTON = ".bootbox-accept";

//TODO Constructor
    private LOsEdit(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '"+ title +"')]";
    }
}