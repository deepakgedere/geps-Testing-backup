package com.source.classes.invoices.poinvoice.cancel;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.invoices.poinvoices.IInvCancel;
import com.source.interfaces.invoices.poinvoices.IInvCreate;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.invoices.poinvoice.LInvCancel.*;
import static com.constants.orderschedules.LOsEdit.getTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class InvCancel implements IInvCancel {

    Logger logger;
    Page page;
    JsonNode jsonNode;
    ILogin iLogin;
    ILogout iLogout;
    IInvCreate iInvCreate;

    private InvCancel(){
    }

//TODO Constructor
    public InvCancel(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, IInvCreate iInvCreate){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.iInvCreate = iInvCreate;
        this.logger = LoggerUtil.getLogger(InvCancel.class);
    }

    public int cancel(String referenceId, String transactionId, String uid){
        int status = 0;
        try {
            String appUrl = jsonNode.get("configSettings").get("appUrl").asText();
            String buyerMailId = jsonNode.get("invoices").get("verifierEmail").asText();
            iLogin.performLogin(buyerMailId);

            Locator invoiceNavigationBarLocator = page.locator(INVOICE_NAVIGATION_BAR);
            locatorVisibleHandler(invoiceNavigationBarLocator);
            invoiceNavigationBarLocator.click();

            Locator invoiceTitle = page.locator(getTitle(referenceId));
            locatorVisibleHandler(invoiceTitle);
            invoiceTitle.click();

            Locator suspendButtonLocator = page.locator(SUSPEND_BUTTON);
            locatorVisibleHandler(suspendButtonLocator);
            suspendButtonLocator.click();

            Locator remarksInputLocator = page.locator(REMARKS_INPUT);
            locatorVisibleHandler(remarksInputLocator);
            remarksInputLocator.fill("Cancelled");

            Locator acceptLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptLocator);

            Response invoiceResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/Invoices/") && response.status() == 200,
                    acceptLocator::click);

            status = invoiceResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Purchase Order Invoice Cancel", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in PO Invoice Cancel function: {}", exception.getMessage());
        }
        return status;
    }
}