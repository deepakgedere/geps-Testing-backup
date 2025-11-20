package com.source.classes.requisitions.approve;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ApproveTest extends BaseTest {

    @Epic("Requisitions")
    @Feature("Requisition Approve")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Approvers Can Approve The Requisition")
    @Test(description = "Requisition Approve Test")
    @Parameters({"type","purchaseType"})
    public void approve(String type, String purchaseType){
        try {
            int status = iPrApprove.approve(type, purchaseType);
            if(status != 1){
                Assert.assertEquals(200, status, "Requisition Approve was not successful");
            }
        } catch (Exception exception) {
            logger.error("Exception in Requisition Approve Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Requisition Edit Test Function: " + exception.getMessage());
        }
    }
}