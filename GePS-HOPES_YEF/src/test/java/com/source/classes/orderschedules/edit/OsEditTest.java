package com.source.classes.orderschedules.edit;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class OsEditTest extends BaseTest {

    @Epic("Order Schedules")
    @Feature("Order Schedule Edit")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Vendor Can Able To Edit The Order Schedule")
    @Test(description = "Order Schedule Edit Test")
    @Parameters({"type", "purchaseType"})
    public void edit(String type, String purchaseType) {
        try {
            int status = iOsEdit.edit(type, purchaseType);
            Assert.assertEquals(200, status, "OS Edit was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in OS Edit Test Function: {}", exception.getMessage());
            Assert.fail("Exception in OS Edit Test Function: " + exception.getMessage());
        }
    }
}