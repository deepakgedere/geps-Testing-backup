package com.source.classes.requisitions.sendforapproval;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SendForApprovalTest extends BaseTest {

    @Epic("Requisitions")
    @Feature("Requisition Send For Approval")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Requester Can Send The Requisition For Approval")
    @Test(description = "Requisition Send For Approval Test")
    @Parameters({"type","purchaseType"})
    public void sendForApproval(String type, String purchaseType) {
        try {
            int status = iPrSendForApproval.sendForApproval(type, purchaseType);
            Assert.assertEquals(200, status, "Requisition Send For Approval was not successful");
        } catch (Exception exception) {
            logger.error("Exception in Send For Approval Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Requisition Edit Test Function: " + exception.getMessage());
        }
    }
}