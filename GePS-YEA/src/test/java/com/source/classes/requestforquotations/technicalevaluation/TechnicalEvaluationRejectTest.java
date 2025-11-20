package com.source.classes.requestforquotations.technicalevaluation;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TechnicalEvaluationRejectTest extends BaseTest {

    @Epic("Technical Evaluation")
    @Feature("Technical Evaluation Reject")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Approver Can Able To Reject The Technical Evaluation")
    @Test(description = "Technical Evaluation Reject Test")
    @Parameters({"type"})
    public void reject(String type){
        try {
            iTeReject.technicalEvaluationReject(type);
            int status =iTeCreate.technicalEvaluationCreate(type);
            Assert.assertEquals(200, status, "Requisition Approve was not successful");
        } catch (Exception exception) {
            logger.error("Exception in Technical Evaluation Reject Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Technical Evaluation Reject Test Function: " + exception.getMessage());
        }
    }
}