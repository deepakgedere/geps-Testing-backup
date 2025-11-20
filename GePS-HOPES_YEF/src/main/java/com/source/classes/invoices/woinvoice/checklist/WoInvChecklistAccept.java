package com.source.classes.invoices.woinvoice.checklist;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.invoices.woinvoices.IWoInvChecklistAccept;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.util.List;
import static com.constants.invoices.woinvoice.LInvChecklistAccept.*;
import static com.constants.invoices.woinvoice.LInvChecklistReject.getTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class WoInvChecklistAccept implements IWoInvChecklistAccept {

    Logger logger;
    Page page;
    JsonNode jsonNode;
    ILogin iLogin;
    ILogout iLogout;
    String appUrl;

    private WoInvChecklistAccept(){
    }

//TODO Constructor
    public WoInvChecklistAccept(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout){
        this.page = page;
        this.jsonNode = jsonNode;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(WoInvChecklistAccept.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int accept(){
        int status = 0;
        try {
            String buyerMailId = jsonNode.get("invoices").get("verifierEmail").asText();
            iLogin.performLogin(buyerMailId);

            Locator invoiceNaviagtionBarLocator = page.locator(INVOICE_NAVIGATION_BAR);
            locatorVisibleHandler(invoiceNaviagtionBarLocator);
            invoiceNaviagtionBarLocator.click();

            String woReferenceId = jsonNode.get("invoices").get("workOrderInvoiceReferenceId").asText();
            Locator invoiceTitle = page.locator(getTitle(woReferenceId));
            locatorVisibleHandler(invoiceTitle);
            invoiceTitle.click();

            Locator checkListLocator = page.locator(CHECKLIST_BUTTON);
            locatorVisibleHandler(checkListLocator);
            checkListLocator.first().click();

            Locator selectAllLocator = page.locator(SELECT_ALL_CHECKBOXES);
            locatorVisibleHandler(selectAllLocator);
            selectAllLocator.first().click();

            Locator acceptCheckListLocator = page.locator(ACCEPT_CHECKLIST_BUTTON);
            locatorVisibleHandler(acceptCheckListLocator);

            Response invoiceResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/Invoices/") && response.status() == 200,
                    acceptCheckListLocator::click
            );
            status = invoiceResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Work Order Invoice Checklist Accept", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in WO Invoice Checklist Accept function: {}", exception.getMessage());
        }
        return status;
    }
}