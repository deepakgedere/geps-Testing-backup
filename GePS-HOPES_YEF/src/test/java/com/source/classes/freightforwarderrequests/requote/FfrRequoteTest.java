package com.source.classes.freightforwarderrequests.requote;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FfrRequoteTest extends BaseTest {

    @Epic("Freight Forwarder Requests")
    @Feature("Freight Forwarder Requests Re-Quote")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Vendor Can Re-Quote")
    @Test(description = "Freight Forwarder Re-Quote Test")
    public void requote(){
        try {
            int[] status = iFfrRequote.requote();
            Assert.assertEquals(200, status[0], "FFR Requote Request was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in Freight Forwarder Requests Requote Test function: {}", exception.getMessage());
            Assert.fail("Exception in FFR Requote Test Function: " + exception.getMessage());
        }
    }
}