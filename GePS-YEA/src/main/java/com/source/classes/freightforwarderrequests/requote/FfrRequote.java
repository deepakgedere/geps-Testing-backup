package com.source.classes.freightforwarderrequests.requote;
import com.constants.freightforwarderrequests.LFfrQuote;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.freightforwarderrequests.IFfrQuote;
import com.source.interfaces.freightforwarderrequests.IFfrRequote;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.util.List;
import static com.constants.dispatchnotes.LDnAssign.DETAILS_BUTTON;
import static com.constants.dispatchnotes.LDnAssign.LIST_CONTAINER;
import static com.constants.freightforwarderrequests.LFfrQuote.*;
import static com.constants.freightforwarderrequests.LFfrReQuote.*;
import static com.constants.inspections.LInsCreate.getTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class FfrRequote implements IFfrRequote {

    Logger logger;
    ILogin iLogin;
    ILogout iLogout;
    JsonNode jsonNode;
    IFfrQuote iFfrQuote;
    Page page;
    String appUrl;

    private FfrRequote(){
    }

//TODO Constructor
    public FfrRequote(ILogin iLogin, JsonNode jsonNode, IFfrQuote iFfrQuote, ILogout iLogout, Page page){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.iFfrQuote = iFfrQuote;
        this.iLogout = iLogout;
        this.page = page;
        this.logger = LoggerUtil.getLogger(FfrRequote.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int[] requote(){
        int[] status = new int[2];
        try {
            String logisticsManager = jsonNode.get("mailIds").get("logisticsManagerEmail").asText();
            iLogin.performLogin(logisticsManager);

            Locator dnNavigationBarLocator = page.locator(DN_NAVIGATION_BAR);
            locatorVisibleHandler(dnNavigationBarLocator);
            dnNavigationBarLocator.click();

            String dnRefId = jsonNode.get("dispatchNotes").get("dispatchNoteReferenceId").asText();
            Locator dnTitle = page.locator(getTitle(dnRefId));
            locatorVisibleHandler(dnTitle);
            dnTitle.click();

            Locator requoteButtonLocator = page.locator(REQUOTE_BUTTON);
            locatorVisibleHandler(requoteButtonLocator);
            requoteButtonLocator.first().click();

            Locator saveButtonLocator = page.locator(SAVE_BUTTON);
            locatorVisibleHandler(saveButtonLocator);
            saveButtonLocator.last().click();

            Locator emailPopUpLocator = page.locator(EMAIL_POP_UP);
            locatorVisibleHandler(emailPopUpLocator);

            Response dnResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/DispatchNotes/") && response.status() == 200,
                    emailPopUpLocator.first()::click
            );
            status[0] = dnResponse.status();

            iLogout.performLogout();

            String vendorMailId = jsonNode.get("mailIds").get("vendorEmail").asText();
            iLogin.performLogin(vendorMailId);

            Locator ffrNavigationBarLocator = page.locator(FFR_NAVIGATION_BAR);
            locatorVisibleHandler(ffrNavigationBarLocator);
            ffrNavigationBarLocator.click();

            String ffrRefId = jsonNode.get("freightForwarderRequests").get("freightForwarderReferenceId").asText();
            Locator ffrTitle = page.locator(getTitle(ffrRefId));
            locatorVisibleHandler(ffrTitle);
            ffrTitle.click();

            Locator sendReQuoteButtonLocator = page.locator(VENDOR_REQUOTE_BUTTON);
            locatorVisibleHandler(sendReQuoteButtonLocator);
            sendReQuoteButtonLocator.click();

            Locator submitQuotationLocator = page.locator(SUBMIT_REQUOTE_BUTTON);
            locatorVisibleHandler(submitQuotationLocator);

            Response ffrResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/VP/DnForFrightForworder/") && response.status() == 200,
                    submitQuotationLocator.first()::click
            );
            status[1] = ffrResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Freight Forwarder Re-Quote", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Freight Forwarder Requests Requote function: {}", exception.getMessage());
        }
        return status;
    }
}