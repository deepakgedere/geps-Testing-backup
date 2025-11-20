package com.classes.login;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Epic("Login")
    @Feature("Login Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Description: Verify The User Can Able To Login")
    @Test(description = "Login Test")
    public void loginTest() {
        try {
            String emailId = jsonNode.get("mailIds").get("procurementManagerWest").asText();
            iLogin.performLogin(emailId);
        } catch (Exception exception) {
            logger.error("Exception in Login Test Function: {}", exception.getMessage());
        }
    }
}