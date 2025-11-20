package com.classes.infra.purchaseorder.accept;

import com.base.BaseTest;
import com.util.PRTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class POAcceptTest extends BaseTest {
    @Epic("PO Accept")
    @Feature("PO Accept Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify The User Can Accept the PO")
    @Test(dataProvider = "trnData", dataProviderClass = PRTrnUtil.class, description = "PO Accept Test")
    public void poAcceptTest(String trnId) {
        try {
            iPoAccept.accept(trnId);
        } catch (Exception exception) {
            logger.error("Exception in PO Accept Test Function: {}", exception.getMessage());
            Assert.fail("Exception in PO Accept Test Function: " + exception.getMessage());
        }
    }
}
