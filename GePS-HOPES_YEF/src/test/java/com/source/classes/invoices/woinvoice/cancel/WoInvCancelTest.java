package com.source.classes.invoices.woinvoice.cancel;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WoInvCancelTest extends BaseTest {

    @Epic("Work Order Cancel")
    @Feature("Work Order Invoice Cancel")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Verifier Can Able To Cancel The Work Order Invoice")
    @Test(description = "Work Order Invoice Cancel Test")
    public void cancel(){
        try {
            int status = iWoInvCancel.cancel();
            Assert.assertEquals(200, status, "WO Invoice Cancel was not Successful");
            iWoInvCreate.create();
            iWoInvCreate.invoiceCreate();
        }  catch (Exception exception) {
            logger.error("Exception in WO Invoice Cancel Test function: {}", exception.getMessage());
            Assert.fail("Exception in WO Invoice Cancel Test Function: " + exception.getMessage());

        }
    }
}