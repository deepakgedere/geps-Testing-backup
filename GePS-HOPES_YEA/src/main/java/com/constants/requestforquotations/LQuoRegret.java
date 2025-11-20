package com.constants.requestforquotations;

public class LQuoRegret {

    public static final String REGRET_BUTTON = "#btnRegret";
    public static final String REMARKS_POP_UP = ".bootbox-input";
    public static final String REMARKS = "Regret";
    public static final String ACCEPT_REMARKS_POP_UP = ".bootbox-accept";

    private LQuoRegret(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '"+ title +"')]";
    }
}