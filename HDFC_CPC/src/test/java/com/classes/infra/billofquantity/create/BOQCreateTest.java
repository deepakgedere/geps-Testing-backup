package com.classes.infra.billofquantity.create;
import com.base.BaseTest;
import com.util.PRTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BOQCreateTest extends BaseTest {

    @Epic("Bill Of Quantity Create")
    @Feature("Bill Of Quantity Create Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify The User Can Create The Bill Of Quantity")
    @Test(dataProvider = "trnData", dataProviderClass = PRTrnUtil.class, description = "Bill Of Quantity Create Test")
    public void boqCreateTest(String trnId) {
        try {
            iBoqCreate.CpcCoordinatorLogin();
            //pass false for 1st time creation
            iBoqCreate.createBoqButton(trnId, false);
            iBoqCreate.approvalDetailsBOQCreate();
        } catch (Exception exception) {
            logger.error("Exception in Bill Of Quantity Create Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Bill Of Quantity Create Test Function: " + exception.getMessage());
        }
    }
}