package com.classes.bms.requisitions.create;

import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BmsCreateTest extends BaseTest {

    @Epic("Requisition Create")
    @Feature("Requisition Create Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify The User Can Create BMS Requisition")
    @Test(description = "Requisition Create Test")
    public void requisitionCreateTest() {
        try {
            iBmsPrCreate.requesterLogin();
            iBmsPrCreate.createButton();
            iBmsPrCreate.location();
            iBmsPrCreate.itemDetails();
            iBmsPrCreate.costCodeDetails();
            iBmsPrCreate.approvalDetails();
            iBmsPrCreate.savePRNumber();
            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in BMS Requisition Create Test Function: {}", exception.getMessage());
            Assert.fail("Exception in BMS Requisition Create Test Function: " + exception.getMessage());
        }
    }
}
