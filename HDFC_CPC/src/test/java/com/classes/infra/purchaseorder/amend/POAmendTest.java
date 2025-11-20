package com.classes.infra.purchaseorder.amend;

import com.base.BaseTest;
import com.util.PRTrnUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.utils.ClearVendorEmails.clearVendorEmails;

public class POAmendTest extends BaseTest {
    private static boolean isFirstRun = true;

    @Epic("PO Ammend")
    @Feature("PO Ammend Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify The User Can Ammend the PO")
    @Test(dataProvider = "trnData", dataProviderClass = PRTrnUtil.class, description = "PO Ammend Test")
    public void poAmmendTest(String trnId) {
        try {
            if(isFirstRun){
                clearVendorEmails(jsonNode);
                isFirstRun = false;
            }
            iPoAmmend.poAmmendLogin();
            iPoAmmend.ammendPo(trnId);
        } catch (Exception exception) {
            logger.error("Exception in PO Ammend Test Function: {}", exception.getMessage());
            Assert.fail("Exception in PO Ammend Test Function: " + exception.getMessage());
        }
    }
}
