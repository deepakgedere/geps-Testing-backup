package com.source.classes.invoices.woinvoice.create;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.currencyexchangerate.ICurrencyExchangeRate;
import com.source.interfaces.invoices.woinvoices.IWoInvCreate;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.util.List;

import static com.constants.invoices.woinvoice.LInvCreate.*;
import static com.constants.invoices.woinvoice.LInvCreate.ACCEPT_BUTTON;
import static com.constants.invoices.woinvoice.LInvCreate.CREATE_BUTTON;
import static com.constants.invoices.woinvoice.LInvCreate.DOCUMENT_ID;
import static com.constants.invoices.woinvoice.LInvCreate.DOM_TRIGGER_SGD_INPUT;
import static com.constants.invoices.woinvoice.LInvCreate.FOREGIN_TOTAL_GST;
import static com.constants.invoices.woinvoice.LInvCreate.INVOICE_DOCUMENT_PATH;
import static com.constants.invoices.woinvoice.LInvCreate.SGD_SUB_TOTAL_INPUT;
import static com.constants.invoices.woinvoice.LInvCreate.SGD_TOTAL_GST_INPUT;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;
import static com.utils.SaveToTestDataJsonUtil.saveReferenceIdFromResponse;
import static com.utils.SaveToTestDataJsonUtil.saveVerifierEmail;


public class WoInvCreate implements IWoInvCreate {

    Logger logger;
    PlaywrightFactory playwrightFactory;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    ICurrencyExchangeRate iCurrencyExchangeRate;
    String appUrl;

    int EUR = 4;
    int USD = 3;
    int INR = 2;
    int CAD = 2;

    String invoiceTitle = "";

    private WoInvCreate() {
    }

//TODO Constructor
    public WoInvCreate(PlaywrightFactory playwrightFactory, ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, ICurrencyExchangeRate iCurrencyExchangeRate) {
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.iCurrencyExchangeRate = iCurrencyExchangeRate;
        this.playwrightFactory = playwrightFactory;
        this.logger = LoggerUtil.getLogger(WoInvCreate.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public void create() {
        try {
            String vendorMailId = jsonNode.get("mailIds").get("vendorEmail").asText();
            iLogin.performLogin(vendorMailId);

            Locator invoiceNavigationBarLocator = page.locator(INVOICE_NAVIGATION_BAR);
            locatorVisibleHandler(invoiceNavigationBarLocator);
            invoiceNavigationBarLocator.click();

            Locator invoiceSelectLocator = page.locator(INVOICE_SELECT);
            locatorVisibleHandler(invoiceSelectLocator);
            invoiceSelectLocator.first().click();

            Locator selectCompanyLocator = page.locator(SELECT_COMPANY_LOCATOR);
            locatorVisibleHandler(selectCompanyLocator);
            selectCompanyLocator.click();

            String woReferenceId = jsonNode.get("workOrders").get("workOrderReferenceId").asText();
            String companyId = woReferenceId.substring(0,4);

            Locator companyLocator = page.locator(getCompanyId(companyId));
            locatorVisibleHandler(companyLocator);
            companyLocator.click();

            Locator selectTypeLocator = page.locator(SELECT_TYPE);
            locatorVisibleHandler(selectTypeLocator);
            selectTypeLocator.last().click();

            Locator searchFieldLocator1 = page.locator(SEARCH_FIELD);
            locatorVisibleHandler(searchFieldLocator1);
            searchFieldLocator1.fill("Work Order");

            Locator poSelectLocator = page.locator(WO_LOCATOR);
            locatorVisibleHandler(poSelectLocator);
            poSelectLocator.first().click();

            String invoiceNumber = jsonNode.get("invoices").get("woInvoiceNumber").asText();
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

            Locator woNumberinputLocator = page.locator(WO_NUMBER_INPUT);
            locatorVisibleHandler(woNumberinputLocator);
            woNumberinputLocator.click();

            Locator searchFieldLocator2 = page.locator(SEARCH_FIELD);
            locatorVisibleHandler(searchFieldLocator2);
            searchFieldLocator2.fill(woReferenceId);

            Locator selectPoLocator = page.locator(getWoReferenceId(woReferenceId));
            locatorVisibleHandler(selectPoLocator);
            selectPoLocator.first().click();

            Locator currencyCodeLocator = page.locator(CURRENCY_CODE);
            locatorVisibleHandler(currencyCodeLocator);
            String getCurrencyCode = currencyCodeLocator.textContent();

            Locator rate = page.locator(RATE_INPUT);
            locatorVisibleHandler(rate);
            rate.fill(jsonNode.get("invoices").get("woInvocieRate").asText());
            rate.evaluate(DOM_TRIGGER_RATE_INPUT);

            playwrightFactory.savePropertiesIntoJsonFile("configSettings", "currencyCode", getCurrencyCode);
        } catch (Exception exception) {
            logger.error("Exception in WO Invoice Create function: {}", exception.getMessage());
        }
    }

    public double gst() {
//TODO Used JavaScript to get the value of the input field => page.evaluate("document.getElementById('USDgstId').value");
        double finalGSTPercentage = 0;
        try {
            Locator gstPercentageLocator = page.locator(GST_LOCATOR);
            locatorVisibleHandler(gstPercentageLocator);
            String gst = jsonNode.get("workOrders").get("gstPercentage").asText();
            gstPercentageLocator.fill(gst);
            gstPercentageLocator.evaluate(DOM_TRIGGER_GST_INPUT);
            String gstPercentage = gst.replaceAll("[^\\d.]", "");
            finalGSTPercentage = Double.parseDouble(gstPercentage);
        } catch (Exception exception) {
            logger.error("Exception in WO GST function: {}", exception.getMessage());
        }
        return finalGSTPercentage;
    }

    public void ifSgdEnable(double finalGSTPercentage) {
        try {
            String woReferenceId = jsonNode.get("workOrders").get("workOrderReferenceId").asText();
            String currencyCode = jsonNode.get("configSettings").get("currencyCode").asText();
            double getSGDValue = 0;
            if (!currencyCode.equals("SGD") && finalGSTPercentage != 0 &&
                    (woReferenceId.startsWith(COMPANY_ID_2400) ||
                            woReferenceId.startsWith(COMPANY_ID_5K00) ||
                            woReferenceId.startsWith(COMPANY_ID_2U00) ||
                            woReferenceId.startsWith(COMPANY_ID_2W00))) {

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
                getSGDValue = (sgdEquivalentSubTotal / foreignSubTotal) * foreignTotalGst;
            }
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
        } catch (Exception exception) {
            logger.error("Exception in PO Invoice SGD Enable function: {}", exception.getMessage());
        }
    }

    public int invoiceCreate() {
        int status = 0;
        try {
            status = 0;
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

            Locator woInvoiceTitle = page.locator(getTitle(invoiceTitle));
            locatorVisibleHandler(woInvoiceTitle);
            Response invoiceResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/VP/Invoices/") && response.status() == 200
                    && response.request().method().equalsIgnoreCase("GET"),
                    woInvoiceTitle::click
            );
            saveReferenceIdFromResponse(invoiceResponse, "invoices", "workOrderInvoiceReferenceId");
            saveVerifierEmail(invoiceResponse,"invoices", "verifierEmail");

            status = invoiceResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Purchase Order Invoice Create", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Invoice Create Function: {} ", exception.getMessage());
        }
        return status;
    }
}