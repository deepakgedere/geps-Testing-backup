package com.source.classes.invoices.poinvoice.invreturn;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.invoices.poinvoices.IInvReturn;
import com.source.interfaces.invoices.poinvoices.IInvSendForApproval;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.invoices.poinvoice.LInvReturn.*;
import static com.constants.requisitions.LPrApprove.getTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class InvReturn implements IInvReturn {

    Logger logger;
    Page page;
    JsonNode jsonNode;
    ILogin iLogin;
    ILogout iLogout;

    private InvReturn(){
    }

//TODO Constructor
    public InvReturn(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(InvReturn.class);
    }

    public int invReturn(String referenceId, String transactionId, String uid, String type){
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

            Locator suspendButtonLocator = page.locator(RETURN_BUTTON);
            locatorVisibleHandler(suspendButtonLocator);
            suspendButtonLocator.click();

            Locator remarksInputLocator = page.locator(REMARKS_INPUT);
            locatorVisibleHandler(remarksInputLocator);
            remarksInputLocator.fill("Returned");

            Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptButtonLocator);
            acceptButtonLocator.click();

            Locator emailPopupButton = page.locator(EMAIL_POP_UP_BUTTON);
            locatorVisibleHandler(emailPopupButton);

            Response invoiceResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/Invoices/") && response.status() == 200,
                    emailPopupButton::click);

            status = invoiceResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Purchase Order Invoice Return", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in PO Invoice Return function: {}", exception.getMessage());
        }
        return status;
    }
}