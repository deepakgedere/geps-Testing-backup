package com.source.classes.orderschedules.create;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class OsCreateTest extends BaseTest {

    @Epic("Order Schedules")
    @Feature("Order Schedule Create")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Vendor Can Able To Create The Order Schedule")
    @Test(description = "Order Schedule Create Test")
    @Parameters({"type", "purchaseType"})
    public void create(String type, String purchaseType) {
        try {
            int status = iOsCreate.create(type, purchaseType);
            Assert.assertEquals(200, status, "OS Create was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in OS Create Test Function: {}", exception.getMessage());
            Assert.fail("Exception in OS Create Test Function: " + exception.getMessage());
        }
    }
}