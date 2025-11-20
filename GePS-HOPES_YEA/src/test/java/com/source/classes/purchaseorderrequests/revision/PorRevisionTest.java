package com.source.classes.purchaseorderrequests.revision;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PorRevisionTest extends BaseTest {

    @Epic("Purchase Order Requests")
    @Feature("Purchase Order Request Revision")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Buyer Can Able To Revise The Purchase Order Request")
    @Test(description = "Purchase Order Request Revision Test")
    @Parameters({"type", "purchaseType"})
    public void porRevision(String type, String purchaseType) {
        try {
            int status = iPorRevision.porRevision(type, purchaseType);
            Assert.assertEquals(200, status, "POR Revision was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in POR Revision Test function: {}", exception.getMessage());
            Assert.fail("Exception in POR Revision Test Function: " + exception.getMessage());
        }
    }
}