package com.source.classes.invoices.woinvoice.sendforapproval;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WoInvSendForApprovalTest extends BaseTest {

    @Epic("Work Order Send For Approval")
    @Feature("Work Order Invoice Send For Approval")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Verifier Can Able To Send The Work Order Invoice For Approval")
    @Test(description = "Work Order Invoice Send For Approval Test")
    public void sendForApproval(){
        try {
            int status = iWoInvSendForApproval.sendForApproval();
            Assert.assertEquals(status, 200, "WO Invoice Send For Approval was not Successful");
        }  catch (Exception exception) {
            logger.error("Exception in WO Invoice Send For Approval Test function: {}", exception.getMessage());
            Assert.fail("Exception in WO Invoice Send For Approval Test Function: " + exception.getMessage());

        }
    }
}