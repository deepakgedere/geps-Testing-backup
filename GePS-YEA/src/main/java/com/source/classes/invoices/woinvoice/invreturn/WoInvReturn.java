package com.source.classes.invoices.woinvoice.invreturn;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.invoices.woinvoices.IWoInvReturn;
import com.source.interfaces.invoices.woinvoices.IWoInvSendForApproval;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import static com.constants.invoices.woinvoice.LInvChecklistReject.getTitle;
import static com.constants.invoices.woinvoice.LInvReturn.*;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class WoInvReturn implements IWoInvReturn {

    Logger logger;
    Page page;
    JsonNode jsonNode;
    ILogin iLogin;
    ILogout iLogout;
    IWoInvSendForApproval iWoInvSendForApproval;
    String appUrl;

    private WoInvReturn(){
    }

//TODO Constructor
    public WoInvReturn(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, IWoInvSendForApproval iWoInvSendForApproval){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.iWoInvSendForApproval = iWoInvSendForApproval;
        this.logger = LoggerUtil.getLogger(WoInvReturn.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int returnMethod(){
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

            Locator returnButtonLocator = page.locator(RETURN_BUTTON);
            locatorVisibleHandler(returnButtonLocator);
            returnButtonLocator.click();

            Locator remarksInputLocator = page.locator(REMARKS_INPUT);
            locatorVisibleHandler(remarksInputLocator);
            remarksInputLocator.fill("Returned");

            Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptButtonLocator);
            acceptButtonLocator.click();

            Locator submitButtonLocator = page.locator(SUBMIT_BUTTON);
            locatorVisibleHandler(submitButtonLocator);

            Response invResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/Invoices/") && response.status() == 200,
                    submitButtonLocator::click
            );
            status = invResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Work Order Invoice Return", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in WO Invoice Return function: {}", exception.getMessage());
        }
        return status;
    }
}