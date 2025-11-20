package com.source.classes.invoices.woinvoice.edit;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WoInvEditTest extends BaseTest {

    @Epic("Work Order Edit")
    @Feature("Work Order Invoice Edit")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Verifier Can Able To Edit The Work Order Invoice")
    @Test(description = "Work Order Invoice Edit Test")
    public void edit(){
        try {
            int status = iWoInvEdit.edit();
            Assert.assertEquals(status, 200, "Work Order Invoice Edit Test Failed");
        }  catch (Exception exception) {
            logger.error("Exception in WO Invoice Edit Test function: {}", exception.getMessage());
            Assert.fail("Exception in WO Invoice Edit Test Function: " + exception.getMessage());

        }
    }
}