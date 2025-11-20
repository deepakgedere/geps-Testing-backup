package com.constants.requisitions;

public class LPrApprove {

    public static final String APPROVE = "#btnApprove";
    public static final String YES = ".bootbox-accept";

//TODO Constructor
    private LPrApprove(){
    }

    public static String getApproveButton(String title){
        String approve = "//*[contains(text(), '" + title + "')]";
        return  approve;
    }
}