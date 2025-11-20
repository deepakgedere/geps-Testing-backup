package com.source.classes.requisitions.suspend;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.requisitions.IPrBuyerManagerSuspend;
import com.source.interfaces.requisitions.IPrEdit;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.requisitions.LPrBuyerManagerSuspend.*;
import static com.constants.requisitions.LPrReject.getTitle;
import static com.utils.GetTitleUtil.getTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class BuyerManagerSuspend implements IPrBuyerManagerSuspend {

    Logger logger;
    ILogin iLogin;
    ILogout iLogout;
    JsonNode jsonNode;
    Page page;
    IPrEdit iPrEdit;
    private String appUrl;

    private BuyerManagerSuspend(){
    }

//TODO Constructor
    public BuyerManagerSuspend(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, IPrEdit iPrEdit){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.iPrEdit = iPrEdit;
        this.logger = LoggerUtil.getLogger(BuyerManagerSuspend.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int suspend(String type, String purchaseType) {
        int status = 0;
        try {
            String buyerManagerMailId = jsonNode.get("mailIds").get("buyerManagerEmail").asText();
            String remarks = jsonNode.get("commonRemarks").get("suspendRemarks").asText();

            iLogin.performLogin(buyerManagerMailId);

            String getTitleFromUtil = getTransactionTitle(type, purchaseType);
            Locator titleLocator = page.locator(getTitle(getTitleFromUtil));
            locatorVisibleHandler(titleLocator);
            titleLocator.first().click();

            Locator suspendButtonLocator = page.locator(SUSPEND_BUTTON);
            locatorVisibleHandler(suspendButtonLocator);
            suspendButtonLocator.click();

            Locator remarksLocator = page.locator(REMARKS);
            locatorVisibleHandler(remarksLocator);
            remarksLocator.fill(remarks + " " + "by" + " " + buyerManagerMailId);

            Locator acceptLocator = page.locator(YES);
            locatorVisibleHandler(acceptLocator);

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
                    acceptLocator::click
            );
            status = suspendResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Requisition Buyer Manager Suspend", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Error in Requisition Buyer Manager Suspend Function: {}", exception.getMessage());
        }
        return status;
    }
}