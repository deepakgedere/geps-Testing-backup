package com.source.classes.invoices.woinvoice.verify;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.invoices.woinvoices.IWoInvVerify;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.util.List;

import static com.constants.invoices.woinvoice.LInvChecklistReject.getTitle;
import static com.constants.invoices.woinvoice.LInvVerify.*;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;


public class WoInvVerify implements IWoInvVerify {

    Logger logger;
    Page page;
    JsonNode jsonNode;
    ILogin iLogin;
    ILogout iLogout;
    String appUrl;

    private WoInvVerify(){
    }

//TODO Constructor
    public WoInvVerify(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout){
        this.page = page;
        this.jsonNode = jsonNode;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(WoInvVerify.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int verify(){
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

            Locator verifyButtonLocator = page.locator(VERIFY_BUTTON);
            locatorVisibleHandler(verifyButtonLocator);
            verifyButtonLocator.click();

            Locator remarksInputLocator = page.locator(REMARKS_INPUT);
            locatorVisibleHandler(remarksInputLocator);
            remarksInputLocator.fill("Verified");

            Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptButtonLocator);

            Response invoiceResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/Invoices/") && response.status() == 200,
                    acceptButtonLocator::click
            );
            status = invoiceResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Work Order Invoice Verify", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in WO Invoice Verify function: {}", exception.getMessage());
        }
        return status;
    }
}