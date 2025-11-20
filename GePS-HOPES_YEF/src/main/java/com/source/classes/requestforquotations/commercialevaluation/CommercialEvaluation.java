package com.source.classes.requestforquotations.commercialevaluation;
import com.constants.requestforquotations.LCeCreate;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.RequestOptions;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.requestforquotations.ICeCreate;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.requestforquotations.LCeCreate.*;
import static com.utils.GetTitleUtil.getRFQTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class CommercialEvaluation implements ICeCreate {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    private String appUrl;

    private CommercialEvaluation(){
    }

//TODO Constructor
    public CommercialEvaluation(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(CommercialEvaluation.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int commercialEvaluationButton(String type){
        int status = 0;
        try {
            String buyerEmailId = jsonNode.get("mailIds").get("buyerEmail").asText();

            iLogin.performLogin(buyerEmailId);

            Locator rfqNavigationBarLocator = page.locator(RFQ_NAVIGATION_BAR);
            locatorVisibleHandler(rfqNavigationBarLocator);
            rfqNavigationBarLocator.click();

            String title = getRFQTransactionTitle(type);
            Locator titleLocator = page.locator(LCeCreate.getTitle(title));
            locatorVisibleHandler(titleLocator);
            titleLocator.first().click();

            Locator createButtonLocator = page.locator(CREATE_BUTTON);
            locatorVisibleHandler(createButtonLocator);
            createButtonLocator.click();

            Locator selectionStatusLocator = page.locator(SELECTION_CRITERIA);
            locatorVisibleHandler(selectionStatusLocator);
            selectionStatusLocator.click();
            selectionStatusLocator.selectOption(OPTION);

            Locator submitButtonLocator = page.locator(SUBMIT_BUTTON);
            locatorVisibleHandler(submitButtonLocator);
            submitButtonLocator.click();

            Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptButtonLocator);

            Response updateResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/VP/RequestForQuotations/all/") && response.status() == 200,
                    acceptButtonLocator::click
            );
            status = updateResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Commercial Evaluation Create", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Commercial Evaluation Create Function: {}", exception.getMessage());
        }
        return status;
    }
}