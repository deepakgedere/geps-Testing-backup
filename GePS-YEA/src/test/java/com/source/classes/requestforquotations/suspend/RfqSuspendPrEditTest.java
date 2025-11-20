package com.source.classes.requestforquotations.suspend;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class RfqSuspendPrEditTest extends BaseTest {

    @Epic("Request For Quotation")
    @Feature("Request For Quotation Suspend Requisition Edit")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Buyer Can Able To Suspend The Request For Quotation and Edit The Requisition")
    @Test(description = "Request For Quotation Suspend Requisition Edit Test")
    @Parameters({"type", "purchaseType"})
    public void RfqSuspendPrEdit(String type, String purchaseType) {
        try {
            int status = iRfqSuspend.suspendPREdit(type, purchaseType);
            Assert.assertEquals(200, status, "Requisition Approve was not successful");
        } catch (Exception exception) {
            logger.error("Exception in RFQ Suspend PR Edit Test Function: {}", exception.getMessage());
            Assert.fail("Exception in RFQ Suspend PR Edit Test Function: " + exception.getMessage());
        }
    }
}