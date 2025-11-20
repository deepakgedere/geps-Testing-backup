package com.source.classes.invoices.poinvoice.approve;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.RequestOptions;
import com.source.interfaces.invoices.poinvoices.IInvApproval;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import com.utils.rpa.invoiceverification.IV_Flow;
import org.apache.logging.log4j.Logger;
import static com.constants.invoices.poinvoice.LInvApproval.*;
import static com.constants.orderschedules.LOsEdit.getTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;
import static com.utils.SaveToTestDataJsonUtil.saveAndReturNextApprover;

public class InvApproval implements IInvApproval {

    Logger logger;
    Page page;
    JsonNode jsonNode;
    ILogin iLogin;
    ILogout iLogout;
    ObjectMapper objectMapper;
    PlaywrightFactory playwrightFactory;
    IV_Flow ivFlow;

    private InvApproval(){
    }

//TODO Constructor
    public InvApproval(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, ObjectMapper objectMapper, PlaywrightFactory playwrightFactory, IV_Flow ivFlow){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.objectMapper = objectMapper;
        this.playwrightFactory = playwrightFactory;
        this.ivFlow = ivFlow;
        this.logger = LoggerUtil.getLogger(InvApproval.class);
    }

    public int approval(String referenceId, String transactionId, String uid){
        int status = 0;
        try {
            String appUrl = jsonNode.get("configSettings").get("appUrl").asText();
            String financeCheckerEmail = jsonNode.get("mailIds").get("financeCheckerEmail").asText();
            String accountType = jsonNode.get("invoices").get("accountType").asText();
            String documentType = jsonNode.get("invoices").get("documentType").asText();
            String generalLedger = jsonNode.get("invoices").get("generalLedger").asText();
            String bankAccount = jsonNode.get("invoices").get("bankAccount").asText();
            String taxCode = jsonNode.get("invoices").get("taxCode").asText();
            boolean advancePaymentFlag = jsonNode.get("purchaseOrderRequests").get("advancePaymentFlag").asBoolean();
            boolean milestonePaymentFlag = jsonNode.get("purchaseOrderRequests").get("milestonePaymentFlag").asBoolean();
            String approver;

            if(advancePaymentFlag || milestonePaymentFlag) {
                approver = jsonNode.get("invoices").get("advancePaymentInvoiceApprovers").asText();
                if(approver.isEmpty()) {
                    approver = jsonNode.get("invoices").get("milestonePaymentInvoiceApprovers").asText();
                }
            } else {
                approver = jsonNode.get("invoices").get("normalInvoiceApprovers").asText();
            }

            iLogin.performLogin(approver);

            Locator invoiceNavigationBarLocator = page.locator(INVOICE_NAVIGATION_BAR);
            locatorVisibleHandler(invoiceNavigationBarLocator);
            invoiceNavigationBarLocator.click();

            Locator invoiceTitle = page.locator(getTitle(referenceId));
            locatorVisibleHandler(invoiceTitle);
            invoiceTitle.click();

            if(approver.equalsIgnoreCase(financeCheckerEmail)) {
                Locator updateFinanceFields = page.locator(UPDATE_FINANCE_FIELDS);
                locatorVisibleHandler(updateFinanceFields);
                updateFinanceFields.click();

                Locator bankAccountDropdownLocator = page.locator(BANK_ACCOUNT);
                locatorVisibleHandler(bankAccountDropdownLocator);
                if(bankAccountDropdownLocator.count() > 0 && bankAccountDropdownLocator.isVisible()){
                    bankAccountDropdownLocator.first().click();
                }
                Locator bankAccountDataLocator = page.locator(getBankAccount(bankAccount));
                locatorVisibleHandler(bankAccountDataLocator);
                if(bankAccountDataLocator.count() > 0 && bankAccountDataLocator.isVisible()){
                    bankAccountDataLocator.first().click();
                }

                Locator accountTypeDropdownLocator = page.locator(ACCOUNT_TYPE);
                locatorVisibleHandler(accountTypeDropdownLocator);
                if(accountTypeDropdownLocator.count() > 0 && accountTypeDropdownLocator.isVisible()){
                    accountTypeDropdownLocator.first().click();
                }
                Locator accountTypeDataLocator = page.locator(getAccountType(accountType));
                locatorVisibleHandler(accountTypeDataLocator);
                if(accountTypeDataLocator.count() > 0 && accountTypeDataLocator.isVisible()){
                    accountTypeDataLocator.first().click();
                }

                Locator documentTypeDropdownLocator = page.locator(DOCUMENT_TYPE);
                locatorVisibleHandler(documentTypeDropdownLocator);
                if(documentTypeDropdownLocator.count() > 0 && documentTypeDropdownLocator.isVisible()){
                    documentTypeDropdownLocator.first().click();
                }
                Locator documentTypeDataLocator = page.locator(getDocumentType(documentType));
                locatorVisibleHandler(documentTypeDataLocator);
                if(documentTypeDataLocator.count() > 0 && documentTypeDataLocator.isVisible()){
                    documentTypeDataLocator.first().click();
                }

                Locator generalLedgerDropdownLocator = page.locator(GENERAL_LEDGER);
                locatorVisibleHandler(generalLedgerDropdownLocator);
                if(generalLedgerDropdownLocator.count() > 0 && generalLedgerDropdownLocator.isVisible()){
                    generalLedgerDropdownLocator.first().click();
                }
                Locator generalLedgerDataLocator = page.locator(getGeneralLedger(generalLedger));
                locatorVisibleHandler(generalLedgerDataLocator);
                if(generalLedgerDataLocator.count() > 0 && generalLedgerDataLocator.isVisible()){
                    generalLedgerDataLocator.first().click();
                }

                Locator textLocator = page.locator(TEXT);
                locatorVisibleHandler(textLocator);
                if(textLocator.count() > 0 && textLocator.isVisible()){
                    textLocator.fill("Finance Field");
                }

                Locator taxCodeDropdownLocator = page.locator(TAX_CODE);
                locatorVisibleHandler(taxCodeDropdownLocator);
                if(taxCodeDropdownLocator.count() > 0 && taxCodeDropdownLocator.isVisible()){
                    taxCodeDropdownLocator.first().click();
                }
                Locator taxCodeDataLocator = page.locator(getTaxCode(taxCode));
                locatorVisibleHandler(taxCodeDataLocator);
                if(taxCodeDataLocator.count() > 0 && taxCodeDataLocator.isVisible()){
                    taxCodeDataLocator.first().click();
                }

                Locator saveFinanceFieldsLocator = page.locator(SAVE_FINANCE_FIELDS);
                locatorVisibleHandler(saveFinanceFieldsLocator);
                saveFinanceFieldsLocator.first().click();
            }

            Locator approveButtonLocator = page.locator(APPROVE_BUTTON);
            locatorVisibleHandler(approveButtonLocator);
            approveButtonLocator.first().click();

            Locator acceptLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptLocator);

            Response invoiceResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/Invoices/")
                            && response.status() == 200
                            && response.request().method().equals("GET"),
                    acceptLocator::click
            );
            status = invoiceResponse.status();

