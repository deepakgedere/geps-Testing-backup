package com.source.classes.requestforquotations.technicalevaluation;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.requestforquotations.ITeApprove;
import com.source.interfaces.requestforquotations.ITeCreate;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import static com.constants.requestforquotations.LTeApprove.*;
import static com.utils.GetTitleUtil.getRFQTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class TechnicalEvaluationApprove implements ITeApprove {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    ITeCreate iTeCreate;
    private String appUrl;

    private TechnicalEvaluationApprove(){
    }

//TODO Constructor
    public TechnicalEvaluationApprove(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, ITeCreate iTeCreate){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.iTeCreate = iTeCreate;
        this.logger = LoggerUtil.getLogger(TechnicalEvaluationApprove.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int technicalEvaluationApprove(String type) {
        int status = 0;
        try {
            String requesterMailId = jsonNode.get("mailIds").get("requesterEmail").asText();
            iLogin.performLogin(requesterMailId);

            Locator myApprovalsButtonLocator = page.locator(MY_APPROVALS);
            locatorVisibleHandler(myApprovalsButtonLocator);
            myApprovalsButtonLocator.click();

            String title = getRFQTransactionTitle(type);
            Locator titleLocator = page.locator(getTitle(title));
            locatorVisibleHandler(titleLocator);
            titleLocator.first().click();

            Locator approveButtonLocator = page.locator(APPROVE_BUTTON);
            locatorVisibleHandler(approveButtonLocator);
            approveButtonLocator.click();

            Locator remarksInputLocator = page.locator(REMARKS_INPUT_LOCATOR);
            locatorVisibleHandler(remarksInputLocator);
            remarksInputLocator.fill("TE Approved");

            Locator acceptLocator = page.locator(YES);
            locatorVisibleHandler(acceptLocator);

            Response submitResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/TechnicalEvaluations/") && response.status() == 200,
                    acceptLocator::click
            );
            status = submitResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Technical Evaluation Approve", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Technical Evaluation Approve Function: {}", exception.getMessage());
        }
        return status;
    }
}