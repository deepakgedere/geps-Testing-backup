package com.source.classes.workorders.okforinvoice;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WoOkForInvoiceTest extends BaseTest {

    @Epic("Work Orders")
    @Feature("Work Orders Ok For Invoice")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Logistics Manager Can Able To Make Ok For Invoice")
    @Test(description = "Work Order Ok For Invoice")
    public void okForInvoice(){
        try {
            int status = iWoOkForInvoice.okForInvoice();
            Assert.assertEquals(200, status, "Work Order OK for Invoice was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in Work Order Ok For Invoice Test function: {}", exception.getMessage());
            Assert.fail("Exception in WO Ok For Invoice Test Function: " + exception.getMessage());
        }
    }
}
