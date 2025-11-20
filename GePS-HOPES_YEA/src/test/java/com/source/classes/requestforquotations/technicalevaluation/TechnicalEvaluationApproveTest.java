package com.source.classes.requestforquotations.technicalevaluation;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TechnicalEvaluationApproveTest extends BaseTest {

    @Epic("Technical Evaluation")
    @Feature("Technical Evaluation Approve")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Approver Can Able To Approve The Technical Evaluation")
    @Test(description = "Technical Evaluation Approve Test")
    @Parameters({"type"})
    public void TechnicalEvaluationApprove(String type){
        try {
            int status = iTeApprove.technicalEvaluationApprove(type);
            Assert.assertEquals(200, status, "Requisition Approve was not successful");
        } catch (Exception exception) {
            logger.error("Exception in Technical Evaluation Approve Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Technical Evaluation Approve Test Function: " + exception.getMessage());
        }
    }
}