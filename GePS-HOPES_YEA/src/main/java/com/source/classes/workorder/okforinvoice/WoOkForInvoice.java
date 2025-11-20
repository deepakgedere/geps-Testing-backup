package com.source.classes.workorder.okforinvoice;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.workorders.IWoOkForInvoice;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.util.List;

import static com.constants.inspections.LInsCreate.getTitle;
import static com.constants.workorders.LOkForInvoice.*;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class WoOkForInvoice implements IWoOkForInvoice {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    PlaywrightFactory playwrightFactory;
    String appUrl;

    private WoOkForInvoice(){
    }

//TODO Constructor
    public WoOkForInvoice(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, PlaywrightFactory playwrightFactory){
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.playwrightFactory = playwrightFactory;
        this.logger = LoggerUtil.getLogger(WoOkForInvoice.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int okForInvoice() {
        int status = 0;
        try {
            String logisticsManagerMailId = jsonNode.get("mailIds").get("logisticsManagerEmail").asText();
            iLogin.performLogin(logisticsManagerMailId);

            Locator workOrderNavigationBarLocator = page.locator(WO_NAVIGATION_BAR);
            locatorVisibleHandler(workOrderNavigationBarLocator);
            workOrderNavigationBarLocator.click();

            String woRefId = jsonNode.get("workOrders").get("workOrderReferenceId").asText();
            Locator title = page.locator(getTitle(woRefId));
            locatorVisibleHandler(title);
            title.click();

            Locator okForInvoiceButton = page.locator(OK_FOR_INVOICE_BUTTON);
            locatorVisibleHandler(okForInvoiceButton);
            okForInvoiceButton.click();

            Locator yesButtonLocator = page.locator(YES_BUTTON);
            locatorVisibleHandler(yesButtonLocator);

            Response woResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/WorkOrder/") && response.request().method().equals("GET") && response.status() == 200,
                    yesButtonLocator::click
            );

            status = woResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Work Order Ok For Invoice", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Work Order OK For Invoice Function: {}", exception.getMessage());
        }
        return status;
    }
}