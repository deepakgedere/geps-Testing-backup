package com.classes.bms.purchaseOrders.ackreject;

import com.base.BaseTest;
import com.util.PRTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BmsAckRejectTest extends BaseTest {
    @Epic("PO Reject")
    @Feature("PO Reject Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify The User Can Reject the PO")
    @Test(dataProvider = "trnData", dataProviderClass = PRTrnUtil.class, description = "PO Reject Test")
    public void poAckRejectTest(String trnId) {
        try {
            iBmsAckReject.reject(trnId);
        } catch (Exception exception) {
            logger.error("Exception in PO Reject Test Function: {}", exception.getMessage());
            Assert.fail("Exception in PO Reject Test Function: " + exception.getMessage());
        }
    }
}
