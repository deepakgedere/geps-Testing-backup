package com.source.classes.purchaseorderrequests.edit;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.purchaseorderrequests.IPorEdit;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.purchaseorderrequests.LPorEdit.*;
import static com.utils.GetTitleUtil.getTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class PorEdit implements IPorEdit {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    String appUrl;

    private PorEdit(){
    }

//TODO Constructor
    public PorEdit(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(PorEdit.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int porEdit(String type, String purchaseType) {
        int status = 0;
        try {
            String buyerMailId = jsonNode.get("mailIds").get("buyerEmail").asText();

            iLogin.performLogin(buyerMailId);

            Locator porNavigationBarLocator = page.locator(POR_NAVIGATION_BAR);
            locatorVisibleHandler(porNavigationBarLocator);
            porNavigationBarLocator.click();

            String title = getTransactionTitle(type, purchaseType);
            Locator titleLocator = page.locator(getTitle(title));
            locatorVisibleHandler(titleLocator);
            titleLocator.first().click();

            Locator editButtonLocator = page.locator(EDIT_BUTTON);
            locatorVisibleHandler(editButtonLocator);
            editButtonLocator.click();
            page.waitForLoadState(LoadState.NETWORKIDLE);

            Locator updateButtonLocator = page.locator(UPDATE_BUTTON);
            locatorVisibleHandler(updateButtonLocator);
            updateButtonLocator.click();

            page.waitForLoadState(LoadState.NETWORKIDLE);

            Locator remarksInputLocator = page.locator(REMARKS_INPUT);
            locatorVisibleHandler(remarksInputLocator);
            remarksInputLocator.fill("Updated");

            Locator acceptLocator = page.locator(YES);
            locatorVisibleHandler(acceptLocator);

            String porType;
            if(type.equalsIgnoreCase("sales")){
                porType = "/api/PurchaseOrderRequestsSales/";
            } else if(type.equalsIgnoreCase("ps")){
                porType = "/api/PurchaseOrderRequests/";
            } else {
                porType = "/api/PurchaseOrderRequestsNonPOC/";
            }

            Response editResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + porType) && response.status() == 200,
                    acceptLocator::click
            );
            status = editResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Purchase Order Request Edit", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in POR Edit function: {}", exception.getMessage());
        }
        return status;
    }
}