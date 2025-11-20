package com.source.classes.requestforquotations.suspend;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class RfqSuspendRfqEditTest extends BaseTest {

    @Epic("Request For Quotation")
    @Feature("Request For Quotation Suspend Request For Quotation Edit")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Buyer Can Able To Suspend The Request For Quotation and Edit The Request For Quotation")
    @Test(description = "Request For Quotation Suspend Request For Quotation Edit Test")
    @Parameters({"type"})
    public void RfqSuspendRfqEditTestMethod(String type) {
        try {
            int status = iRfqSuspend.suspendRfqEdit(type);
            Assert.assertEquals(200, status, "Requisition Approve was not successful");
        } catch (Exception exception) {
            logger.error("Exception in RFQ Suspend and Edit Test Function: {}", exception.getMessage());
            Assert.fail("Exception in RFQ Suspend and Edit Test Function: " + exception.getMessage());
        }
    }
}