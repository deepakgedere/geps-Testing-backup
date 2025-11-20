package com.source.classes.requisitions.reject;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class RejectTest extends BaseTest {

    @Epic("Requisitions")
    @Feature("Requisition Reject")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Approvers Can Reject The Requisition")
    @Test(description = "Requisition Reject Test")
    @Parameters({"type", "purchaseType"})
    public void reject(String type, String purchaseType) {
        try {
            int status = iPrReject.reject(type, purchaseType);
            if(status != 1){
            iPrEdit.edit(type, purchaseType);
            iPrSendForApproval.sendForApproval(type, purchaseType);
            Assert.assertEquals(200, status, "Requisition Edit was not successful");
            }
        } catch (Exception exception) {
            logger.error("Exception in Requisition Reject Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Requisition Reject Test Function: " + exception.getMessage());
        }
    }
}