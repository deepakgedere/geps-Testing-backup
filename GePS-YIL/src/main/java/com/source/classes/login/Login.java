package com.source.classes.login;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.utils.JsonUtil.JsonUtil;
import com.utils.LoggerUtil;
import org.apache.hc.client5.http.auth.AuthCache;
import org.apache.logging.log4j.Logger;
import static com.constants.login.LLogin.*;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class Login implements ILogin {

    Logger logger = LoggerUtil.getLogger(Login.class);;
    Page page;
    JsonNode jsonNode;
    ObjectMapper objectMapper;
    private Login() {
    }

//TODO Constructor
    public Login(JsonNode jsonNode, Page page, ObjectMapper objectMapper) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.objectMapper = objectMapper;
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

            page.waitForLoadState(LoadState.NETWORKIDLE);

            String loginSuccessLocator = "//span[contains(text(),'Yokogawa India Limited')]";
            if(page.waitForSelector(loginSuccessLocator).isVisible()){
                status = 200;
//                logger.info("Login was successful, locator found: {}", loginSuccessLocator);
            }
            else {
                logger.error("Login was not successful, locator not found: {}", loginSuccessLocator);
                status = 401; // Unauthorized
            }
        } catch (Exception exception) {
            logger.error("Error in Perform Login Function: {}", exception.getMessage());
        }
        return status;
    }
}