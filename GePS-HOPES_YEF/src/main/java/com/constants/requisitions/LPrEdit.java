package com.constants.requisitions;

public class LPrEdit {

    public static final String EDIT_BUTTON = "#btnEdit";
    public static final String UPDATE_BUTTON = "#btnUpdate";
    public static final String YES = ".bootbox-accept";

//TODO Constructor
    private LPrEdit(){
    }

    public static String getTitle(String title){
        String getTitle = "//*[contains(text(), '" + title + "')]";
        return getTitle;
    }
}