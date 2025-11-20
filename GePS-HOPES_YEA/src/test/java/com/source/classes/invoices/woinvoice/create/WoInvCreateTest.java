package com.source.classes.invoices.woinvoice.create;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WoInvCreateTest extends BaseTest {

    @Epic("Work Order Create")
    @Feature("Work Order Invoice Create")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Verifier Can Able To Create The Work Order Invoice")
    @Test(description = "Work Order Invoice Create Test")
    public void create(){
        try {
            iWoInvCreate.create();
            double finalGSTPercentage = iWoInvCreate.gst();
            iWoInvCreate.ifSgdEnable(finalGSTPercentage);
            int status = iWoInvCreate.invoiceCreate();
            Assert.assertEquals(200, status, "WO Invoice Create was not Successful");
        }  catch (Exception exception) {
            logger.error("Exception in WO Invoice Create Test function: {}", exception.getMessage());
            Assert.fail("Exception in WO Invoice Create Test Function: " + exception.getMessage());

        }
    }
}