package com.source.classes.invoices.woinvoice.reject;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WoInvRejectTest extends BaseTest {

    @Epic("Work Order Reject")
    @Feature("Work Order Invoice Reject")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Approver Can Able To Reject The Work Order Invoice")
    @Test(description = "Work Order Invoice Reject Test")
    public void reject() {
        try {
            int status = iWoInvReject.reject();
            Assert.assertEquals(status, 200, "WO Invoice Reject was not Successful");
        }  catch (Exception exception) {
            logger.error("Exception in WO Invoice Reject Test function: {}", exception.getMessage());
            Assert.fail("Exception in WO Invoice Reject Test Function: " + exception.getMessage());

        }
    }
}