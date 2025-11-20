package com.source.classes.freightforwarderrequests.quote;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FfrQuoteTest extends BaseTest {

    @Epic("Freight Forwarder Requests")
    @Feature("Freight Forwarder Requests Quote")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Vendor Can Quote")
    @Test(description = "Freight Forwarder Quote Test")
    public void quote(){
        try {
            int status = iFfrQuote.quote();
            Assert.assertEquals(200, status, "FFR Quote was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in Freight Forwarder Requests Quote Test function: {}", exception.getMessage());
            Assert.fail("Exception in FFR Quote Test Function: " + exception.getMessage());
        }
    }
}