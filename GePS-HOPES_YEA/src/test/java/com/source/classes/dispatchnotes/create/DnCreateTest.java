package com.source.classes.dispatchnotes.create;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class DnCreateTest extends BaseTest {

    @Epic("Dispatch Notes")
    @Feature("Dispatch Notes Create")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Vendor Can Create The Dispatch Notes")
    @Test(description = "Dispatch Notes Create Test")
    @Parameters({"type", "purchaseType"})
    public void create(String type, String purchaseType) {
        try {
            int status = iDnCreate.create(type, purchaseType);
            Assert.assertEquals(200, status, "Dispatch Note Create was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in Dispatch Notes Create Test function: {}", exception.getMessage());
            Assert.fail("Exception in DN Create Test Function: " + exception.getMessage());
        }
    }
}