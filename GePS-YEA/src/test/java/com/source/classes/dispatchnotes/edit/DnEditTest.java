package com.source.classes.dispatchnotes.edit;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DnEditTest extends BaseTest {

    @Epic("Dispatch Notes")
    @Feature("Dispatch Notes Edit")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Vendor Can Edit The Dispatch Notes")
    @Test(description = "Dispatch Notes Edit Test")
    public void edit(){
        try {
            int status = iDnEdit.edit();
            Assert.assertEquals(200, status, "Dispatch Note Edit was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in Dispatch Notes Edit Test function: {}", exception.getMessage());
            Assert.fail("Exception in DN Edit Test Function: " + exception.getMessage());
        }
    }
}