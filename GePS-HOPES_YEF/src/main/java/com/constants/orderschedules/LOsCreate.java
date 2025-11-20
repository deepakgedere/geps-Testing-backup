package com.constants.orderschedules;

public class LOsCreate {

    public static final String PO_NAVIGATION_BAR = "//*[contains(text(), 'Purchase Orders')]";
    public static final String PO_REFERENCE_ID = "#referenceId";
    public static final String CREATE_OS_BUTTON = "#btnCreateOR";
    public static final String TODAY = "//span[@class='flatpickr-day today']";
    public static final String SCHEDULE_DATE = ".scheduleDate-1";
    public static final String CREATE_BUTTON = "#btnCreate";
    public static final String ACCEPT_BUTTON = ".bootbox-accept";
    public static final String LIST_CONTAINER = "#listContainer tr td";
    public static final String DETAILS_BUTTON = ".btn-link";

//TODO Constructor
    private LOsCreate(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '"+ title +"')]";
    }
}