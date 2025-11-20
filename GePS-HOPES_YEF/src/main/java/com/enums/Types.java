package com.enums;

public enum Types {

    PROJECT_ORDER ("PS"),
    SALES_ORDER ("Sales"),
    SERVICE_ORDER ("SD");

    private final String type;

//TODO Constructor
    Types(String type) {
        this.type = type;
    }

    public static String getType(String type) {
        return type;
    }
}