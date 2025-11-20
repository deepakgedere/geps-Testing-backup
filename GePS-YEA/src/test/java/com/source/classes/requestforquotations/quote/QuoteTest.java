package com.source.classes.requestforquotations.quote;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class QuoteTest extends BaseTest {

    @Epic("Quotation")
    @Feature("Quotation Submit")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Vendor Can Able To Submit The Quotation")
    @Test(description = "Quotation Submit Test")
    @Parameters({"type"})
    public void quote(String type) {
        try {
            iQuoSubmit.inviteRegisteredVendor(type);
            iQuoSubmit.vendorLogin(type);
            iQuoSubmit.liquidatedDamages();
            iQuoSubmit.rohsCompliance();
            iQuoSubmit.warrantyRequirements();
            iQuoSubmit.quotationItems();
            iQuoSubmit.gst();
            iQuoSubmit.quotationAttachments();
            int status = iQuoSubmit.quotationSubmitButton(type);
            Assert.assertEquals(200, status, "Requisition Approve was not successful");
        } catch (Exception exception) {
            logger.error("Exception in Quotation Submit Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Quotation Submit Test Function: " + exception.getMessage());
        }
    }
}