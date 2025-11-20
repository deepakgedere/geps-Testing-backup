package com.source.classes.invoices.poinvoice.approve;
import com.base.BaseTest;
import com.util.PoInvTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PoInvApprovalTest extends BaseTest {

    @Epic("Purchase Order Invoice")
    @Feature("Purchase Order Invoice Approve")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Approver Can Able To Approve The Purchase Order Invoice")
    @Test(dataProvider = "invoiceData", dataProviderClass = PoInvTrnUtil.class, description = "Purchase Order Invoice Approve Test")
    public void approval(String referenceId, String transactionId, String uid, String type){
        try {
            int status = iInvApproval.approval(referenceId, transactionId, uid, type);
            Assert.assertEquals(200, status, "Invoice Approve was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in PO Invoice Approval Test function: {}", exception.getMessage());
            Assert.fail("Exception in PO Invoice Approval Test Function: " + exception.getMessage());
        }
    }
}