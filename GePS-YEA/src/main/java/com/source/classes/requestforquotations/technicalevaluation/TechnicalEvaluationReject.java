package com.source.classes.requestforquotations.technicalevaluation;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.requestforquotations.ITeCreate;
import com.source.interfaces.requestforquotations.ITeReject;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.requestforquotations.LTeReject.*;
import static com.utils.GetTitleUtil.getRFQTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class TechnicalEvaluationReject implements ITeReject {

    Logger logger;
    ILogin iLogin;
    JsonNode jsonNode;
    Page page;
    ILogout iLogout;
    ITeCreate iTeCreate;
    private String appUrl;

    private TechnicalEvaluationReject(){
    }

//TODO Constructor
    public TechnicalEvaluationReject(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, ITeCreate iTeCreate){
        this.iLogin = iLogin;
        this.page = page;
        this.jsonNode = jsonNode;
        this.iLogout = iLogout;
        this.iTeCreate = iTeCreate;
        this.logger = LoggerUtil.getLogger(TechnicalEvaluationReject.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int technicalEvaluationReject(String type){
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

            Locator rejectButtonLocator = page.locator(REJECT_BUTTON);
            locatorVisibleHandler(rejectButtonLocator);
            rejectButtonLocator.click();

            Locator remarksInputLocator = page.locator(REMARKS_INPUT_LOCATOR);
            locatorVisibleHandler(remarksInputLocator);
            remarksInputLocator.fill("TE Rejected");

            Locator acceptLocator = page.locator(YES);
            locatorVisibleHandler(acceptLocator);

            Response submitResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/TechnicalEvaluations/") && response.status() == 200,
                    acceptLocator::click
            );
            status = submitResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Technical Evaluation Reject", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Technical Evaluation Reject Function: {}", exception.getMessage());
        }
        return status;
    }
}