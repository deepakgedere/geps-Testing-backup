package com.classes.bms.requisitions.selectVendor;

import com.base.BaseTest;
import com.util.PRTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BmsSelectVendorTest extends BaseTest {
    @Epic("Select Vendor")
    @Feature("Requisition Select Vendor Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify The User Can Select Vendor In BMS Requisition")
    @Test(dataProvider = "trnData", dataProviderClass = PRTrnUtil.class, description = "Requisition Select Vendor Test")
    public void selectVnedorTest(String trnId) {
        try {
            iBmsSelectVendor.requesterLoginPRDetails(trnId);
            iBmsSelectVendor.selectVendor();
        } catch (Exception exception) {
            logger.error("Exception in BMS Requisition Select Vendor Test Function: {}", exception.getMessage());
            Assert.fail("Exception in BMS Requisition Select Vendor Test Function: " + exception.getMessage());
        }
    }
}
