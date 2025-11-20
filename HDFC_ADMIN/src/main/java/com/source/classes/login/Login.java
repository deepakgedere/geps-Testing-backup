package com.source.classes.login;

import com.enums.login.LLogin;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.source.interfaces.login.ILogin;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

public class Login implements ILogin {

    protected Logger logger = LoggerUtil.getLogger(Login.class);
    protected Page page;
    protected JsonNode jsonNode;

    private Login() {
    }

    //TODO Constructor
    public Login(JsonNode jsonNode, Page page) {
        this.jsonNode = jsonNode;
        this.page = page;
    }

    public boolean performLogin(String emailId) {
        boolean loginSuccess = false;
        String password = jsonNode.get("configSettings").get("loginPassword").asText();
        String url = jsonNode.get("configSettings").get("appUrl").asText() + "/login/forget-pasword";
        page.navigate(url);

        page.waitForSelector(LLogin.EMAIL.getSelector()).fill(emailId);
        page.waitForSelector(LLogin.PASSWORD.getSelector()).fill(password);
        page.waitForSelector(LLogin.LOGIN_BUTTON.getSelector()).click();

        for (int i = 0; i < 3; i++) {
            try {
                Locator alert = page.locator(LLogin.ALERT.getSelector());
                Locator procurementManagerPopup = page.locator(LLogin.PROCUREMENT_MANAGER_POPUP.getSelector());

                if (alert.isVisible()) {
                    page.waitForSelector(LLogin.PROCEED_BUTTON.getSelector(), new Page.WaitForSelectorOptions().setTimeout(2000)).click();
                }
                if (procurementManagerPopup.isVisible()) {
                    page.waitForSelector(LLogin.CLOSE_BUTTON.getSelector(), new Page.WaitForSelectorOptions().setTimeout(2000)).click();
                }
                if (page.title().equals("P2P App")) {
                    loginSuccess = true;
                    break;
                }
            } catch (Exception exception) {
                logger.error("Exception handling alert or popup: {}", exception.getMessage());
            }
        }
        if (!loginSuccess) {
            throw new RuntimeException("Login failed after 3 attempts.");
        }
        return loginSuccess;
    }
}