package com.source.classes.requestforquotations.edit;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class RfqEditTest extends BaseTest {

    @Epic("Request For Quotation")
    @Feature("Request For Quotation Edit")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Buyer Can Able To Edit The Request For Quotation")
    @Test(description = "Request For Quotation Edit Test")
    @Parameters({"type"})
    public void edit(String type) {
        try {
            int status = iRfqEdit.rfqEditMethod(type);
            Assert.assertEquals(200, status, "Requisition Approve was not successful");
        } catch (Exception exception) {
            logger.error("Exception in RFQ Edit Test Function: {}", exception.getMessage());
            Assert.fail("Exception in RFQ Edit Test Function: " + exception.getMessage());
        }
    }
}