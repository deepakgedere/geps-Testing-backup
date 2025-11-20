package com.classes.infra.purchaseorder.approve;

import com.base.BaseTest;
import com.util.PRTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class POApproveTest extends BaseTest {
    @Epic("PO Approve")
    @Feature("PO Approve Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify The User Can Approve the PO")
    @Test(dataProvider = "trnData", dataProviderClass = PRTrnUtil.class, description = "PO Approve Test")
    public void poApproveTest(String trnId) {
        try {
            iPoApprove.poApproverLogin();
            iPoApprove.poDetails(trnId);
            iPoApprove.approve();
        } catch (Exception exception) {
            logger.error("Exception in PO Approve Test Function: {}", exception.getMessage());
            Assert.fail("Exception in PO Approve Test Function: " + exception.getMessage());
        }
    }
}
