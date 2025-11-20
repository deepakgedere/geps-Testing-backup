package com.source.classes.login;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Epic("Login")
    @Feature("Login Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Description: Verify The User Can Able To Login")
    @Test(description = "Login Test")
    public void login() {
        try {
            String emailId = jsonNode.get().get("mailIds").get("requesterEmail").asText();
            int statusCode = iLogin.get().performLogin(emailId);
            Assert.assertEquals(statusCode, 200, "Login was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in Login Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Login Test Function: " + exception.getMessage());
        }
    }
}