package com.source.classes.invoices.poinvoice.cancel;
import com.base.BaseTest;
import com.util.PoInvTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PoInvCancelTest extends BaseTest {

    @Epic("Purchase Order Invoice")
    @Feature("Purchase Order Invoice Cancel")
    @Severity(SeverityLevel.NORMAL)
    @Parameters({"type", "purchaseType"})
    @Description("Test Description: Verify Verifier Can Able To Cancel The Purchase Order Invoice")
    @Test(dataProvider = "invoiceData", dataProviderClass = PoInvTrnUtil.class, description = "Purchase Order Invoice Cancel Test")
    public void cancel(String referenceId, String transactionId, String uid, String type){
        try {
            int status = iInvCancel.cancel(referenceId, transactionId, uid, type);
            Assert.assertEquals(200, status, "Invoice Cancel was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in PO Invoice Cancel Test function: {}", exception.getMessage());
            Assert.fail("Exception in PO Invoice Cancel Test Function: " + exception.getMessage());
        }
    }

    @Parameters({"type", "purchaseType"})
    @Test(dependsOnMethods = {"cancel"})
    public void createAfterCancel(String type, String purchaseType) {
        try {
            int status = iInvCreate.invoiceTypeHandler(type, purchaseType);
            Assert.assertEquals(200, status, "Invoice Create After Cancel was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in PO Invoice Create after Cancel Test function: {}", exception.getMessage());
            Assert.fail("Exception in PO Invoice Create after Cancel Test Function: " + exception.getMessage());
        }
    }
}