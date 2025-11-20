package com.source.classes.invoices.poinvoice.edit;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.invoices.poinvoices.IInvEdit;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.invoices.poinvoice.LInvEdit.*;
import static com.constants.requisitions.LPrApprove.getTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class InvEdit implements IInvEdit {

    Logger logger;
    Page page;
    JsonNode jsonNode;
    ILogin iLogin;
    ILogout iLogout;

    private InvEdit(){
    }

//TODO Constructor
    public InvEdit(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(InvEdit.class);
    }

    public int edit(String referenceId, String transactionId, String uid, String type) {
        int status = 0;
        try {
            String appUrl = jsonNode.get("configSettings").get("appUrl").asText();
            String vendorMailId;
            if (type.equalsIgnoreCase("punchout")) {
                vendorMailId = "punchoutVendorEmail";
            } else {
                vendorMailId = jsonNode.get("mailIds").get("vendorEmail").asText();
            }
            iLogin.performLogin(vendorMailId);

            page.waitForLoadState(LoadState.NETWORKIDLE);

            Locator vendorSelection = page.locator("#vendor-container");
            if(vendorSelection.isVisible()){
                if(type.equalsIgnoreCase("punchout"))
                    page.locator("//tr[.//td[contains(text(),'" + jsonNode.get("requisition").get("punchoutVendorName").asText() + "')]]//input[@type='radio']").click();
                else
                    page.locator("//tr[.//td[contains(text(),'" + jsonNode.get("requisition").get("vendorName").asText() + "')]]//input[@type='radio']").click();
                page.locator("#glbBtnChangeVendor").click();
            }

            Locator invoiceNavigationBarLocator = page.locator(INVOICE_NAVIGATION_BAR);
            locatorVisibleHandler(invoiceNavigationBarLocator);
            invoiceNavigationBarLocator.click();

            Locator invoiceTitle = page.locator(getTitle(referenceId));
            locatorVisibleHandler(invoiceTitle);
            invoiceTitle.click();

            Locator editButtonLocator = page.locator(EDIT_BUTTON);
            locatorVisibleHandler(editButtonLocator);
            editButtonLocator.click();

            page.waitForLoadState(LoadState.NETWORKIDLE);

            Locator popUpLocator = page.locator(POP_UP_ACCEPT);
            locatorVisibleHandler(popUpLocator);
            popUpLocator.click();

            Locator acceptLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptLocator);

            Response invoiceResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/VP/Invoices/") && response.status() == 200,
                    acceptLocator::click);

            status = invoiceResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Purchase Order Invoice Edit", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in PO Invoice Edit function: {}", exception.getMessage());
        }
        return status;
    }
}