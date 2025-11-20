package com.source.classes.workorder.edit;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.workorders.IWoEdit;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import static com.constants.dispatchnotes.LDnCreate.ROWS;
import static com.constants.dispatchnotes.LDnCreate.THIRD_CHILD_ELEMENT;
import static com.constants.inspections.LInsCreate.getTitle;
import static com.constants.workorders.LWoEdit.*;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class WoEdit implements IWoEdit {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    String appUrl;

    private WoEdit(){
    }

//TODO Constructor
    public WoEdit(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout){
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(WoEdit.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int edit() {
        int status = 0;
        try {
            String logisticsManager = jsonNode.get("mailIds").get("buyerEmail").asText();
            iLogin.performLogin(logisticsManager);

            Locator woNavigationBarLocator = page.locator(WO_NAVIGATION_BAR);
            locatorVisibleHandler(woNavigationBarLocator);
            woNavigationBarLocator.click();

            String woRefId = jsonNode.get("workOrders").get("workOrderReferenceId").asText();
            Locator title = page.locator(getTitle(woRefId));
            locatorVisibleHandler(title);
            title.click();

            Locator editButtonLocator = page.locator(EDIT_BUTTON);
            locatorVisibleHandler(editButtonLocator);
            editButtonLocator.click();

            Locator updateWorkOrderButtonLocator = page.locator(UPDATE_BUTTON);
            locatorVisibleHandler(updateWorkOrderButtonLocator);
            updateWorkOrderButtonLocator.click();

            Locator yesButtonLocator = page.locator(YES_BUTTON);
            locatorVisibleHandler(yesButtonLocator);

            Response editResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/WorkOrder/Listing") && response.status() == 200,
                    yesButtonLocator.first()::click
            );

            String poNumber = jsonNode.get("purchaseOrders").get("poReferenceId").asText();
//TODO Locate the row containing the dynamic poReferenceId and click the <a> tag
            Locator rows = page.locator(ROWS);
            int rowCount = rows.count();
            for (int i = 0; i < rowCount; i++) {
                Locator row = rows.nth(i);
                String referenceText = row.locator(THIRD_CHILD_ELEMENT).innerText();
                if (referenceText.contains(poNumber)) {
                    Response woResponse = page.waitForResponse(
                            response -> response.url().startsWith(appUrl + "/api/WorkOrder/") && response.status() == 200,
                            row.locator("a").first()::click
                    );
                    status = woResponse.status();
                    break;
                }
            }

            PlaywrightFactory.attachScreenshotWithName("Work Order Edit", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Work Order Create function: {}", exception.getMessage());
        }
        return status;
    }
}