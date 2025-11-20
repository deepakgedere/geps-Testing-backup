package com.classes.infra.requisitions.create;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateTest extends BaseTest {

    @Epic("Requisition Create")
    @Feature("Requisition Create Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify The User Can Create The Requisition")
    @Test(description = "Requisition Create Test")
    public void requisitionCreateTest() {
        try {
            iPrCreate.requesterLogin();
            iPrCreate.createButton();
            iPrCreate.location();
            iPrCreate.itemDetails();
            iPrCreate.costCodeDetails();
            iPrCreate.approvalDetails();
            iPrCreate.savePRNumber();
            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Requisition Create Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Requisition Create Test Function: " + exception.getMessage());
        }
    }
}