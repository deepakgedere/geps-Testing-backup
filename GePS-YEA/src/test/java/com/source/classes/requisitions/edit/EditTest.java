package com.source.classes.requisitions.edit;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class EditTest extends BaseTest {

    @Epic("Requisitions")
    @Feature("Requisition Edit")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Requester Can Edit The Requisition")
    @Test(description = "Requisition Edit Test")
    @Parameters({"type", "purchaseType"})
    public void edit(String type, String purchaseType){
        try {
            int status = iPrEdit.edit(type, purchaseType);
            Assert.assertEquals(200, status, "Requisition Edit was not successful");
        } catch (Exception exception) {
            logger.error("Exception in Requisition Edit Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Requisition Edit Test Function: " + exception.getMessage());
        }
    }
}