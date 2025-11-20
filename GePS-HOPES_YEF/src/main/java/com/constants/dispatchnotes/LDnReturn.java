package com.constants.dispatchnotes;

public class LDnReturn {

    public static final String DN_NAVIGATION_BAR = "//*[contains(text(), 'Dispatch Notes')]";
    public static final String LIST_CONTAINER = "#listContainer tr td";
    public static final String DETAILS_BUTTON = ".btn-link";
    public static final String DROP_DOWN = "#dropdownMenuButton";
    public static final String RETURN_BUTTON = "#btnToReturn";
    public static final String REMARKS_FIELD = ".bootbox-input";
    public static final String ACCEPT_BUTTON = ".bootbox-accept";


//TODO Constructor
    private LDnReturn(){
    }

    public static String getTitle(String title){
        return "//*[contains(text(), '"+ title +"')]";
    }
}