package com.source.interfaces.login;
import com.microsoft.playwright.Page;

public interface ILogin {
    int performLogin(String emailId);
    void performLogin(String emailId, Page page);
}