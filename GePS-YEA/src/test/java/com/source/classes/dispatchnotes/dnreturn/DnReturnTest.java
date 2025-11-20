package com.source.classes.dispatchnotes.dnreturn;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DnReturnTest extends BaseTest {

    @Epic("Dispatch Notes")
    @Feature("Dispatch Notes Return")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Logistics Manager Can Return The Dispatch Notes")
    @Test(description = "Dispatch Notes Return Test")
    public void dnReturn(){
        try {
            int status = iDnReturn.dnReturn();
            Assert.assertEquals(200, status, "Dispatch Note Return was not Successful");
        }  catch (Exception exception) {
            logger.error("Exception in Dispatch Notes Return Test function: {}", exception.getMessage());
            Assert.fail("Exception in DN Return Test Function: " + exception.getMessage());
        }
    }
}