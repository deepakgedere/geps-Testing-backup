package com.source.classes.requestforquotations.suspend;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.requestforquotations.IRfqCreate;
import com.source.interfaces.requestforquotations.IRfqEdit;
import com.source.interfaces.requestforquotations.IRfqSuspend;
import com.source.interfaces.requisitions.IPrApprove;
import com.source.interfaces.requisitions.IPrAssign;
import com.source.interfaces.requisitions.IPrEdit;
import com.source.interfaces.requisitions.IPrSendForApproval;
import org.apache.logging.log4j.Logger;
import static com.constants.requestforquotations.LRfqSuspend.*;
import static com.utils.GetTitleUtil.getRFQTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class RfqSuspend implements IRfqSuspend {

    Logger logger;
    ILogin iLogin;
    ILogout iLogout;
    JsonNode jsonNode;
    Page page;
    IRfqEdit iRfqEdit;
    IPrEdit iPrEdit;
    IPrSendForApproval iPrSendForApproval;
    IPrApprove iPrApprove;
    IPrAssign iPrAssign;
    IRfqCreate iRfqCreate;
    private String appUrl;

    private RfqSuspend(){
    }

//TODO Constructor
    public RfqSuspend(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, IRfqEdit iRfqEdit, IPrEdit iPrEdit,
                      IPrSendForApproval iPrSendForApproval, IPrApprove iPrApprove, IPrAssign iPrAssign, IRfqCreate iRfqCreate){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.iRfqEdit = iRfqEdit;
        this.iPrEdit = iPrEdit;
        this.iPrSendForApproval = iPrSendForApproval;
        this.iPrApprove = iPrApprove;
        this.iPrAssign = iPrAssign;
        this.iRfqCreate = iRfqCreate;
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int suspendRfqEdit(String type) {
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

            Locator suspendButtonLocator = page.locator(SUSPEND_BUTTON);
            locatorVisibleHandler(suspendButtonLocator);
            suspendButtonLocator.click();

            Locator remarksInputLocator = page.locator(REMARKS_INPUT);
            locatorVisibleHandler(remarksInputLocator);
            remarksInputLocator.fill("Suspended");

            Locator acceptLocator = page.locator(YES);
            locatorVisibleHandler(acceptLocator);

            String rfqType;
            if (type.equalsIgnoreCase("sales")) {
                rfqType = "/api/RequestForQuotationsOthers/";
            } else if (type.equalsIgnoreCase("POC")) {
                rfqType = "/api/RequestForQuotations/";
            } else {
                rfqType = "/api/RequestForQuotationsNonPoc/";
            }

            Response suspendResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + rfqType) && response.status() == 200,
                    acceptLocator::click
            );
            status = suspendResponse.status();

            iLogout.performLogout();

            iRfqEdit.rfqEditMethod(type);
        } catch (Exception exception) {
            logger.error("Exception in Suspend RFQ Edit Function: {}", exception.getMessage());
        }
        return status;
    }

    public int suspendPREdit(String type, String purchaseType) {
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

            Locator suspendButtonLocator = page.locator(SUSPEND_BUTTON);
            locatorVisibleHandler(suspendButtonLocator);
            suspendButtonLocator.click();

            Locator remarksInputLocator = page.locator(REMARKS_INPUT);
            locatorVisibleHandler(remarksInputLocator);
            remarksInputLocator.fill("Suspended");

            Locator acceptLocator = page.locator(YES);
            locatorVisibleHandler(acceptLocator);

            String rfqType;
            if (type.equalsIgnoreCase("sales")) {
                rfqType = "/api/RequestForQuotationsOthers/";
            } else if (type.equalsIgnoreCase("POC")) {
                rfqType = "/api/RequestForQuotations/";
            } else {
                rfqType = "/api/RequestForQuotationsNonPoc/";
            }

            Response suspendResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + rfqType) && response.status() == 200,
                    acceptLocator::click
            );
            status = suspendResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Request For Quotation Suspend", page);

            iLogout.performLogout();

            iPrEdit.edit(type, purchaseType);
            iPrSendForApproval.sendForApproval(type, purchaseType);
            iPrApprove.approve(type, purchaseType);
            iPrAssign.buyerManagerAssign(type, purchaseType);
            iRfqCreate.buyerRfqCreate(type);
        } catch (Exception exception) {
            logger.error("Exception in Suspend RFQ and PR Edit Function: {}", exception.getMessage());
        }
        return status;
    }
}