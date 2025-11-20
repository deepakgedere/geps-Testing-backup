package com.source.classes.invoices.poinvoice.hold;
import com.base.BaseTest;
import com.util.PoInvTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PoInvHoldTest extends BaseTest {

    @Epic("Purchase Order Invoice")
    @Feature("Purchase Order Invoice Hold")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Verifier Can Able To Hold The Purchase Order Invoice")
    @Test(dataProvider = "invoiceData", dataProviderClass = PoInvTrnUtil.class, description = "Purchase Order Invoice Hold Test")
    public void hold(String referenceId, String transactionId, String uid){
        try {
            int status = iInvHold.hold(referenceId, transactionId, uid);
            Assert.assertEquals(200, status, "Invoice Hold was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in PO Invoice Hold Test function: {}", exception.getMessage());
            Assert.fail("Exception in PO Invoice Hold Test Function: " + exception.getMessage());
        }
    }
}