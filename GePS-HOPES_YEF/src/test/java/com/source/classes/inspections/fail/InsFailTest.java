package com.source.classes.inspections.fail;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class InsFailTest extends BaseTest {

    @Epic("Inspections")
    @Feature("Inspection Fail")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Assigned User Can Fail The Inspection")
    @Test(description = "Inspections Fail Test")
    @Parameters({"type", "purchaseType"})
    public void fail(String type, String purchaseType){
        try {
            int status = iInsFail.fail(type, purchaseType);
            Assert.assertEquals(200, status, "Inspection Fail was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in Inspection Fail Test function: {}", exception.getMessage());
            Assert.fail("Exception in Inspection Fail Test Function: " + exception.getMessage());
        }
    }
}