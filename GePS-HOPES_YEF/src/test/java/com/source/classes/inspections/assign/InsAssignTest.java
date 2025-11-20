package com.source.classes.inspections.assign;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InsAssignTest extends BaseTest {

    @Epic("Inspections")
    @Feature("Inspection Assign")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Requester Can Assign The User")
    @Test(description = "Inspections Assign Test")
    public void assign(){
        try {
            int status = iInsAssign.assign();
            Assert.assertEquals(200, status, "Assign Inspector was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in Inspection Assign Test function: {}", exception.getMessage());
            Assert.fail("Exception in Inspection Assign Test Function: " + exception.getMessage());
        }
    }
}