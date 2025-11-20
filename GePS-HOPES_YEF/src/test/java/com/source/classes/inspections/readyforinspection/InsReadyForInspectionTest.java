package com.source.classes.inspections.readyforinspection;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class InsReadyForInspectionTest extends BaseTest {

    @Epic("Inspections")
    @Feature("Inspection Ready For Inspection")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Vendor Can Make The Ready For Inspection")
    @Test(description = "Inspections Ready For Inspection Test")
    @Parameters({"type", "purchaseType"})
    public void readyForInspection(String type, String purchaseType){
        try {
            int status = iInsReadyForInspection.readyForInspection(type, purchaseType);
            Assert.assertEquals(200, status, "Send For Inspection was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in Ready for Inspection Test function: {}", exception.getMessage());
            Assert.fail("Exception in Ready For Inspection Test Function: " + exception.getMessage());
        }
    }
}