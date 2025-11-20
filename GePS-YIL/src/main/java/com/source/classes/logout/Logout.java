package com.source.classes.logout;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.logout.LLogout.*;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class Logout implements ILogout {

    Logger logger = LoggerUtil.getLogger(Logout.class);
    Page page;
    JsonNode jsonNode;

//TODO Constructor
    public Logout(Page page, JsonNode jsonNode) {
        this.page = page;
        this.jsonNode = jsonNode;
    }

    public void performLogout() {
        try {
            page.waitForLoadState(LoadState.NETWORKIDLE);
            Locator loginAvatarLocator = page.locator(LOGIN_AVATAR);
            locatorVisibleHandler(loginAvatarLocator);
            loginAvatarLocator.click();

            Locator singOutLocator = page.locator(SIGN_OUT);
            locatorVisibleHandler(singOutLocator);
            singOutLocator.click();

            String logoutSuccessLocator = "//a[contains(text(),'If you are an Employee of Yokogawa, Please click here.')]";
            if (page.waitForSelector(logoutSuccessLocator).isVisible()) {
//                logger.info("Logout was successful, locator found: {}", logoutSuccessLocator);
            } else {
                logger.error("Logout was not successful, locator not found: {}", logoutSuccessLocator);
            }
        } catch (Exception exception) {
            logger.error("Error in Perform Logout Function: {}", exception.getMessage());
        }
    }

    //TODO This function is only used for currency Exchange Rate
    public void performLogout(Page page) {
        try {
            Locator loginAvatarLocator = page.locator(LOGIN_AVATAR);
            locatorVisibleHandler(loginAvatarLocator);
            loginAvatarLocator.click();

            Locator singOutLocator = page.locator(SIGN_OUT);
            locatorVisibleHandler(singOutLocator);
            singOutLocator.click();
        } catch (Exception exception) {
            logger.error("Error in Perform Logout Function for Admin (Currency Exchange Rate): {}", exception.getMessage());
        }
    }
}