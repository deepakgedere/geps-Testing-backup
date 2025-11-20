package com.source.classes.invoices.poinvoice.checklist;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.invoices.poinvoices.IInvChecklistReject;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.invoices.poinvoice.LInvChecklistReject.*;
import static com.constants.requisitions.LPrApprove.getTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class InvChecklistReject implements IInvChecklistReject {

    Logger logger;
    Page page;
    JsonNode jsonNode;
    ILogin iLogin;
    ILogout iLogout;

    private InvChecklistReject(){
    }

//TODO Constructor
    public InvChecklistReject(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout){
        this.page = page;
        this.jsonNode = jsonNode;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(InvChecklistReject.class);
    }

    public int reject(String referenceId, String transactionId, String uid){
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

            Locator checklistLocator = page.locator(CHECKLIST_BUTTON);
            locatorVisibleHandler(checklistLocator);
            checklistLocator.first().click();

            Locator rejectButtonLocator = page.locator(REJECT_CHECKLIST_BUTTON);
            locatorVisibleHandler(rejectButtonLocator);

            Response invoiceResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/Invoices/") && response.status() == 200,
                    rejectButtonLocator::click);

            status = invoiceResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Purchase Order Invoice Checklist Reject", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in PO Invoice Checklist Reject function: {}", exception.getMessage());
        }
        return status;
    }
}