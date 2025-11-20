package com.source.classes.invoices.woinvoice.invreturn;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WoInvReturnTest extends BaseTest {

    @Epic("Work Order Return")
    @Feature("Work Order Invoice Return")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Verifier Can Able To Return The Work Order Invoice")
    @Test(description = "Work Order Invoice Return Test")
    public void returnMethod(){
        try {
            int status = iWoInvReturn.returnMethod();
            Assert.assertEquals(200, status,"WO Invoice Return was not Successful");
        }  catch (Exception exception) {
            logger.error("Exception in WO Invoice Return Test function: {}", exception.getMessage());
            Assert.fail("Exception in WO Invoice Return Test Function: " + exception.getMessage());

        }
    }
}