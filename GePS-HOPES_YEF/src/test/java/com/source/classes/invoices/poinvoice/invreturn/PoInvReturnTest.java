package com.source.classes.invoices.poinvoice.invreturn;
import com.base.BaseTest;
import com.util.PoInvTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PoInvReturnTest extends BaseTest {

    @Epic("Purchase Order Invoice")
    @Feature("Purchase Order Invoice Return")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Verifier Can Able To Return The Purchase Order Invoice")
    @Test(dataProvider = "invoiceData", dataProviderClass = PoInvTrnUtil.class, description = "Purchase Order Invoice Return Test")
    public void returnMethod(String referenceId, String transactionId, String uid, String type){
        try {
            int status = iInvReturn.invReturn(referenceId, transactionId, uid, type);
            Assert.assertEquals(200, status, "Invoice Return was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in PO Invoice Return Test function: {}", exception.getMessage());
            Assert.fail("Exception in PO Invoice Return Test Function: " + exception.getMessage());
        }
    }
}