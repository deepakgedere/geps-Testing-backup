package com.source.classes.requestforquotations.readyforevaluation;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.RequestOptions;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.requestforquotations.IReadyForEvalutation;
import org.apache.logging.log4j.Logger;
import static com.constants.requestforquotations.LReadyForEvaluation.*;
import static com.constants.requisitions.LPrEdit.getTitle;
import static com.utils.GetTitleUtil.getRFQTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class ReadyForEvaluation implements IReadyForEvalutation {

    Logger logger;
    PlaywrightFactory playwrightFactory;
    ObjectMapper objectMapper;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    private String appUrl;


    private ReadyForEvaluation(){
    }

//TODO Constructor
    public ReadyForEvaluation(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, PlaywrightFactory playwrightFactory, ObjectMapper objectMapper){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.playwrightFactory = playwrightFactory;
        this.objectMapper = objectMapper;
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int readyForEvaluationButton(String type){
        int status = 0;
        try {
            String buyerMailId = jsonNode.get("mailIds").get("buyerEmail").asText();
            iLogin.performLogin(buyerMailId);

            Locator rfqNavigationBarLocator = page.locator(RFQ_NAVIGATION_BAR);
            locatorVisibleHandler(rfqNavigationBarLocator);
            rfqNavigationBarLocator.click();

            String title = getRFQTransactionTitle(type);
            Locator titleLocator = page.locator(getTitle(title));
            locatorVisibleHandler(titleLocator);
            titleLocator.first().click();

            Locator readyForEvaluationButtonLocator = page.locator(READY_FOR_EVALUATION_BUTTON);
            locatorVisibleHandler(readyForEvaluationButtonLocator);
            readyForEvaluationButtonLocator.click();

            Locator acceptLocator = page.locator(YES);
            locatorVisibleHandler(acceptLocator);

            String rfqType;
            if (type.equalsIgnoreCase("sales")) {
                rfqType = "/api/RequestForQuotationsOthers/";
            } else if (type.equalsIgnoreCase("ps")) {
                rfqType = "/api/RequestForQuotations/";
            } else {
                rfqType = "/api/RequestForQuotationsNonPoc/";
            }

            Response submitResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + rfqType) && response.status() == 200,
                    acceptLocator::click
            );
            status = submitResponse.status();

            String url = page.url();
            String[] urlArray = url.split("=");
            String getUid = urlArray[1];
            playwrightFactory.savePropertiesIntoJsonFile("requestForQuotation", "requestForQuotationUid", getUid);

            if (type.equalsIgnoreCase("sales")) {
                APIResponse apiResponse = page.request().fetch(appUrl + "/api/RequestForQuotationsOthers/" + getUid, RequestOptions.create());
                JsonNode jsonNode = objectMapper.readTree(apiResponse.body());
                String requestForQuotationId = jsonNode.get("requestForQuotationItems").get(0).get("requestForQuotationId").asText();
                playwrightFactory.savePropertiesIntoJsonFile("requestForQuotation", "requestForQuotationId", requestForQuotationId);
            }

            PlaywrightFactory.attachScreenshotWithName("Ready For Evaluation", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Ready For Evaluation Button Function: {}", exception.getMessage());
        }
        return status;
    }
}