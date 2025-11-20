package com.source.classes.requisitions.suspend;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.requisitions.IPrBuyerSuspend;
import org.apache.logging.log4j.Logger;
import static com.constants.requisitions.LPrBuyerSuspend.*;
import static com.constants.requisitions.LPrReject.getTitle;
import static com.utils.GetTitleUtil.getTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class BuyerSuspend implements IPrBuyerSuspend {

    private ILogin iLogin;
    private ILogout iLogout;
    private JsonNode jsonNode;
    private Page page;
    Logger logger;
    private String appUrl;
    private ObjectMapper objectMapper;

    private BuyerSuspend(){
    }

//TODO Constructor
    public BuyerSuspend(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, ObjectMapper objectMapper){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
        this.objectMapper = objectMapper;
    }

    public int suspend(String type, String purchaseType){
        int status = 0;
        try {
            String buyerMailId = jsonNode.get("mailIds").get("buyerEmail").asText();
            String remarks = jsonNode.get("commonRemarks").get("suspendRemarks").asText();

            iLogin.performLogin(buyerMailId);

            String getTitleFromUtil = getTransactionTitle(type, purchaseType);
            Locator titleLocator = page.locator(getTitle(getTitleFromUtil));
            locatorVisibleHandler(titleLocator);
            titleLocator.first().click();

            Locator suspendButtonLocator = page.locator(SUSPEND_BUTTON);
            locatorVisibleHandler(suspendButtonLocator);
            suspendButtonLocator.click();

            Locator remarksLocator = page.locator(REMARKS);
            locatorVisibleHandler(remarksLocator);
            remarksLocator.fill(remarks + " " + "by" + " " + buyerMailId);

            Locator yesButtonLocator = page.locator(YES);
            locatorVisibleHandler(yesButtonLocator);

            String reqType;
            if(type.equalsIgnoreCase("sales")){
                reqType = "/api/RequisitionsSales/";
            } else if(type.equalsIgnoreCase("ps")){
                reqType = "/api/Requisitions/";
            } else {
                reqType = "/api/RequisitionsNonPoc/";
            }

            Response suspendResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + reqType) && response.status() == 200,
                    yesButtonLocator::click
            );
            status = suspendResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Requisition Buyer Suspend", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Error in Requisition Buyer Suspend Function: {}", exception.getMessage());
        }
        return status;
    }
}