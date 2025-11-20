package com.source.classes.purchaseorderrequests.edit;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PorEditTest extends BaseTest {

    @Epic("Purchase Order Requests")
    @Feature("Purchase Order Request Edit")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Buyer Can Able To Edit The Purchase Order Request")
    @Test(description = "Purchase Order Request Edit Test")
    @Parameters({"type", "purchaseType"})
    public void edit(String type, String purchaseType) {
        try {
            int status = iPorEdit.porEdit(type, purchaseType);
            Assert.assertEquals(200, status, "POR Edit was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in POR Edit Test Function: {}", exception.getMessage());
            Assert.fail("Exception in POR Edit Test Function: " + exception.getMessage());
        }
    }
}