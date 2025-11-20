package com.classes.bms.requisitions.edit;

import com.base.BaseTest;
import com.util.PRTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BmsEditTest extends BaseTest {
    @Epic("Requisition Edit")
    @Feature("Requisition Edit Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify The User Can Edit BMS Requisition")
    @Test(dataProvider = "trnData", dataProviderClass = PRTrnUtil.class, description = "Requisition Edit Test")
    public void requisitionEditTest(String trnId) {
        try {
            iBmsPrEdit.requesterLoginEditPR();
            iBmsPrEdit.edit(trnId);
            iBmsPrEdit.editPageNextButton();
        } catch (Exception exception) {
            logger.error("Exception in BMS Requisition Edit Test Function: {}", exception.getMessage());
            Assert.fail("Exception in BMS Requisition Edit Test Function: " + exception.getMessage());
        }
    }
}
