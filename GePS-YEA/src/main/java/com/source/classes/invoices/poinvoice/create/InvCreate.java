package com.source.classes.invoices.poinvoice.create;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.currencyexchangerate.ICurrencyExchangeRate;
import com.source.interfaces.invoices.poinvoices.IInvCreate;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.util.List;
import static com.constants.invoices.poinvoice.LInvCreate.*;
import static com.constants.invoices.woinvoice.LInvCreate.getCompanyId;
import static com.utils.GetInvoiceReferenceIdUtil.getInvoiceReferenceIds;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;
import static com.utils.SaveToTestDataJsonUtil.saveReferenceIdFromResponse;
import static com.utils.SaveToTestDataJsonUtil.saveVerifierEmail;

public class InvCreate implements IInvCreate {

    Logger logger;
    PlaywrightFactory playwrightFactory;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    ICurrencyExchangeRate iCurrencyExchangeRate;
    String appUrl;
    String invoiceTitle;

    int EUR = 4;
    int USD = 3;
    int INR = 2;
    int CAD = 2;

    private InvCreate() {
    }

    //TODO Constructor
    public InvCreate(PlaywrightFactory playwrightFactory, ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, ICurrencyExchangeRate iCurrencyExchangeRate) {
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.iCurrencyExchangeRate = iCurrencyExchangeRate;
        this.playwrightFactory = playwrightFactory;
        this.logger = LoggerUtil.getLogger(InvCreate.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
        this.invoiceTitle="";
    }

    public int invoiceTypeHandler() {
        int status = 0;
        try {
            String vendorMailId = jsonNode.get("mailIds").get("vendorEmail").asText();
            iLogin.performLogin(vendorMailId);

            boolean advancePaymentFlag = jsonNode.get("purchaseOrderRequests").get("advancePaymentFlag").asBoolean();
            boolean milestonePaymentFlag = jsonNode.get("purchaseOrderRequests").get("milestonePaymentFlag").asBoolean();
            int milestoneCount = jsonNode.get("purchaseOrderRequests").get("milestonePaymentCount").asInt();
            String transactionNumber = jsonNode.get("purchaseOrders").get("poTransactionId").asText();

//TODO Handle invoice creation based on payment flags
            if (!advancePaymentFlag && !milestonePaymentFlag) {
                create(transactionNumber);

                double finalGSTPercentage = gst();
                ifSgdEnable(finalGSTPercentage, transactionNumber, advancePaymentFlag, milestonePaymentFlag);
                status = invoiceCreate();
            } if (advancePaymentFlag) {
                create(transactionNumber);
                Locator advAndMilButton = page.locator(ADVANCEPAYMENT_AND_MILESTONEPAYMENT_BUTTON);
                locatorVisibleHandler(advAndMilButton);
                advAndMilButton.click();

                Locator checkbox = page.locator(ADVANCEPAYMENT_CHECKBOX);
                locatorVisibleHandler(checkbox);
                checkbox.click();

                Locator saveButton = page.locator(SAVE_BUTTON);
                locatorVisibleHandler(saveButton);
                saveButton.click();

                double finalGSTPercentage = gst();
                ifSgdEnable(finalGSTPercentage, transactionNumber, advancePaymentFlag, milestonePaymentFlag);
                status = invoiceCreate();
            } if (milestonePaymentFlag) {
                create(transactionNumber);

                Locator advAndMilButton = page.locator(ADVANCEPAYMENT_AND_MILESTONEPAYMENT_BUTTON);
                locatorVisibleHandler(advAndMilButton);
                advAndMilButton.click();

                for (int i = 1; i <= milestoneCount; i++) {
                    Locator checbox = page.locator(MILESTONEPAYMENT_CHECKBOX + i);
                    locatorVisibleHandler(checbox);
                    checbox.click();
                }

                Locator saveButton = page.locator(SAVE_BUTTON);
                locatorVisibleHandler(saveButton);
                saveButton.click();

                double finalGSTPercentage = gst();
                ifSgdEnable(finalGSTPercentage, transactionNumber, advancePaymentFlag, milestonePaymentFlag);
                status = invoiceCreate();
            }

            iLogout.performLogout();
            getInvoiceReferenceIds();
        } catch (Exception exception) {
            logger.error("Exception in Invoice Handler Function: {}", exception.getMessage());
        }
        return status;
    }

    public void create(String transactionNumber) {
        try {
            String poReferenceId = jsonNode.get("purchaseOrders").get("poReferenceId").asText();

            Locator invoiceNavigationBarLocator = page.locator(INVOICE_NAVIGATION_BAR);
            locatorVisibleHandler(invoiceNavigationBarLocator);
            invoiceNavigationBarLocator.first().click();

            Locator invoiceSelectLocator = page.locator(INVOICE_SELECT);
            locatorVisibleHandler(invoiceSelectLocator);
            invoiceSelectLocator.first().click();

            Locator selectCompanyLocator = page.locator(SELECT_COMPANY_LOCATOR);
            locatorVisibleHandler(selectCompanyLocator);
            selectCompanyLocator.click();

            String companyId = jsonNode.get("purchaseOrders").get("poTransactionId").asText().substring(0, 4);
            Locator companyLocator = page.locator(getCompanyId(companyId));
            locatorVisibleHandler(companyLocator);
            companyLocator.click();

            Locator selectTypeLocator = page.locator(SELECT_TYPE);
            locatorVisibleHandler(selectTypeLocator);
            selectTypeLocator.last().click();

            Locator searchFieldLocator1 = page.locator(SEARCH_FIELD);
            locatorVisibleHandler(searchFieldLocator1);
            searchFieldLocator1.fill("Purchase Order");

            Locator poSelectLocator = page.locator(PO_LOCATOR);
            locatorVisibleHandler(poSelectLocator);
            poSelectLocator.first().click();

            String invoiceNumber = jsonNode.get("invoices").get("poInvoiceNumber").asText();
            int randomNumber = (int) (Math.random() * 1000);
            Locator invoiceNumberLocator = page.locator(INVOICE_NUMBER_LOCATOR);
            locatorVisibleHandler(invoiceNumberLocator);
            invoiceTitle = invoiceNumber + randomNumber;
            invoiceNumberLocator.fill(invoiceTitle);

            Locator invoiceDateLocator = page.getByPlaceholder(DATE_PLACE_HOLDER);
            locatorVisibleHandler(invoiceDateLocator);
            invoiceDateLocator.last().click();

            Locator todayLocator = page.locator(TODAY);
            locatorVisibleHandler(todayLocator);
            todayLocator.last().click();

            Locator poNumberinputLocator = page.locator(PO_NUMBER_INPUT);
            locatorVisibleHandler(poNumberinputLocator);
            poNumberinputLocator.click();

            Locator searchFieldLocator2 = page.locator(SEARCH_FIELD);
            locatorVisibleHandler(searchFieldLocator2);
            searchFieldLocator2.fill(poReferenceId);

            Locator selectPoLocator = page.locator(getPoReferenceId(poReferenceId));
            locatorVisibleHandler(selectPoLocator);
            selectPoLocator.first().click();

            Locator currencyCodeLocator = page.locator(CURRENCY_CODE);
            locatorVisibleHandler(currencyCodeLocator);
            String getCurrencyCode = currencyCodeLocator.textContent();

            playwrightFactory.savePropertiesIntoJsonFile("configSettings", "currencyCode", getCurrencyCode);
        } catch (Exception exception) {
            logger.error("Exception in PO Invoice Create function: {}", exception.getMessage());
        }
    }

    public double gst() {
//TODO Used JavaScript to get the value of the input field => page.evaluate("document.getElementById('USDgstId').value");
        double finalGSTPercentage = 0;
        try {
            Locator gstPercentageLocator = page.locator(GST_LOCATOR);
            locatorVisibleHandler(gstPercentageLocator);
            String getGstPercentage = String.valueOf(gstPercentageLocator.evaluate(DOM_TRIGGER));
            String gstPercentage = getGstPercentage.replaceAll("[^\\d.]", "");
            finalGSTPercentage = Double.parseDouble(gstPercentage);
        } catch (Exception exception) {
            logger.error("Exception in PO Invoice GST function: {}", exception.getMessage());
        }
        return finalGSTPercentage;
    }

    public void ifSgdEnable(double finalGSTPercentage, String transactionNumber, boolean advancePaymentFlag, boolean milestonePaymentFlag) {
        try {
            String currencyCode = jsonNode.get("configSettings").get("currencyCode").asText();
            if (!currencyCode.equals("SGD") && finalGSTPercentage != 0 &&
                    (transactionNumber.startsWith(COMPANY_ID_2400) ||
                            transactionNumber.startsWith(COMPANY_ID_5K00) ||
                            transactionNumber.startsWith(COMPANY_ID_2U00) ||
                            transactionNumber.startsWith(COMPANY_ID_2W00))) {

                double foreignSubTotal = Double.parseDouble(
                        page.locator(FOREIGN_CURRENCY_LOCATOR).getAttribute("value").replaceAll("[^\\d.]", "")
                );
                double currencyExchangeRate = iCurrencyExchangeRate.findCurrency();
                double sgdEquivalentSubTotal = foreignSubTotal * currencyExchangeRate;

                Locator sgdInputLocator = page.locator(SGD_SUB_TOTAL_INPUT);
                locatorVisibleHandler(sgdInputLocator);
                sgdInputLocator.fill(String.valueOf(sgdEquivalentSubTotal));
                sgdInputLocator.evaluate(DOM_TRIGGER_SGD_INPUT);

                double foreignTotalGst = Double.parseDouble(
                        page.locator(FOREGIN_TOTAL_GST).getAttribute("value").replaceAll("[^\\d.]", "")
                );
                double getSGDValue = (sgdEquivalentSubTotal / foreignSubTotal) * foreignTotalGst;

                int scale;
                switch (currencyCode) {
                    case "EUR": scale = EUR; break;
                    case "USD": scale = USD; break;
                    case "INR": scale = INR; break;
                    case "CAD": scale = CAD; break;
                    default:
                        System.out.println("Not a valid currency code");
                        return;
                }
                BigDecimal roundedValue = new BigDecimal(getSGDValue).setScale(scale, RoundingMode.HALF_UP);
                Locator gstInputLocator = page.locator(SGD_TOTAL_GST_INPUT);
                locatorVisibleHandler(gstInputLocator);
                gstInputLocator.fill(String.valueOf(roundedValue));
            }
        } catch (Exception exception) {
            logger.error("Exception in PO Invoice SGD Enable function: {}", exception.getMessage());
        }
    }

    public int invoiceCreate() {
        int status = 0;
        try {
//TODO Invoice Document
            Locator invoiceDocumentButton = page.locator(DOCUMENT_ID);
            locatorVisibleHandler(invoiceDocumentButton);
            invoiceDocumentButton.first().setInputFiles(Paths.get(INVOICE_DOCUMENT_PATH));

            Locator createButtonLocator = page.locator(CREATE_BUTTON);
            locatorVisibleHandler(createButtonLocator);
            createButtonLocator.click();

            Locator acceptLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptLocator);
            acceptLocator.click();

            Locator invoiceTitleLocator = page.locator(getTitle(invoiceTitle));
            locatorVisibleHandler(invoiceTitleLocator);

            Response invoiceResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/VP/Invoices/") && response.status() == 200
                    && response.request().method().equalsIgnoreCase("GET"),
                    invoiceTitleLocator::click
            );
            saveReferenceIdFromResponse(invoiceResponse, "invoices", "invoiceReferenceId");
            saveVerifierEmail(invoiceResponse,"invoices", "verifierEmail");
            status = invoiceResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Purchase Order Invoice Create", page);
        } catch (Exception exception) {
            logger.error("Exception in Invoice Create Function: {} ", exception.getMessage());
        }
        return status;
    }
}