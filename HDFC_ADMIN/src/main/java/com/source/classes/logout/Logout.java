package com.source.classes.logout;
import com.enums.logout.LLogout;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.logout.ILogout;
import com.microsoft.playwright.Page;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

public class Logout implements ILogout {

    protected Logger logger = LoggerUtil.getLogger(Logout.class);
    protected Page page;

    private Logout() {
    }

    //TODO Constructor
    public Logout(Page page) {
        this.page = page;
    }

    public void performLogout() {
        try {
            page.waitForLoadState(LoadState.NETWORKIDLE);
            page.waitForSelector(LLogout.PERSON_BUTTON.getSelector()).click();
            page.waitForSelector(LLogout.LOGOUT.getSelector()).click();
//            page.waitForSelector(LLogout.LOGIN_PAGE_VALIDATION.getSelector());
            page.waitForSelector("//span[contains(text(),'Back to Login')]");
        } catch (Exception exception) {
            logger.error("Exception in Perform Logout function: {}", exception.getMessage());
        }
    }

    public void closeBrowser(Page page) {
        if (page != null && !page.isClosed()) {
            page.close(); // optional if closing context anyway
        }
        BrowserContext context = page.context();
        if (context != null) {
            context.close(); // closes all pages in this context
        }
        // If you created Browser per suite, close it in a suite-level teardown
        // browser.close();
    }

}