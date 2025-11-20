package com.source.classes.orderschedules.reject;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.orderschedules.IOsReject;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.orderschedules.LOsReject.*;
import static com.utils.GetTitleUtil.getTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class OsReject implements IOsReject {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    String appUrl;

    private OsReject(){
    }

//TODO Constructor
    public OsReject(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(OsReject.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int reject(String type, String purchaseType){
        int status =0;
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

            Locator rejectButtonLocator = page.locator(REJECT_BUTTON);
            locatorVisibleHandler(rejectButtonLocator);
            rejectButtonLocator.click();

            Locator remarksInoutLocator = page.locator(REMARKS_INPUT);
            locatorVisibleHandler(remarksInoutLocator);
            remarksInoutLocator.fill("Rejected");

            Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptButtonLocator);

            Response rejectResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/PurchaseOrders/GetOrderSchedules/") && response.status() == 200,
                        acceptButtonLocator::click
            );
            status = rejectResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Order Schedule Reject", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in OS Reject Function: {}", exception.getMessage());
        }
        return status;
    }
}