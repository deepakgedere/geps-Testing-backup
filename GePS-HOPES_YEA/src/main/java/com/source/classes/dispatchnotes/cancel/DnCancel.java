package com.source.classes.dispatchnotes.cancel;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.dispatchnotes.IDnCancel;
import com.source.interfaces.dispatchnotes.IDnCreate;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.util.List;
import static com.constants.dispatchnotes.LDnCancel.*;
import static com.constants.inspections.LInsCreate.getTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class DnCancel implements IDnCancel {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    IDnCreate iDnCreate;
    String appUrl;

    private DnCancel(){
    }

//TODO Constructor
    public DnCancel(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, IDnCreate iDnCreate){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.iDnCreate = iDnCreate;
        this.logger = LoggerUtil.getLogger(DnCancel.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int cancel(String type, String purchaseType) {
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

            Locator cancelButtonLocator = page.locator(CANCEL_BUTTON);
            locatorVisibleHandler(cancelButtonLocator);
            cancelButtonLocator.click();

            Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptButtonLocator);

            Response dnResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/DispatchNotes/") && response.status() == 200,
                    acceptButtonLocator.first()::click
            );
            status = dnResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Dispatch Notes Cancel", page);

            iLogout.performLogout();

            iDnCreate.create(type, purchaseType);
        } catch (Exception exception) {
            logger.error("Exception in Dispatch Notes Cancel function: {}", exception.getMessage());
        }
        return status;
    }
}