package com.classes.infra.requisitions.edit;
import com.base.BaseTest;
import com.util.PRTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EditTest extends BaseTest {

    @Epic("Requisition Edit")
    @Feature("Requisition Edit Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify The User Can Edit The Requisition")
    @Test(dataProvider = "trnData", dataProviderClass = PRTrnUtil.class, description = "Requisition Edit Test")
    public void requisitionEditTest(String trnId) {
        try {
            iPrEdit.requesterLoginEditPR();
            iPrEdit.edit(trnId);
            iPrEdit.editPageNextButton();
        } catch (Exception exception) {
            logger.error("Exception in Requisition Edit Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Requisition Edit Test Function: " + exception.getMessage());
        }
    }
}