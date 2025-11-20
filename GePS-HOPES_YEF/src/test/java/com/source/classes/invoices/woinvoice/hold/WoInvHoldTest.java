package com.source.classes.invoices.woinvoice.hold;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WoInvHoldTest extends BaseTest {

    @Epic("Work Order Hold")
    @Feature("Work Order Invoice Hold")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Verifier Can Able To Hold The Work Order Invoice")
    @Test(description = "Work Order Invoice Hold Test")
    public void hold(){
        try {
            int status = iWoInvHold.hold();
            Assert.assertEquals(status, 200, "WO Invoice Hold was not Successful");
        }  catch (Exception exception) {
            logger.error("Exception in WO Invoice Hold Test function: {}", exception.getMessage());
            Assert.fail("Exception in WO Invoice Hold Test Function: " + exception.getMessage());

        }
    }
}