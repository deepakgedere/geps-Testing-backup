package com.constants.inspections;

public class LInsReadyForInspection {

    public static final String OS_NAVIGATION_BAR = "//*[contains(text(), 'Order Schedules')]";
    public static final String LIST_CONTAINER = "#listContainer tr td";
    public static final String DETAILS_BUTTON = ".btn-link";
    public static final String READY_FOR_INSPECTION_BUTTON = "#btnSendForInspection";
    public static final String CREATE_BUTTON = "#btnCreateInspection";
    public static final String ACCEPT_BUTTON = ".bootbox-accept";

//TODO Constructor
    private LInsReadyForInspection(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '"+ title +"')]";
    }
}