package com.classes.infra.billofquantity.approve;
import com.base.BaseTest;
import com.util.PRTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BOQApproveTest extends BaseTest {

    @Epic("Bill Of Quantity Approve")
    @Feature("Bill Of Quantity Approve Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify The User Can Approve The Bill Of Quantity")
    @Test(dataProvider = "trnData", dataProviderClass = PRTrnUtil.class, description = "Bill Of Quantity Approve Test")
    public void boqApproveTest(String trnId) {
        try {
            iBoqApprove.boqApprove(trnId);
        } catch (Exception exception) {
            logger.error("Exception in Bill Of Quantity Approve Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Bill Of Quantity Approve Test Function: " + exception.getMessage());
        }
    }
}