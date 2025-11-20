package com.classes.infra.requisitions.approve;
import com.base.BaseTest;
import com.util.PRTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApproveTest extends BaseTest {

    @Epic("Requisition Approve")
    @Feature("Requisition Approve Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify The User Can Approve The Requisition")
    @Test(dataProvider = "trnData", dataProviderClass = PRTrnUtil.class, description = "Requisition Approve Test")
    public void requisitionApproveTest(String trnId) {
        try {
            iPrApprove.approve(trnId);
        } catch (Exception exception) {
            logger.error("Exception in Requisition Approve Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Requisition Approve Test Function: " + exception.getMessage());
        }
    }
}