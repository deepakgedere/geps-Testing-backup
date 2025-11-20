package com.enums.logout;

public enum LLogout {

    PERSON_BUTTON ("//mat-icon[text()='person']"),
    LOGOUT ("//span[text()='Log Out']"),
    BACK_TO_LOGIN("//span[contains(text(),'Back to Login')]"),
    LOGIN_PAGE_VALIDATION("//button[contains(text(),'Get OTP')]");

    private final String selector;

//TODO Constructor
    LLogout(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}