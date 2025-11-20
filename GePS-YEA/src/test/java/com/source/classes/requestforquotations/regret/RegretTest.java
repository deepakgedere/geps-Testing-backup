package com.source.classes.requestforquotations.regret;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class RegretTest extends BaseTest {

    @Epic("Quotation")
    @Feature("Quotation Regret")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Vendor Can Able To Regret The Quotation")
    @Test(description = "Quotation Regret Test")
    @Parameters({"type"})
    public void regret(String type){
        try {
            int status = iQuoRegret.regret(type);
            Assert.assertEquals(200, status, "Requisition Approve was not successful");
        } catch (Exception exception) {
            logger.error("Exception in Quotation Regret Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Quotation Regret Test Function: " + exception.getMessage());
        }
    }
}