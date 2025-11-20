package com.source.classes.invoices.woinvoice.reject;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.classes.invoices.woinvoice.verify.WoInvVerify;
import com.source.interfaces.invoices.poinvoices.IInvSendForApproval;
import com.source.interfaces.invoices.poinvoices.IInvVerify;
import com.source.interfaces.invoices.woinvoices.IWoInvReject;
import com.source.interfaces.invoices.woinvoices.IWoInvSendForApproval;
import com.source.interfaces.invoices.woinvoices.IWoInvVerify;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.util.List;

import static com.constants.invoices.woinvoice.LInvChecklistReject.getTitle;
import static com.constants.invoices.woinvoice.LInvReject.*;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;


public class WoInvReject implements IWoInvReject {

    Logger logger;
    Page page;
    JsonNode jsonNode;
    ILogin iLogin;
    ILogout iLogout;
    IWoInvSendForApproval iWoInvSendForApproval;
    IWoInvVerify iWoInvVerify;
    String appUrl;

    private WoInvReject(){
    }

//TODO Constructor
    public WoInvReject(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, IWoInvSendForApproval iWoInvSendForApproval, IWoInvVerify iWoInvVerify){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.iWoInvSendForApproval = iWoInvSendForApproval;
        this.iWoInvVerify = iWoInvVerify;
        this.logger = LoggerUtil.getLogger(WoInvReject.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int reject(){
        int status = 0;
        try {
            String approver = jsonNode.get("invoices").get("woInvoiceApprovers").asText();
            iLogin.performLogin(approver);

            Locator invoiceNavigationBarLocator = page.locator(INVOICE_NAVIGATION_BAR);
            locatorVisibleHandler(invoiceNavigationBarLocator);
            invoiceNavigationBarLocator.click();

            String woReferenceId = jsonNode.get("invoices").get("workOrderInvoiceReferenceId").asText();
            Locator invoiceTitle = page.locator(getTitle(woReferenceId));
            locatorVisibleHandler(invoiceTitle);
            invoiceTitle.click();

            Locator rejectButtonLocator = page.locator(REJECT_BUTTON);
            locatorVisibleHandler(rejectButtonLocator);
            rejectButtonLocator.click();

            Locator remarksInputLocator = page.locator(REMARKS_INPUT);
            locatorVisibleHandler(remarksInputLocator);
            remarksInputLocator.fill("Rejected");

            Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptButtonLocator);

            Response invoiceResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/Invoices/")
                            && response.status() == 200
                            && response.request().method().equals("GET"),
                    acceptButtonLocator::click
            );
            status = invoiceResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Work Order Invoice Reject", page);

            iLogout.performLogout();

            iWoInvVerify.verify();
            iWoInvSendForApproval.sendForApproval();
        } catch (Exception exception) {
            logger.error("Exception in WO Invoice Reject function: {}", exception.getMessage());
        }
        return status;
    }
}