package com.constants.purchaseorderrequests;

public class LPorSendForApproval {

    public static final String POR_NAVIGATION_BAR = "//span[contains(text(), 'Purchase Order Requests')]";
    public static final String SEND_FOR_APPROVAL__BUTTON = "#btnNewSendApproval";
    public static final String APPROVAL_POP_UP = "//h3[contains(text(), 'Purchase Order Request Send For Approval')]";
    public static final String CFO1_DROPDOWN_LOCATOR = "#select2-role-6-container";
    public static final String CFO2_DROPDOWN_LOCATOR = "#select2-role-7-container";
    public static final String SEARCH_FIELD = ".select2-search__field";
    public static final String PRESIDENT_DROPDOWN_LOCATOR = "#select2-role-8-container";
    public static final String SUBMIT_BUTTON = "#btnSendForApproval";
    public static final String YES = ".bootbox-accept";

//TODO Constructor
    private LPorSendForApproval(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '"+ title +"')]";
    }

    public static String getCfoId(String id){
        return "//li[contains(text(), '"+ id +"')]";
    }

    public static String getPresidentId(String id){
        return "//li[contains(text(), '"+ id +"')]";
    }
}
