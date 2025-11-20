package com.constants.purchaseorderrequests;

public class LPorApprove {

    public static final String MY_APPROVALS = "//span[contains(text(), 'My Approvals')]";
    public static final String ADD_APPROVERS = "#btnAddApprovers";
    public static final String APPROVAL_POP_UP = "//h3[contains(text(), 'Purchase Order Request Send For Approval')]";
    public static final String PROJECT_MANAGER_DROP_DOWN = "#select2-PMBId-container";
    public static final String SEARCH_FIELD = ".select2-search__field";
    public static final String DEPARTMENT_MANAGER_DROP_DOWN = "#select2-departmentManagerId-container";
    public static final String DIVISION_MANAGER = "#select2-divisionManagerId-container";
    public static final String SAVE_APPROVAL_USERS = "#btnSendUserFromPM";
    public static final String APPROVE_BUTTON = "#btnApprove";
    public static final String ACCEPT_BUTTON = ".bootbox-accept";

//TODO Constructor
    private LPorApprove(){
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
