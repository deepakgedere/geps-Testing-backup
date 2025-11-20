package com.source.classes.workorders.edit;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WoEditTest extends BaseTest {

    @Epic("Work Orders")
    @Feature("Work Orders Edit")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Assigned User Can Able To Edit The Work Order")
    @Test(description = "Work Order Edit Test")
    public void edit() {
        try {
            int status = iWoEdit.edit();
            Assert.assertEquals(200, status, "Work Order Edit was not Successful");
        } catch (Exception exception) {
            logger.error("Exception in Work Order Edit Test function: {}", exception.getMessage());
            Assert.fail("Exception in WO Edit Test Function: " + exception.getMessage());
        }
    }
}