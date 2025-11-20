package com.source.classes.workorder.trackerstatus;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.workorders.IWoTrackerStatus;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.inspections.LInsCreate.getTitle;
import static com.constants.workorders.LWoTrackerStatus.*;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class WoTrackerStatus implements IWoTrackerStatus {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    PlaywrightFactory playwrightFactory;
    String appUrl;

    private WoTrackerStatus(){
    }

//TODO Constructor
    public WoTrackerStatus(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, PlaywrightFactory playwrightFactory){
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.playwrightFactory = playwrightFactory;
        this.logger = LoggerUtil.getLogger(WoTrackerStatus.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int trackerStatus() {
        int status = 0;
        try {
            String vendorMailId = jsonNode.get("mailIds").get("vendorEmail").asText();
            iLogin.performLogin(vendorMailId);

            Locator workOrderNavigationBarLocator = page.locator(WO_NAVIGATION_BAR);
            locatorVisibleHandler(workOrderNavigationBarLocator);
            workOrderNavigationBarLocator.click();

            String woRefId = jsonNode.get("workOrders").get("workOrderReferenceId").asText();
            Locator title = page.locator(getTitle(woRefId));
            locatorVisibleHandler(title);
            title.click();

            for (String trackerStatus : STATUSES) {
                Locator datePickerLocator = page.locator(DATE_PICKER);
                locatorVisibleHandler(datePickerLocator);
                datePickerLocator.click();

                Locator todayLocator = page.locator(TODAY);
                locatorVisibleHandler(todayLocator);
                todayLocator.first().click();

                Locator statusLocator = page.locator(STATUS_CONTAINER);
                locatorVisibleHandler(statusLocator);
                statusLocator.last().click();

                Locator statusSelectLocator = page.locator(getStatus(trackerStatus));
                locatorVisibleHandler(statusSelectLocator);
                statusSelectLocator.click();

                Locator submitButtonLocator = page.locator(SUBMIT_BUTTON);
                locatorVisibleHandler(submitButtonLocator);
                submitButtonLocator.click();

                Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
                locatorVisibleHandler(acceptButtonLocator);

                //TODO Check for last iteration
                if (!trackerStatus.equals(STATUSES[STATUSES.length - 1])) {
                    acceptButtonLocator.click();
                } else {
                    Response woResponse = page.waitForResponse(
                            response -> response.url().startsWith(appUrl + "/api/VP/WorkOrder/") && response.status() == 200,
                            acceptButtonLocator::click
                    );

                    status = woResponse.status();
                    break;
                }
            }

            PlaywrightFactory.attachScreenshotWithName("Work Order Tracker Status", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Work Order Tracker Status: {}", exception.getMessage());
        }
        return status;
    }
}