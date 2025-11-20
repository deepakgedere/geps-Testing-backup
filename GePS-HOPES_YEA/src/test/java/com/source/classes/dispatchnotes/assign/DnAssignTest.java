package com.source.classes.dispatchnotes.assign;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DnAssignTest extends BaseTest {

    @Epic("Dispatch Notes")
    @Feature("Dispatch Notes Assign")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Logistics Manager Can Assign The Dispatch Notes")
    @Test(description = "Dispatch Notes Assign Test")
    public void assign(){
        try {
            int status = iDnAssign.assign();
            Assert.assertEquals(200, status, "Dispatch Note Assign was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in Dispatch Notes Assign Test function: {}", exception.getMessage());
            Assert.fail("Exception in DN Assign Test Function: " + exception.getMessage());
        }
    }
}