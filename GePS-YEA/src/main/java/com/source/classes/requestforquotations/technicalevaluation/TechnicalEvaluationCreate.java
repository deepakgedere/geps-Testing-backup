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
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.requestforquotations.LRfqSuspend.getTitle;
import static com.constants.requestforquotations.LTeCreate.*;
import static com.utils.GetTitleUtil.getRFQTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class TechnicalEvaluationCreate implements ITeCreate  {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    private String appUrl;

    private TechnicalEvaluationCreate(){
    }

//TODO Constructor
    public TechnicalEvaluationCreate(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(TechnicalEvaluationCreate.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int technicalEvaluationCreate(String type) {
        int status = 0;
        try {
            String requesterMailId = jsonNode.get("mailIds").get("requesterEmail").asText();
            iLogin.performLogin(requesterMailId);

            Locator rfqNavigationBarLocator = page.locator(RFQ_NAVIGATION_BAR);
            locatorVisibleHandler(rfqNavigationBarLocator);
            rfqNavigationBarLocator.click();

            String title = getRFQTransactionTitle(type);
            Locator titleLocator = page.locator(getTitle(title));
            locatorVisibleHandler(titleLocator);
            titleLocator.first().click();

            Locator teCreateButtonLocator = page.locator(TE_CREATE_BUTTON);
            locatorVisibleHandler(teCreateButtonLocator);
            teCreateButtonLocator.click();

            Locator vendorSelectCheckboxLocator = page.locator(VENDOR_SELECT_CHECKBOX);
            locatorVisibleHandler(vendorSelectCheckboxLocator);
            vendorSelectCheckboxLocator.click();

            Locator createTeButtonLocator = page.locator(CREATE_TECHNICAL_EVALUATION_BUTTON);
            locatorVisibleHandler(createTeButtonLocator);
            createTeButtonLocator.click();

            Locator remarksAccept = page.locator(YES);
            locatorVisibleHandler(remarksAccept);
            remarksAccept.click();

            Locator sendForApprovalLocator = page.locator(SEND_FOR_APPROVAL);
            locatorVisibleHandler(sendForApprovalLocator);
            sendForApprovalLocator.click();

            Locator teApproverSelectLocator = page.locator(APPROVER_SELECT);
            locatorVisibleHandler(teApproverSelectLocator);
            teApproverSelectLocator.first().click();

            String teApprover = jsonNode.get("mailIds").get("requesterEmail").asText();
            Locator teApproverSearchLocator = page.locator(SEARCH_FIELD);
            locatorVisibleHandler(teApproverSearchLocator);
            teApproverSearchLocator.fill(teApprover);

            Locator getTeApproverLocator = page.locator(getTeApprover(teApprover));
            locatorVisibleHandler(getTeApproverLocator);
            getTeApproverLocator.click();

            Locator saveApproverLocator = page.locator(SAVE_APPROVER);
            locatorVisibleHandler(saveApproverLocator);
            saveApproverLocator.click();

            Locator acceptLocator = page.locator(YES);
            locatorVisibleHandler(acceptLocator);

            Response submitResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/TechnicalEvaluations/") && response.status() == 200,
                    acceptLocator::click
            );
            status = submitResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Technical Evaluation Create", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Technical Evaluation Create Function: {}", exception.getMessage());
        }
        return status;
    }
}