package com.source.classes.purchaseorderrequests.suspend;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SuspendEditTest extends BaseTest {

    @Epic("Purchase Order Requests")
    @Feature("Purchase Order Request Suspend")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Buyer Can Able To Suspend The Purchase Order Request")
    @Test(description = "Purchase Order Request Suspend Test")
    @Parameters({"type", "purchaseType"})
    public void suspend(String type, String purchaseType) {
        try {
            int status = iPorSuspend.suspendPorEdit(type, purchaseType);
            Assert.assertEquals(200, status, "POR Suspend was not Successful");
            status = iPorSuspend.suspendRfqOrPrEdit(type, purchaseType);
            Assert.assertEquals(200, status, "POR Suspend was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in Suspend Edit Test Function: {}", exception.getMessage());
            Assert.fail("Exception in POR Suspend Edit Test Function: " + exception.getMessage());
        }
    }
}