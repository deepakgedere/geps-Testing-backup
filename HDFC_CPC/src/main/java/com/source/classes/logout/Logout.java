package com.source.classes.logout;
import com.enums.logout.LLogout;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.logout.ILogout;
import com.microsoft.playwright.Page;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

public class Logout implements ILogout {

    Logger logger;
    Page page;

    private Logout() {
    }

//TODO Constructor
    public Logout(Page page) {
        this.page = page;
        this.logger = LoggerUtil.getLogger(Logout.class);
    }

    public void performLogout() {
        try {
            page.waitForLoadState(LoadState.NETWORKIDLE);
            page.waitForSelector(LLogout.PERSON_BUTTON.getSelector()).click();
            page.waitForSelector(LLogout.LOGOUT.getSelector()).click();
//            Locator x = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("User Manual"));
//            x.waitFor(new Locator.WaitForOptions().setTimeout(5000));
            page.waitForSelector(LLogout.BACK_TO_LOGIN.getSelector());
        } catch (Exception exception) {
            logger.error("Exception in Perform Logout function: {}", exception.getMessage());
        }
    }
}