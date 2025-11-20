package com.source.classes.invoices.woinvoice.checklist;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WoInvChecklistRejectTest extends BaseTest {

    @Epic("Work Order Checklist Reject")
    @Feature("Work Order Invoice Checklist Reject")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Verifier Can Able To Reject The Work Order Invoice Checklist")
    @Test(description = "Work Order Invoice Checklist Reject Test")
    public void reject(){
        try {
            int status = iWoInvChecklistReject.reject();
            Assert.assertEquals(200, status,"WO Invoice Checklist Reject was not Successful");
        }  catch (Exception exception) {
            logger.error("Exception in WO Invoice CheckList Reject Test function: {}", exception.getMessage());
            Assert.fail("Exception in WO Invoice Checklist Reject Test Function: " + exception.getMessage());

        }
    }
}