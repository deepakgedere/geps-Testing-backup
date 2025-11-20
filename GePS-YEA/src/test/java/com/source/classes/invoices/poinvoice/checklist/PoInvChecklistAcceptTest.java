package com.source.classes.invoices.poinvoice.checklist;
import com.base.BaseTest;
import com.util.PoInvTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PoInvChecklistAcceptTest extends BaseTest {

    @Epic("Purchase Order Invoice")
    @Feature("Purchase Order Invoice CheckList Accept")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Verifier Can Able To Accept The Purchase Order Invoice Checklist")
    @Test(dataProvider = "invoiceData", dataProviderClass = PoInvTrnUtil.class, description = "Purchase Order Invoice CheckList Accept Test")
    public void accept(String referenceId, String transactionId, String uid){
        try {
            int status = iInvChecklistAccept.accept(referenceId, transactionId, uid);
            Assert.assertEquals(200, status, "Invoice Checklist Accept was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in PO Invoice CheckList Accept Test function: {}", exception.getMessage());
            Assert.fail("Exception in PO Invoice Checklist Accept Test Function: " + exception.getMessage());

        }
    }
}