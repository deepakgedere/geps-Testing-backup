package com.classes.bms.purchaseOrders.create;
import com.base.BaseTest;
import com.util.PRTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BmsPoCreateTest extends BaseTest {

    @Epic("PO Create")
    @Feature("PO Create Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify The User Can Create PO")
    @Test(dataProvider = "trnData", dataProviderClass = PRTrnUtil.class, description = "PO Create Test")
    public void poCreateTest(String trnId) {
        try {
            iBmsPoCreate.requesterLogin();
            iBmsPoCreate.createPO(trnId);
        } catch (Exception exception) {
            logger.error("Exception in PO Create Test Function: {}", exception.getMessage());
            Assert.fail("Exception in PO Create Test Function: " + exception.getMessage());
        }
    }
}