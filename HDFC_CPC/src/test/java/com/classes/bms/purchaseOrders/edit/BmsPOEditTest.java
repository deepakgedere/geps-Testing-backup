package com.classes.bms.purchaseOrders.edit;

import com.base.BaseTest;
import com.util.PRTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BmsPOEditTest extends BaseTest {
    @Epic("PO Edit")
    @Feature("PO Edit Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify The User Can Edit the PO")
    @Test(dataProvider = "trnData", dataProviderClass = PRTrnUtil.class, description = "PO Edit Test")
    public void poEditTest(String trnId) {
        try {
            iBmsPoEdit.poEditorLogin();
            iBmsPoEdit.poEdit(trnId);
        } catch (Exception exception) {
            logger.error("Exception in PO Edit Test Function: {}", exception.getMessage());
            Assert.fail("Exception in PO Edit Test Function: " + exception.getMessage());
        }
    }
}