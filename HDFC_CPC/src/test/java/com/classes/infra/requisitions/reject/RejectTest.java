package com.classes.infra.requisitions.reject;
import com.base.BaseTest;
import com.util.PRTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RejectTest extends BaseTest {

    @Epic("Requisition Reject")
    @Feature("Requisition Reject Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify The User Can Reject The Requisition")
    @Test(dataProvider = "trnData", dataProviderClass = PRTrnUtil.class, description = "Requisition Reject Test")
    public void requisitionRejectTest(String data) {
        try {
            iPrReject.procurementManagerLogin();
            iPrReject.detailsPR(data);
            iPrReject.rejectPR();
        } catch (Exception exception) {
            logger.error("Exception in Requisition Reject Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Requisition Reject Test Function: " + exception.getMessage());
        }
    }
}