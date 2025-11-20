package com.source.classes.freightforwarderrequests.quote;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.freightforwarderrequests.IFfrQuote;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.util.List;
import static com.constants.freightforwarderrequests.LFfrQuote.*;
import static com.constants.inspections.LInsCreate.getTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class FfrQuote implements IFfrQuote {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    String appUrl;

    private FfrQuote(){
    }

//TODO Constructor
    public FfrQuote(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(FfrQuote.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int quote() {
        int status = 0;
        try {
            String vendorMailId = jsonNode.get("mailIds").get("vendorEmail").asText();
            iLogin.performLogin(vendorMailId);

            Locator ffrNavigationBarLocator = page.locator(FFR_NAVIGATION_BAR);
            locatorVisibleHandler(ffrNavigationBarLocator);
            ffrNavigationBarLocator.click();

            String ffrRefId = jsonNode.get("freightForwarderRequests").get("freightForwarderReferenceId").asText();
            Locator ffrTitle = page.locator(getTitle(ffrRefId));
            locatorVisibleHandler(ffrTitle);
            ffrTitle.click();

            Locator sendQuoteButtonLocator = page.locator(SEND_QUOTE_BUTTON);
            locatorVisibleHandler(sendQuoteButtonLocator);
            sendQuoteButtonLocator.click();

            String totalChargeableWeight = jsonNode.get("freightForwarderRequests").get("totalChargeableWeightKg").asText();
            Locator totalChargeableWeightLocator = page.locator(TOTAL_CHARGEABLE_WEIGHT);
            locatorVisibleHandler(totalChargeableWeightLocator);
            totalChargeableWeightLocator.fill(totalChargeableWeight);

            String unitRate = jsonNode.get("freightForwarderRequests").get("unitRate").asText();
            Locator unitRateLocator = page.locator(UNIT_RATE);
            locatorVisibleHandler(unitRateLocator);
            unitRateLocator.fill(unitRate);

            Locator submitQuotationLocator = page.locator(SUBMIT_BUTTON);
            locatorVisibleHandler(submitQuotationLocator);
            submitQuotationLocator.click();

            Response ffrResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/VP/DnForFrightForworder/") && response.status() == 200,
                    submitQuotationLocator.first()::click
            );
            status = ffrResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Freight Forwarder Quote", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Freight Forwarder Requests Quote function: {}", exception.getMessage());
        }
        return status;
    }
}