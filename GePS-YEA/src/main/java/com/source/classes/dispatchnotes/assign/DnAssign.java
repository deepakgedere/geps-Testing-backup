package com.source.classes.dispatchnotes.assign;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.dispatchnotes.IDnAssign;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.util.List;
import static com.constants.dispatchnotes.LDnAssign.*;
import static com.constants.inspections.LInsCreate.getTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class DnAssign implements IDnAssign {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    PlaywrightFactory playwrightFactory;
    String appUrl;

    private DnAssign(){
    }

//TODO Constructor
    public DnAssign(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, PlaywrightFactory playwrightFactory){
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.playwrightFactory = playwrightFactory;
        this.logger = LoggerUtil.getLogger(DnAssign.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int assign() {
        int status =0;
        try {
            String logisticsManagerMailId = jsonNode.get("mailIds").get("logisticsManagerEmail").asText();
            iLogin.performLogin(logisticsManagerMailId);

            Locator dnNavigationBarLocator = page.locator(DN_NAVIGATION_BAR);
            locatorVisibleHandler(dnNavigationBarLocator);
            dnNavigationBarLocator.click();

            String dnRefId = jsonNode.get("dispatchNotes").get("dispatchNoteReferenceId").asText();
            Locator dnTitle = page.locator(getTitle(dnRefId));
            locatorVisibleHandler(dnTitle);
            dnTitle.click();

            Locator dropDownLocator = page.locator(DROP_DOWN);
            locatorVisibleHandler(dropDownLocator);
            dropDownLocator.click();

            Locator assignButtonLocator = page.locator(ASSIGN_BUTTON);
            locatorVisibleHandler(assignButtonLocator);
            assignButtonLocator.click();

            Locator assignDropDownLocator = page.locator(SELECT_LOGISTICS_MANAGER_DROP_DOWN);
            locatorVisibleHandler(assignDropDownLocator);
            assignDropDownLocator.click();

            Locator searchFieldLocator = page.locator(SEARCH_FIELD);
            locatorVisibleHandler(searchFieldLocator);
            searchFieldLocator.fill(logisticsManagerMailId);

            Locator getLogisticsManagerMailId = page.locator(getLogisticsManagerId(logisticsManagerMailId));
            locatorVisibleHandler(getLogisticsManagerMailId);
            getLogisticsManagerMailId.click();

            Locator saveButtonLocator = page.locator(SAVE_BUTTON);
            locatorVisibleHandler(saveButtonLocator);

            Response dnResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/DispatchNotes/") && response.status() == 200,
                    saveButtonLocator.first()::click
            );

            status = dnResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Dispatch Notes Assign", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Dispatch Notes Assign function: {}", exception.getMessage());
        }
        return status;
    }
}