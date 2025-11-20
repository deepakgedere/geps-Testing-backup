package com.source.classes.invoices.woinvoice.edit;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.invoices.woinvoices.IWoInvEdit;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.util.List;

import static com.constants.invoices.woinvoice.LInvChecklistReject.getTitle;
import static com.constants.invoices.woinvoice.LInvEdit.*;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class WoInvEdit implements IWoInvEdit {

    Logger logger;
    Page page;
    JsonNode jsonNode;
    ILogin iLogin;
    ILogout iLogout;
    String appUrl;

    private WoInvEdit(){
    }

//TODO Constructor
    public WoInvEdit(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(WoInvEdit.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int edit(){
        int status = 0;
        try {
            String vendorMailId = jsonNode.get("mailIds").get("vendorEmail").asText();
            iLogin.performLogin(vendorMailId);
            page.waitForLoadState(LoadState.NETWORKIDLE);

            Locator vendorSelection = page.locator("#vendor-container");
            if(vendorSelection.isVisible()){
                page.locator("//tr[.//td[contains(text(),'" + jsonNode.get("requisition").get("vendorName").asText() + "')]]//input[@type='radio']").click();
                page.locator("#glbBtnChangeVendor").click();
            }

            Locator invoiceNavigationBarLocator = page.locator(INVOICE_NAVIGATION_BAR);
            locatorVisibleHandler(invoiceNavigationBarLocator);
            invoiceNavigationBarLocator.click();

            String woReferenceId = jsonNode.get("invoices").get("workOrderInvoiceReferenceId").asText();
            Locator invoiceTitle = page.locator(getTitle(woReferenceId));
            locatorVisibleHandler(invoiceTitle);
            invoiceTitle.click();

            Locator editButtonLocator = page.locator(EDIT_BUTTON);
            locatorVisibleHandler(editButtonLocator);
            editButtonLocator.click();

            Locator createButtonLocator = page.locator(POP_UP_ACCEPT);
            locatorVisibleHandler(createButtonLocator);
            createButtonLocator.click();

            Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptButtonLocator);

            Response invoiceResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/VP/Invoices/") && response.status() == 200,
                    acceptButtonLocator::click
            );
            status = invoiceResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Work Order Invoice Edit", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in WO Invoice Edit function: {}", exception.getMessage());
        }
        return status;
    }
}