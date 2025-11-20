package com.classes.bms.purchaseOrders.approve;

import com.base.BaseTest;
import com.util.PRTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BmsPOApproveTest extends BaseTest {
    @Epic("PO Approve")
    @Feature("PO Approve Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify The User Can Approve the PO")
    @Test(dataProvider = "trnData", dataProviderClass = PRTrnUtil.class, description = "PO Approve Test")
    public void poApproveTest(String trnId) {
        try {
            iBmsPoApprove.poApproverLogin();
            iBmsPoApprove.poDetails(trnId);
            iBmsPoApprove.approve();
        } catch (Exception exception) {
            logger.error("Exception in PO Approve Test Function: {}", exception.getMessage());
            Assert.fail("Exception in PO Approve Test Function: " + exception.getMessage());
        }
    }
}
