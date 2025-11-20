package com.source.classes.invoices.poinvoice.sendforapproval;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.RequestOptions;
import com.source.interfaces.invoices.poinvoices.IInvSendForApproval;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.constants.invoices.poinvoice.LInvSendForApproval.*;
import static com.constants.requisitions.LPrApprove.getTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;
import static com.utils.SaveToTestDataJsonUtil.saveAndReturNextApprover;

public class InvSendForApproval implements IInvSendForApproval {

    Logger logger;
    Page page;
    JsonNode jsonNode;
    ILogin iLogin;
    ILogout iLogout;
    ObjectMapper objectMapper;

    private InvSendForApproval(){
    }

//TODO Constructor
    public InvSendForApproval(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, ObjectMapper objectMapper){
        this.page = page;
        this.jsonNode = jsonNode;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.objectMapper = objectMapper;
        this.logger = LoggerUtil.getLogger(InvSendForApproval.class);
    }

    public int sendForApproval(String referenceId, String transactionId, String uid, String type){
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

            Locator sendForApprovalLocator = page.locator(SEND_FOR_APPROVAL_BUTTON);
            locatorVisibleHandler(sendForApprovalLocator);
            sendForApprovalLocator.click();

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
            JsonNode approvers = jsonNode.get("approvers");
            boolean advancePaymentFlag = jsonNode.get("isAdvancePymentChecked").asBoolean();
            boolean milestonePaymentFlag = jsonNode.get("isMilestoneChecked").asBoolean();

            List<String> approversList = new ArrayList<>();
            for(JsonNode approver : approvers) {
                String getApprover = approver.get("email").asText();
                approversList.add(getApprover);
            }

            String invoiceType;
            if (advancePaymentFlag) {
                invoiceType = "AdvancePaymentInvoice";
            } else if(milestonePaymentFlag) {
                invoiceType = "MilestonePaymentInvoice";
            } else {
                invoiceType = "NormalInvoice";
            }

            saveAndReturNextApprover(invoiceResponse, invoiceType);

            PlaywrightFactory.attachScreenshotWithName("Purchase Order Invoice Send For Approval", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in PO Invoice Send For Approval function: {}", exception.getMessage());
        }
        return status;
    }
}