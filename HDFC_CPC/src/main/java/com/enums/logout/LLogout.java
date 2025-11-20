package com.enums.logout;

public enum LLogout {

    PERSON_BUTTON ("//mat-icon[text()='person']"),
    LOGOUT ("//span[text()='Log Out']"),
    BACK_TO_LOGIN("//span[contains(text(),'Back to Login')]");

    private final String selector;

//TODO Constructor
    LLogout(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}