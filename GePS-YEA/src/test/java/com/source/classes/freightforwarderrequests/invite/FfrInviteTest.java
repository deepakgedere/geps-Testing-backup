package com.source.classes.freightforwarderrequests.invite;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FfrInviteTest extends BaseTest {

    @Epic("Freight Forwarder Requests")
    @Feature("Freight Forwarder Requests Invite")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Logistics Manager Can Invite The Freight Forwarder")
    @Test(description = "Freight Forwarder Invite Test")
    public void invite(){
        try {
            int status = iFfrInvite.invite();
            Assert.assertEquals(200, status, "FFR Invite was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in Freight Forwarder Requests Invite Test function: {}", exception.getMessage());
            Assert.fail("Exception in FFR Invite Test Function: " + exception.getMessage());
        }
    }
}