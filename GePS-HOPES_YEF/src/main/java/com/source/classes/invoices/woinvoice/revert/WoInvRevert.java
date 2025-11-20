package com.source.classes.invoices.woinvoice.revert;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.invoices.woinvoices.IWoInvRevert;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import static com.constants.invoices.woinvoice.LInvChecklistReject.getTitle;
import static com.constants.invoices.woinvoice.LInvRevert.*;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;


public class WoInvRevert implements IWoInvRevert {

    Logger logger;
    Page page;
    JsonNode jsonNode;
    ILogin iLogin;
    ILogout iLogout;
    String appUrl;

    private WoInvRevert(){
    }

//TODO Constructor
    public WoInvRevert(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(WoInvRevert.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int revert(){
        int status = 0;
        try {
            String buyerMailId = jsonNode.get("invoices").get("verifierEmail").asText();
            iLogin.performLogin(buyerMailId);

            Locator invoiceNavigationBarLocator = page.locator(INVOICE_NAVIGATION_BAR);
            locatorVisibleHandler(invoiceNavigationBarLocator);
            invoiceNavigationBarLocator.click();

            String woReferenceId = jsonNode.get("invoices").get("workOrderInvoiceReferenceId").asText();
            Locator invoiceTitle = page.locator(getTitle(woReferenceId));
            locatorVisibleHandler(invoiceTitle);
            invoiceTitle.click();

            Locator revertButtonLocator = page.locator(REVERT_BUTTON);
            locatorVisibleHandler(revertButtonLocator);

            Response invoiceResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/Invoices/") && response.status() == 200,
                    revertButtonLocator::click
            );
            status = invoiceResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Work Order Invoice Revert", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in WO Invoice Revert function: {}", exception.getMessage());
        }
        return status;
    }
}