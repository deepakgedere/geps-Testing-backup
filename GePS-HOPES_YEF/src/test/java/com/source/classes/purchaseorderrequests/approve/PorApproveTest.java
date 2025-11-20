package com.source.classes.purchaseorderrequests.approve;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PorApproveTest extends BaseTest {

    @Epic("Purchase Order Requests")
    @Feature("Purchase Order Request Approve")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Approvers Can Able To Approve The Purchase Order Request")
    @Test(description = "Purchase Order Request Approve Test")
    @Parameters({"type", "purchaseType"})
    public void approvalAndApprove(String type, String purchaseType){
        try {
            int status = iPorApprove.approve(type, purchaseType);
            Assert.assertEquals(200, status, "POR Approve was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in POR Approve Test Function: {}", exception.getMessage());
            Assert.fail("Exception in POR Approve Test Function: " + exception.getMessage());
        }
    }
}