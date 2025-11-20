package com.source.classes.dispatchnotes.markAsComplete;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.source.interfaces.dispatchnotes.IDnCancel;
import com.source.interfaces.dispatchnotes.IDnCreate;
import com.source.interfaces.dispatchnotes.IDnMarkAsComplete;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import static com.constants.dispatchnotes.LDnMarkAsComplete.*;
import static com.constants.inspections.LInsCreate.getTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class DnMarkAsComplete implements IDnMarkAsComplete {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    IDnCreate iDnCreate;
    String appUrl;

    private DnMarkAsComplete(){
    }

//TODO Constructor
    public DnMarkAsComplete(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, IDnCreate iDnCreate){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.iDnCreate = iDnCreate;
        this.logger = LoggerUtil.getLogger(DnMarkAsComplete.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int markAsComplete(String type, String purchaseType) {
        int status =0;
        try {
            String logisticsManagerMailId = jsonNode.get("mailIds").get("buyerEmail").asText();
            iLogin.performLogin(logisticsManagerMailId);

            Locator dnNavigationBarLocator = page.locator(DN_NAVIGATION_BAR);
            locatorVisibleHandler(dnNavigationBarLocator);
            dnNavigationBarLocator.click();

            String dnRefId = jsonNode.get("dispatchNotes").get("dispatchNoteReferenceId").asText();
            Locator dnTitle = page.locator(getTitle(dnRefId));
            locatorVisibleHandler(dnTitle);
            dnTitle.click();

            Locator cancelButtonLocator = page.locator(MARK_AS_COMPLETE_BUTTON);
            locatorVisibleHandler(cancelButtonLocator);
            cancelButtonLocator.click();

            Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptButtonLocator);

            Response dnResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/DispatchNotes/") && response.status() == 200,
                    acceptButtonLocator.first()::click
            );
            status = dnResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Dispatch Notes Mark As Complete", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Dispatch Notes Cancel function: {}", exception.getMessage());
        }
        return status;
    }
}