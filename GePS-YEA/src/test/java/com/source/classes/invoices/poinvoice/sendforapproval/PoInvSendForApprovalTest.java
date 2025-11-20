package com.source.classes.invoices.poinvoice.sendforapproval;
import com.base.BaseTest;
import com.util.PoInvTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PoInvSendForApprovalTest extends BaseTest {

    @Epic("Purchase Order Invoice")
    @Feature("Purchase Order Invoice Send For Approval")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Verifier Can Able To Send The Purchase Order Invoice For Approval")
    @Test(dataProvider = "invoiceData", dataProviderClass = PoInvTrnUtil.class, description = "Purchase Order Invoice Send For Approval Test")
    public void sendForApproval(String referenceId, String transactionId, String uid){
        try {
            int status = iInvSendForApproval.sendForApproval(referenceId, transactionId, uid);
            Assert.assertEquals(200, status, "Invoice Send For Approval was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in PO Invoice Send For Approval Test function: {}", exception.getMessage());
            Assert.fail("Exception in PO Invoice Send For Approval Test Function: " + exception.getMessage());
        }
    }
}