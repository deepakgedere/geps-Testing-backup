package com.source.classes.purchaseorders;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PoSendForVendorTest extends BaseTest {

    @Epic("Purchase Orders")
    @Feature("Purchase Order Send To Vendor")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Buyer Can Able To Send The Purchase Order To Vendor")
    @Test(description = "Purchase Order Send To Vendor Test")
    @Parameters({"type", "purchaseType"})
    public void sendForVendor(String type, String purchaseType) {
        try {
            int status = iPoSendForVendor.sendPoForVendor(type, purchaseType);
            Assert.assertEquals(200, status, "PO Send to Vendor was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in Send For Vendor Test function: {}", exception.getMessage());
            Assert.fail("Exception in PO Send For Vendor Test Function: " + exception.getMessage());
        }
    }
}