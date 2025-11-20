package com.constants.requisitions;

public class LPrAssign {

    public static final String ASSIGN_USER = "#btnAssignUser";
    public static final String SEARCHBOX = ".select2-search__field";
    public static final String SELECT_ASSIGN_USER = "#select2-bgUser-container";
    public static final String SAVE_USER = "#saveBuyerUser";

//TODO Constructor

    private LPrAssign(){
    }

    public static String getTitle(String title){
        String title1 = "//*[contains(text(), '"+ title +"')]";
        return title1;
    }

    public static String getBuyerMailId(String mailId){
        String mailId1 = "//li[contains(text(), '"+ mailId +"')]";
        return mailId1;
    }
}