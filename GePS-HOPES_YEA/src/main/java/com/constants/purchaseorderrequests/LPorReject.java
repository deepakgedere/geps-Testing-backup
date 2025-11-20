package com.constants.purchaseorderrequests;

public class LPorReject {

    public static final String POR_NAVIGATION_BAR = "//span[contains(text(), 'Purchase Order Requests')]";
    public static final String REJECT_BUTTON = "#btnReject";
    public static final String REMARKS_INPUT = ".bootbox-input";
    public static final String YES = ".bootbox-accept";
    public static final String APPROVAL_POP_UP = "//h3[contains(text(), 'Purchase Order Request Send For Approval')]";
    public static final String PROJECT_MANAGER_DROP_DOWN = "#select2-PMBId-container";
    public static final String SEARCH_FIELD = ".select2-search__field";
    public static final String DEPARTMENT_MANAGER_DROP_DOWN = "#select2-departmentManagerId-container";
    public static final String DIVISION_MANAGER = "#select2-divisionManagerId-container";
    public static final String SAVE_APPROVAL_USERS = "#btnSendUserFromPM";

//TODO Constructor
    private LPorReject(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '"+ title +"')]";
    }

    public static String getGroupB(String groupB){
        return "//li[contains(text(), '"+ groupB +"')]";
    }

    public static String getGroupC(String groupC){
        return "//li[contains(text(), '"+ groupC +"')]";
    }

    public static String getGroupD(String groupD){
        return "//li[contains(text(), '"+ groupD +"')]";
    }
}