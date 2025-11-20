package com.source.classes.invoices.woinvoice.approve;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.RequestOptions;
import com.source.interfaces.invoices.woinvoices.IWoInvApproval;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import com.utils.rpa.invoiceverification.IV_Flow;
import org.apache.logging.log4j.Logger;
import java.util.List;
import static com.constants.invoices.woinvoice.LInvApproval.*;
import static com.constants.invoices.woinvoice.LInvChecklistReject.getTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;
import static com.utils.SaveToTestDataJsonUtil.saveAndReturNextApprover;

public class WoInvApproval implements IWoInvApproval {

    Logger logger;
    Page page;
    JsonNode jsonNode;
    ILogin iLogin;
    ILogout iLogout;
    String appUrl;
    ObjectMapper objectMapper;
    PlaywrightFactory playwrightFactory;
    IV_Flow ivFlow;

    private WoInvApproval(){
    }

//TODO Constructor
    public WoInvApproval(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, ObjectMapper objectMapper, PlaywrightFactory playwrightFactory, IV_Flow ivFlow){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.objectMapper = objectMapper;
        this.playwrightFactory = playwrightFactory;
        this.ivFlow = ivFlow;
        this.logger = LoggerUtil.getLogger(WoInvApproval.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int approval(){
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

            Locator approveButtonLocator = page.locator(APPROVE_BUTTON);
            locatorVisibleHandler(approveButtonLocator);
            approveButtonLocator.click();

            Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptButtonLocator);

            Response invoiceResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/Invoices/")
                            && response.status() == 200
                            && response.request().method().equals("GET"),
                    acceptButtonLocator::click
            );
            status = invoiceResponse.status();

            String url = page.url();
            String[] urlArray = url.split("=");
            String getUid = urlArray[1];

            APIResponse apiResponse = page.request().fetch(appUrl + "/api/Invoices/" + getUid, RequestOptions.create());
            JsonNode jsonNode = objectMapper.readTree(apiResponse.body());
            String invoiceReferenceId = jsonNode.get("referenceId").asText();
            String invoiceId = jsonNode.get("id").asText();
            String invoiceStatus = jsonNode.get("status").asText();

            playwrightFactory.savePropertiesIntoJsonFile("invoices", "status", invoiceStatus);
            playwrightFactory.savePropertiesIntoJsonFile("invoices", "invoiceReferenceId", invoiceReferenceId);
            playwrightFactory.savePropertiesIntoJsonFile("invoices", "invoiceId", invoiceId);

            if(invoiceStatus.equalsIgnoreCase("InvoiceProcessing")) {
                ivFlow.ivFlow();
            } else {
                throw new RuntimeException("Invoice is not in Invoice Processing status");
            }

            PlaywrightFactory.attachScreenshotWithName("Work Order Invoice Approval", page);

            iLogout.performLogout();

            String nextApprover = saveAndReturNextApprover(invoiceResponse);
            if(!nextApprover.equalsIgnoreCase("")){
                approval();
            }
        } catch (Exception exception) {
            logger.error("Exception in WO Invoice Approval function: {}", exception.getMessage());
        }
        return status;
    }
}