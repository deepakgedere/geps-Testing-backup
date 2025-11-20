package com.source.login;
import com.base.BaseMain;
import com.base.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseMain {

    boolean loginStatus = false;

    @Epic("Login")
    @Feature("Login Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Description: Verify The User Can Able To Login")
    @Test(description = "Login Test")

    @Given("Cluster Head has access to Login")
    public void clusterHeadHasAccessToLogin() {
        try {
            String email = jsonNode.get("mailIds").get("clusterHead").asText();
            loginStatus = iLogin.performLogin(email);
        } catch (Exception exception) {
            logger.error("Exception in Cluster Head Login Function: {}", exception.getMessage());
        }
    }

    @Then("The Home page should be visible")
    public void theHomePageShouldBeVisible() {
        try {
            if (loginStatus) {
                Assert.assertTrue(true, "Login was not successful.");
            } else {
                Assert.fail();
                logger.error("Login failed, Home page is not visible.");
            }
        } catch (Exception exception) {
            logger.error("Exception in Home Page Visibility Check: {}", exception.getMessage());
        }
    }
}