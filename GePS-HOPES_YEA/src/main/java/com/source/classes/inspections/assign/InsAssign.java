package com.source.classes.inspections.assign;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.inspections.IInsAssign;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.util.List;
import static com.constants.inspections.LInsAssign.*;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class InsAssign implements IInsAssign {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    String appUrl;

    private InsAssign() {
    }

//TODO Constructor
    public InsAssign(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout) {
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(InsAssign.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int assign() {
        int status =0;
        try {
            String mailId = jsonNode.get("mailIds").get("requesterEmail").asText();
            iLogin.performLogin(mailId);

            Locator osNavigationBarLocator = page.locator(OS_NAVIGATION_BAR);
            locatorVisibleHandler(osNavigationBarLocator);
            osNavigationBarLocator.click();

            String osRefId = jsonNode.get("orderSchedules").get("orderScheduleReferenceId").asText();
            Locator osTitle = page.locator(getTitle(osRefId));
            locatorVisibleHandler(osTitle);
            osTitle.click();

            Locator assignButtonLocator = page.locator(ASSIGN_INSPECTOR_BUTTON);
            locatorVisibleHandler(assignButtonLocator);
            assignButtonLocator.click();

            Locator dropDownLocator = page.locator(SELECT_INSPECTOR_DROP_DOWN);
            locatorVisibleHandler(dropDownLocator);
            dropDownLocator.click();

            Locator searchFieldLocator = page.locator(SEARCH_FIELD);
            locatorVisibleHandler(searchFieldLocator);
            searchFieldLocator.fill(mailId);

            Locator requesterMailIdLocator = page.locator(getRequestorId(mailId));
            locatorVisibleHandler(requesterMailIdLocator);
            requesterMailIdLocator.first().click();

            Locator saveInspectorButtonLocator = page.locator(SAVE_INSPECTOR);
            locatorVisibleHandler(saveInspectorButtonLocator);

            Response assignResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/buyer/OrderSchedule/") && response.status() == 200,
                    saveInspectorButtonLocator.first()::click
            );
            status = assignResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Inspection Assign", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Inspection Assign function: {}", exception.getMessage());
        }
        return status;
    }
}