            String url = page.url();
            String[] urlArray = url.split("=");
            String getUid = urlArray[1];

            APIResponse apiResponse = page.request().fetch(appUrl + "/api/Invoices/" + getUid, RequestOptions.create());
            JsonNode jsonNode = objectMapper.readTree(apiResponse.body());
            String invoiceReferenceId = jsonNode.get("referenceId").asText();
            String vendorReferenceId = jsonNode.get("purchaseOrder").get("vendorReferenceId").asText();
            String invoiceId = jsonNode.get("id").asText();
            String invoiceStatus = jsonNode.get("status").asText();
            String companyCode = jsonNode.get("purchaseOrder").get("companyCode").asText();
            boolean advancePaymentFlag1 = jsonNode.get("isAdvancePymentChecked").asBoolean();
            boolean milestonePaymentFlag1 = jsonNode.get("isMilestoneChecked").asBoolean();

            playwrightFactory.savePropertiesIntoJsonFile("invoices", "status", invoiceStatus);
            playwrightFactory.savePropertiesIntoJsonFile("invoices", "companyCode", companyCode);
            playwrightFactory.savePropertiesIntoJsonFile("invoices", "invoiceReferenceId", invoiceReferenceId);
            playwrightFactory.savePropertiesIntoJsonFile("invoices", "vendorReferenceId", vendorReferenceId);
            playwrightFactory.savePropertiesIntoJsonFile("invoices", "invoiceId", invoiceId);

            if(invoiceStatus.equalsIgnoreCase("InvoiceProcessing")) {
                ivFlow.ivFlow();
            } else {
                throw new RuntimeException("Invoice is not in Invoice Processing status");
            }

            PlaywrightFactory.attachScreenshotWithName("Purchase Order Invoice Approve", page);

            iLogout.performLogout();

            String invoiceType;
            if (advancePaymentFlag1) {
                invoiceType = "AdvancePaymentInvoice";
            } else if(milestonePaymentFlag1) {
                invoiceType = "MilestonePaymentInvoice";
            } else {
                invoiceType = "NormalInvoice";
            }

            String nextApprover = saveAndReturNextApprover(invoiceResponse, invoiceType);
            if(!nextApprover.equalsIgnoreCase("")){
                approval(referenceId, transactionId, uid);
            }
        } catch(Exception exception) {
            logger.error("Exception in PO Invoice Approval function: {}", exception.getMessage());
        }
        return status;
    }
}