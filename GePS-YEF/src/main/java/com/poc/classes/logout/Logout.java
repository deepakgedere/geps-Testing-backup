package com.poc.classes.logout;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.poc.interfaces.logout.ILogout;
import static com.factory.PlaywrightFactory.waitForLocator;
import static com.constants.logout.LLogout.LOGIN_AVATAR;
import static com.constants.logout.LLogout.SIGN_OUT;

public class Logout implements ILogout {

    Page page;

//TODO Constructor
    public Logout(Page page) {
        this.page = page;
    }

    public void performLogout() {
        try {
            Locator avatarLocator = page.locator(LOGIN_AVATAR);
            waitForLocator(avatarLocator);
            avatarLocator.click();
            Locator signOutLocator = page.locator(SIGN_OUT);
            waitForLocator(signOutLocator);
            signOutLocator.click();
        } catch (Exception error) {
            System.out.println("Logout error: " + error.getMessage());
        }
    }
}