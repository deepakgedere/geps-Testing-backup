package com.source.classes.requestforquotations.commercialevaluation;
import com.base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CommercialEvaluationTest extends BaseTest {

    @Epic("Commercial Evaluation")
    @Feature("Commercial Evaluation Create")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Verify Buyer Can Able To Create The Commercial Evaluation")
    @Test(description = "Commercial Evaluation Create Test")
    @Parameters({"type"})
    public void ceCreate(String type){
        try {
            int status = iCeCreate.commercialEvaluationButton(type);
            Assert.assertEquals(200, status, "Commercial Evaluation Create was not successful");
        } catch (Exception exception) {
            logger.error("Exception in Commercial Evaluation Test Function: {}", exception.getMessage());
            Assert.fail("Exception in Commercial Evaluation Test Function: " + exception.getMessage());
        }
    }
}