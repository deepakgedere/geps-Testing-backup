package com.source.classes.requisitions.create;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PrCreateTest extends BaseTest {

    @Epic("Pr Create")
    @Feature("Pr Create Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Description: Verify The User Can Able To Pr Create")
    @Test(description = "Pr Create Test")
    @Parameters({"type", "purchaseType"})
    public void PrCreate(String type, String purchaseType) {
        try {
            int statusCode = iPrType.get().processRequisitionType(type, purchaseType);
            Assert.assertEquals(statusCode, 200, "Pr Create was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in Pr Create Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Pr Create Test Function: " + exception.getMessage());
        }
    }
}