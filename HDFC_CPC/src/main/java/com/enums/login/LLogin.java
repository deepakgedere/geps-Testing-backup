package com.enums.login;

public enum LLogin {

    EMAIL("//input[@placeholder='E-mail']"),
    PASSWORD("//input[@placeholder='Password']"),
    LOGIN_BUTTON("//button[contains(text(), ' Login ')]"),
    ALERT("//p[contains(text(), 'Unauthorized use of the HDFC Bank applications is prohibited')]"),
    PROCEED_BUTTON("//span[contains(text(), 'Proceed')]"),
    PROCUREMENT_MANAGER_POPUP("//h4[contains(text(),'Select Your Option')]"),
    CLOSE_BUTTON("//mat-icon[contains(text(), 'close')]");

    private final String selector;

//TODO Constructor
    LLogin(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}