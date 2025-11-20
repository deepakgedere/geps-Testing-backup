package com.source.classes.login;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.login.LLogin.*;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class Login implements ILogin {

    Logger logger;
    Page page;
    JsonNode jsonNode;

    private Login() {
    }

//TODO Constructor
    public Login(JsonNode jsonNode, Page page) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.logger = LoggerUtil.getLogger(Login.class);
    }


    public int performLogin(String emailId) {
        int status = 0;
        try {
            String appUrl = jsonNode.get("configSettings").get("appUrl").asText();
            String password = jsonNode.get("configSettings").get("loginPassword").asText();

            Locator email = page.locator(EMAIL);
            locatorVisibleHandler(email);
            email.fill(emailId);

            Locator passwordLocator = page.locator(PASSWORD);
            locatorVisibleHandler(passwordLocator);
            passwordLocator.fill(password);

            Locator loginButton = page.locator(LOGIN_BUTTON);
            locatorVisibleHandler(loginButton);
            loginButton.click();

            APIResponse apiResponse = page.request().fetch(appUrl + "/api/users/current");
            status = apiResponse.status();
        } catch (Exception exception) {
            logger.error("Error in Perform Login Function: {}", exception.getMessage());
        }
        return status;
    }

    //TODO This function is only used for currency Exchange Rate
    public void performLogin(String emailId, Page page) {
        try {
            String password = jsonNode.get("configSettings").get("loginPassword").asText();

            Locator email = page.locator(EMAIL);
            locatorVisibleHandler(email);
            email.fill(emailId);

            Locator passwordLocator = page.locator(PASSWORD);
            locatorVisibleHandler(passwordLocator);
            passwordLocator.fill(password);

            Locator loginButton = page.locator(LOGIN_BUTTON);
            locatorVisibleHandler(loginButton);
            loginButton.click();
        } catch (Exception exception) {
            logger.error("Error in Perform Login Function for Admin (Currency Exchange Rate): {}", exception.getMessage());
        }
    }
}