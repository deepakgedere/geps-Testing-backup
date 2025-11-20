package com.source.classes.requestforquotations.technicalevaluation;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TechnicalEvaluationCreateTest extends BaseTest {

    @Epic("Technical Evaluation")
    @Feature("Technical Evaluation Create")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Requester Can Able To Create The Technical Evaluation")
    @Test(description = "Technical Evaluation Create Test")
    @Parameters({"type"})
    public void TechnicalEvaluationCreate(String type){
        try {
            int status =iTeCreate.technicalEvaluationCreate(type);
            Assert.assertEquals(200, status, "Requisition Approve was not successful");
        } catch (Exception exception) {
            logger.error("Exception in Technical Evaluation Create Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Technical Evaluation Create Test Function: " + exception.getMessage());
        }
    }
}