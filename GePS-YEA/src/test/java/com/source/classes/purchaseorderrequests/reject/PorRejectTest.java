package com.source.classes.purchaseorderrequests.reject;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PorRejectTest extends BaseTest {

    @Epic("Purchase Order Requests")
    @Feature("Purchase Order Request Reject")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Approvers Can Able To Reject The Purchase Order Request")
    @Test(description = "Purchase Order Request Reject Test")
    @Parameters({"type", "purchaseType"})
    public void reject(String type, String purchaseType) {
        try {
            int status = iPorReject.porReject(type, purchaseType);
            Assert.assertEquals(200, status, "POR Reject was not Successful");
            iPorEdit.porEdit(type, purchaseType);
        } catch (Exception exception) {
            logger.error("Exception in POR Reject Test Function: {}", exception.getMessage());
            Assert.fail("Exception in POR Reject Test Function: " + exception.getMessage());
        }
    }
}