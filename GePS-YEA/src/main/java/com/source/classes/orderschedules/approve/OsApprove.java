package com.source.classes.orderschedules.approve;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.RequestOptions;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.orderschedules.IOsApprove;
import com.utils.LocatorHandlerUtil;
import com.utils.LoggerUtil;
import com.utils.rpa.orderacknowledgement.OA_Flow;
import org.apache.logging.log4j.Logger;
import static com.constants.orderschedules.LOsApprove.*;
import static com.constants.orderschedules.LOsReject.getTitle;
import static com.utils.GetTitleUtil.getTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class OsApprove implements IOsApprove {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    ObjectMapper objectMapper;
    OA_Flow oaFlow;
    PlaywrightFactory playwrightFactory;
    String appUrl;

    private OsApprove(){
    }

//TODO Constructor
    public OsApprove(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, ObjectMapper objectMapper, OA_Flow oaFlow, PlaywrightFactory playwrightFactory){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.objectMapper = objectMapper;
        this.oaFlow = oaFlow;
        this.playwrightFactory = playwrightFactory;
        this.logger = LoggerUtil.getLogger(OsApprove.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int approve(String type, String purchaseType){
        int status = 0;
        try {
            String buyerMailId = jsonNode.get("mailIds").get("buyerEmail").asText();
            iLogin.performLogin(buyerMailId);

            Locator poNavigationBarLocator = page.locator(PO_NAVIGATION_BAR);
            locatorVisibleHandler(poNavigationBarLocator);
            poNavigationBarLocator.click();

            String title = getTransactionTitle(type, purchaseType);
            Locator titleLocator = page.locator(getTitle(title));
            locatorVisibleHandler(titleLocator);
            titleLocator.first().click();

            Locator viewOrderScheduleButtonLocator = page.locator(VIEW_ORDER_SCHEDULE__BUTTON);
            locatorVisibleHandler(viewOrderScheduleButtonLocator);
            viewOrderScheduleButtonLocator.click();

            Locator approveButtonLocator = page.locator(APPROVE_BUTTON);
            locatorVisibleHandler(approveButtonLocator);
            approveButtonLocator.click();

            Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptButtonLocator);

            Response approveResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/PurchaseOrders/GetOrderSchedules/") && response.status() == 200,
                    acceptButtonLocator.first()::click
            );
            status = approveResponse.status();

            String appUrl = jsonNode.get("configSettings").get("appUrl").asText();

            String url = page.url();
            String[] urlArray = url.split("=");
            String getUid = urlArray[1];

            APIResponse apiResponse = page.request().fetch(appUrl + "/api/PurchaseOrders/" + getUid, RequestOptions.create());
            JsonNode jsonNode = objectMapper.readTree(apiResponse.body());
            String integrationStatus = jsonNode.get("integrationStatus").asText();
            String poId = jsonNode.get("id").asText();
            playwrightFactory.savePropertiesIntoJsonFile("purchaseOrderRequests", "poId", poId);

            if(integrationStatus.equalsIgnoreCase("AckUpdatingInYGS")) {
                oaFlow.oaFlow();
            } else {
                throw new RuntimeException("PO is not in ACK Updating In HOPES status");
            }

            PlaywrightFactory.attachScreenshotWithName("Order Schedule Approve", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in OS Approve function: {}", exception.getMessage());
        }
        return status;
    }
}