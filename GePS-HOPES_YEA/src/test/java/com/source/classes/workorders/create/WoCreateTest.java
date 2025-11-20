package com.source.classes.workorders.create;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WoCreateTest extends BaseTest {

    @Epic("Work Orders")
    @Feature("Work Orders Create")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Assigned User Can Able To Create The Work Order")
    @Test(description = "Work Order Create Test")
    public void create() {
        try {
            int status = iWoCreate.create();
            Assert.assertEquals(200, status, "Work Order Create was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in Work Order Create Test function: {}", exception.getMessage());
            Assert.fail("Exception in WO Create Test Function: " + exception.getMessage());
        }
    }
}