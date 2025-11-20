package com.source.classes.requisitions.create;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CreateTest extends BaseTest {

    @Epic("Requisitions")
    @Feature("Requisition Create")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Requester Can Create The Requisition")
    @Test(description = "Requisition Create Test")
    @Parameters({"type", "purchaseType"})
    public void create(String type, String purchaseType) {
        try {
            int status = iPrType.processRequisitionType(type, purchaseType);
            Assert.assertEquals(200, status, "Requisition Create was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in Requisition Create Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Requisition Create Test Function: " + exception.getMessage());
        }
    }
}