package com.source.classes.orderschedules.create;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.orderschedules.IOsCreate;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import static com.constants.dispatchnotes.LDnCreate.ROWS;
import static com.constants.dispatchnotes.LDnCreate.THIRD_CHILD_ELEMENT;
import static com.constants.orderschedules.LOsCreate.*;
import static com.utils.GetTitleUtil.getTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;
import static com.utils.SaveToTestDataJsonUtil.saveReferenceIdFromResponse;

public class OsCreate implements IOsCreate {

    Logger logger;
    JsonNode jsonNode;
    PlaywrightFactory playwrightFactory;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    String appUrl;

    private OsCreate() {
    }

//TODO Constructor
    public OsCreate(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, PlaywrightFactory playwrightFactory) {
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.playwrightFactory = playwrightFactory;
        this.logger = LoggerUtil.getLogger(OsCreate.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int create(String type, String purchaseType) {
        int status =0;
        try {
            String vendorMailId = jsonNode.get("mailIds").get("vendorEmail").asText();
            iLogin.performLogin(vendorMailId);

            Locator poNavigationBarLocator = page.locator(PO_NAVIGATION_BAR);
            locatorVisibleHandler(poNavigationBarLocator);
            poNavigationBarLocator.click();

            String title = getTransactionTitle(type, purchaseType);
            Locator titleLocator = page.locator(getTitle(title));
            locatorVisibleHandler(titleLocator);
            titleLocator.first().click();

            Locator createOsButtonLocator = page.locator(CREATE_OS_BUTTON);
            locatorVisibleHandler(createOsButtonLocator);
            createOsButtonLocator.click();

            Locator orderScheduleDate = page.locator(SCHEDULE_DATE);
            locatorVisibleHandler(orderScheduleDate);
            orderScheduleDate.last().click();

            Locator todayLocator = page.locator(TODAY);
            locatorVisibleHandler(todayLocator);
            todayLocator.first().click();

            Locator createButtonLocator = page.locator(CREATE_BUTTON);
            locatorVisibleHandler(createButtonLocator);
            createButtonLocator.click();

            Locator acceptLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptLocator);

            Response createResponse = page.waitForResponse(
                    response -> response.url().equals(appUrl + "/api/VP/OrderSchedules/Listing") && response.status() == 200,
                    acceptLocator.first()::click
            );

            String poNumber = jsonNode.get("purchaseOrders").get("poReferenceId").asText();
//TODO Locate the row containing the dynamic poReferenceId and click the <a> tag
            Locator rows = page.locator(ROWS);
            int rowCount = rows.count();
            for (int i = 0; i < rowCount; i++) {
                Locator row = rows.nth(i);
                String referenceText = row.locator(THIRD_CHILD_ELEMENT).innerText();
                if (referenceText.contains(poNumber)) {
                    Response osResponse = page.waitForResponse(
                            response -> response.url().startsWith(appUrl + "/api/VP/OrderSchedules/") && response.status() == 200,
                            row.locator("a").first()::click
                    );
                    saveReferenceIdFromResponse(osResponse, "orderSchedules", "orderScheduleReferenceId");
                    status = osResponse.status();
                    break;
                }
            }

            PlaywrightFactory.attachScreenshotWithName("Order Schedule Create", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in OS Create Function: {}", exception.getMessage());
        }
        return status;
    }
}