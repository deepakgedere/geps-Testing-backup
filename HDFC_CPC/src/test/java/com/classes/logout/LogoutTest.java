package com.classes.logout;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;

public class LogoutTest extends BaseTest {

    @Epic("Logout")
    @Feature("Logout Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Description: Verify The User Can Able To Logout")
    @Test(description = "Logout Test")
    public void login() {
        try {
            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Login Test Function: {}", exception.getMessage());
        }
    }
}