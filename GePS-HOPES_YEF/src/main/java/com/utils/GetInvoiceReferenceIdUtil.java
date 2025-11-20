package com.utils;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import static com.constants.purchaseorderrequests.LPorCreate.getTitle;
import static com.constants.purchaseorders.LPoSendForVendor.PO_NAVIGATION_BAR;
import static com.utils.GetTitleUtil.getTransactionTitle;

public class GetInvoiceReferenceIdUtil {

    static Logger logger;
    static PlaywrightFactory playwrightFactory;
    static JsonNode jsonNode;
    static Page page;
    static ILogin iLogin;
    static ILogout iLogout;
    static String appUrl;
    static Playwright playwright;
    static ObjectMapper objectMapper;

    private GetInvoiceReferenceIdUtil() {
    }

//TODO Constructor
    public GetInvoiceReferenceIdUtil(Playwright playwright, PlaywrightFactory playwrightFactory, Page page, ILogin iLogin, JsonNode jsonNode, ObjectMapper objectMapper, ILogout iLogout) {
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.playwright = playwright;
        this.playwrightFactory = playwrightFactory;
        this.logger = LoggerUtil.getLogger(GetInvoiceReferenceIdUtil.class);
        this.objectMapper = objectMapper;
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public static void getInvoiceReferenceIds() {
        try {
            String buyerMailId = jsonNode.get("mailIds").get("buyerEmail").asText();
            iLogin.performLogin(buyerMailId);

            Locator poNavigationBarLocator = page.locator(PO_NAVIGATION_BAR);
            poNavigationBarLocator.click();

            String transactionNumber = jsonNode.get("purchaseOrders").get("poTransactionId").asText();
            String poTitle = jsonNode.get("purchaseOrders").get("poTitle").asText();
            String purchaseOrderUid = jsonNode.get("purchaseOrders").get("purchaseOrderUid").asText();

            Locator titleLocator = page.locator(getTitle(poTitle));
            titleLocator.first().click();

            page.waitForLoadState(LoadState.NETWORKIDLE);

            String title = page.locator("#title").textContent().trim().toLowerCase();

            page.locator("#transactionIdLink").click();

            APIResponse apiResponse = page.request().fetch(appUrl + "/api/Requisitions/GetTransactionAllDetails?uid=" + purchaseOrderUid + "&&transactionId=" + transactionNumber);
            JsonNode jsonNode = objectMapper.readTree(apiResponse.body());

            page.locator("//div[@id=\"modalTransactionDetails\"]//button[@aria-label=\"Close\"]").click();

            List<InvoicePOJOUtil> invTxnList = new ArrayList<>();
            for (JsonNode node : jsonNode) {
                if (node.has("invTxnDetails")) {
                    JsonNode inv = node.get("invTxnDetails");
                    String status = inv.get("status").asText();
                    if(status.equalsIgnoreCase("Cancelled")) {
                        continue;
                    }
                    String referenceId = inv.get("referenceId").asText();
                    String transactionId = inv.get("transactionId").asText();
                    String uid = inv.get("uid").asText();
                    String type ="";
                    if(title.contains("punchout"))
                        type = "punchout";
                    invTxnList.add(new InvoicePOJOUtil(referenceId, transactionId, uid, type));
                }
            }

            playwrightFactory.saveInvoiceListIntoJsonFile("invoices", "invoiceTransactionDetails", invTxnList);

            page.waitForLoadState(LoadState.NETWORKIDLE);

            iLogout.performLogout();
        } catch(Exception exception){
            logger.error("Exception in Get Invoice Reference Id function: {}", exception.getMessage());
        }
    }
}