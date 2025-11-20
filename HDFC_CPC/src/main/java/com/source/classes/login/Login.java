package com.source.classes.login;
import com.enums.login.LLogin;
import com.fasterxml.jackson.databind.JsonNode;
import com.source.interfaces.login.ILogin;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

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

    public void performLogin(String emailId) {
        try {
            String password = jsonNode.get("configSettings").get("loginPassword").asText();
            String url = jsonNode.get("configSettings").get("appUrl").asText() + "/login";
            page.navigate(url);

            page.waitForSelector(LLogin.EMAIL.getSelector()).fill(emailId);
            page.waitForSelector(LLogin.PASSWORD.getSelector()).fill(password);

            // Click login button
            page.waitForSelector(LLogin.LOGIN_BUTTON.getSelector()).click();

            boolean loginSuccess = false;
            for (int i = 0; i < 3; i++) {
                try {
                    page.waitForSelector("//small[contains(text(),'Last Login')]",
                            new Page.WaitForSelectorOptions().setTimeout(2000));
                    loginSuccess = true;
                    break;
                } catch (Exception e) {
                    // Try to handle alert or popup
                    try {
                        Locator alert = page.locator(LLogin.ALERT.getSelector());
                        Locator procurementManagerPopup = page.locator(LLogin.PROCUREMENT_MANAGER_POPUP.getSelector());

                        if (alert.isVisible()) {
                            page.waitForSelector(LLogin.PROCEED_BUTTON.getSelector()).click();
                        } else if (procurementManagerPopup.isVisible()) {
                            page.waitForSelector(LLogin.CLOSE_BUTTON.getSelector()).click();
                        }
                    } catch (Exception inner) {
                        // Ignore and retry
                    }
                }
            }
            if (!loginSuccess) {
                throw new RuntimeException("Login failed after 3 attempts.");
            }

        } catch (Exception exception) {
            logger.error("Exception in Perform Login function: {}", exception.getMessage());
        }
    }
}