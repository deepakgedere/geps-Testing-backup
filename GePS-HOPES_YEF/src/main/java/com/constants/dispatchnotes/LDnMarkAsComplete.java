package com.constants.dispatchnotes;

public class LDnMarkAsComplete {

    public static final String DN_NAVIGATION_BAR = "//*[contains(text(), 'Dispatch Notes')]";
    public static final String LIST_CONTAINER = "#listContainer tr td";
    public static final String DETAILS_BUTTON = ".btn-link";
    public static final String MARK_AS_COMPLETE_BUTTON = "#btnToMarkasComplete";
    public static final String CANCEL_BUTTON = "#btnToCancel";
    public static final String ACCEPT_BUTTON = ".bootbox-accept";

//TODO Constructor
    private LDnMarkAsComplete(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '"+ title +"')]";
    }
}