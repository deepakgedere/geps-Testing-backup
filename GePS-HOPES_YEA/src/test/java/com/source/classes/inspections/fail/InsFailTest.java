package com.source.classes.inspections.fail;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InsFailTest extends BaseTest {

    @Epic("Inspections")
    @Feature("Inspection Fail")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Assigned User Can Fail The Inspection")
    @Test(description = "Inspections Fail Test")
    public void fail(){
        try {
            int status = iInsFail.fail();
            Assert.assertEquals(200, status, "Inspection Fail was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in Inspection Fail Test function: {}", exception.getMessage());
            Assert.fail("Exception in Inspection Fail Test Function: " + exception.getMessage());
        }
    }
}