package com.source.classes.purchaseorderrequests.create;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PorCreateTest extends BaseTest {

    @Epic("Purchase Order Requests")
    @Feature("Purchase Order Request Create")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Buyer Can Able To Create The Purchase Order Request")
    @Test(description = "Purchase Order Request Create Test")
    @Parameters({"type", "purchaseType"})
    public void create(String type, String purchaseType){
        try {
            int status = iPorCreate.porCreate(type, purchaseType);
            Assert.assertEquals(200, status, "POR Create was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in POR Create Test Function: {}", exception.getMessage());
            Assert.fail("Exception in POR Create Test Function: " + exception.getMessage());
        }
    }
}