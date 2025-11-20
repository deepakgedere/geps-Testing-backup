package com.source.classes.invoices.woinvoice.revert;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WoInvRevertTest extends BaseTest {

    @Epic("Work Order Revert")
    @Feature("Work Order Invoice Revert")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Verifier Can Able To Revert The Work Order Invoice")
    @Test(description = "Work Order Invoice Revert Test")
    public void revert() {
        try {
            int status = iWoInvRevert.revert();
            Assert.assertEquals(status, 200, "WO Invoice Revert was not Successful");
        }  catch (Exception exception) {
            logger.error("Exception in WO Invoice Revert Test function: {}", exception.getMessage());
            Assert.fail("Exception in WO Invoice Revert Test Function: " + exception.getMessage());

        }
    }
}