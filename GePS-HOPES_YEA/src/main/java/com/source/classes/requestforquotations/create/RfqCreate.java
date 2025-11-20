package com.source.classes.requestforquotations.create;
import com.constants.requestforquotations.LCeCreate;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.RequestOptions;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.requestforquotations.IRfqCreate;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.requestforquotations.LRfqCreate.*;
import static com.utils.GetTitleUtil.getRFQTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class RfqCreate implements IRfqCreate {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    private String appUrl;
    PlaywrightFactory playwrightFactory;
    ObjectMapper objectMapper;

    private RfqCreate(){
    }

//TODO Constructor
    public RfqCreate(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, PlaywrightFactory playwrightFactory, ObjectMapper objectMapper){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(RfqCreate.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
        this.playwrightFactory = playwrightFactory;
        this.objectMapper = objectMapper;
    }

    public int buyerRfqCreate(String type) {
        int status = 0;
        try {
            String buyerEmailId = jsonNode.get("mailIds").get("buyerEmail").asText();
            String rfqNotes = jsonNode.get("requestForQuotation").get("rfqNotes").asText();

            iLogin.performLogin(buyerEmailId);

            String title = getRFQTransactionTitle(type);
            Locator titleLocator = page.locator(LCeCreate.getTitle(title));
            locatorVisibleHandler(titleLocator);
            titleLocator.first().click();

            Locator rfqCreateButton = page.locator(CREATE_RFQ_BUTTON);
            locatorVisibleHandler(rfqCreateButton);
            rfqCreateButton.click();

            Locator notesLocator = page.locator(NOTES);
            locatorVisibleHandler(notesLocator);
            notesLocator.fill(rfqNotes);

            Locator createButtonLocator = page.locator(CREATE_BUTTON);
            locatorVisibleHandler(createButtonLocator);
            createButtonLocator.click();

            Locator yesButtonLocator = page.locator(YES_BUTTON);
            locatorVisibleHandler(yesButtonLocator);

            String rfqType;
            if(type.equalsIgnoreCase("sales")) {
                rfqType = "/api/RequestForQuotationsOthers/";
            } else if (type.equalsIgnoreCase("ps")) {
                rfqType = "/api/RequestForQuotations/";
            } else {
                rfqType = "/api/RequestForQuotationsNonPoc/";
            }

            Response createResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + rfqType) && response.status() == 200,
                    yesButtonLocator::click
            );

            status = createResponse.status();

            String url = page.url();
            String[] urlArray = url.split("=");
            String getUid = urlArray[1];
            playwrightFactory.savePropertiesIntoJsonFile("requestForQuotation", "requestForQuotationUid", getUid);

            //TODO Save Id for PR List
            if (type.equalsIgnoreCase("sales")) {
                APIResponse apiResponse = page.request().fetch(appUrl + "/api/RequestForQuotationsOthers/" + getUid, RequestOptions.create());
                JsonNode jsonNode1 = objectMapper.readTree(apiResponse.body());
                String requestForQuotationId = jsonNode.get("baseId").asText();
                String transactionId = jsonNode1.get("transactionId").asText();
                playwrightFactory.savePropertiesIntoJsonFile("requestForQuotation", "requestForQuotationId", requestForQuotationId);
                playwrightFactory.savePropertiesIntoJsonFile("requestForQuotation", "salesTransactionNumber", transactionId);
            }

            PlaywrightFactory.attachScreenshotWithName("Request For Quotation Create", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Buyer RFQ Create Function: {}", exception.getMessage());
        }
        return status;
    }
}