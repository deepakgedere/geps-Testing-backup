package com.constants.inspections;

public class LInsAssign {

    public static final String OS_NAVIGATION_BAR = "//*[contains(text(), 'Order Schedules')]";
    public static final String LIST_CONTAINER = "#listContainer tr td";
    public static final String DETAILS_BUTTON = ".btn-link";
    public static final String ASSIGN_INSPECTOR_BUTTON = "#btnAssignInspector";
    public static final String SELECT_INSPECTOR_DROP_DOWN = "#select2-InspectionId-container";
    public static final String SEARCH_FIELD = ".select2-search__field";
    public static final String SAVE_INSPECTOR = "#saveInspector";
    public static final String ACCEPT_BUTTON = ".bootbox-accept";

//TODO Constructor
    private LInsAssign(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '"+ title +"')]";
    }

    public static String getRequestorId(String id){
        return "//li[contains(text(), '"+ id +"')]";
    }
}