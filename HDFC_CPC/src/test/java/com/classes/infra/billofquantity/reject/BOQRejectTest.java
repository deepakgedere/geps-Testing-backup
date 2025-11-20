package com.classes.infra.billofquantity.reject;
import com.base.BaseTest;
import com.util.PRTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BOQRejectTest extends BaseTest {

    @Epic("Bill Of Quantity Reject")
    @Feature("Bill Of Quantity Reject Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify The User Can Reject The Bill Of Quantity")
    @Test(dataProvider = "trnData", dataProviderClass = PRTrnUtil.class, description = "Bill Of Quantity Reject Test")
    public void boqRejectTest(String trnId) {
        try {
            iBoqReject.rejectBoq(trnId);
        } catch (Exception exception) {
            logger.error("Exception in Bill Of Quantity Reject Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Bill Of Quantity Reject Test Function: " + exception.getMessage());
        }
    }
}