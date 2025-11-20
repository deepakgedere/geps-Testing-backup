package com.source.classes.dispatchnotes.dnreturn;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.dispatchnotes.IDnEdit;
import com.source.interfaces.dispatchnotes.IDnReturn;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.util.List;
import static com.constants.dispatchnotes.LDnReturn.*;
import static com.constants.inspections.LInsCreate.getTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class DnReturn implements IDnReturn {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    IDnEdit iDnEdit;
    String appUrl;

    private DnReturn(){
    }

//TODO Constructor
    public DnReturn(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, IDnEdit iDnEdit){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.iDnEdit = iDnEdit;
        this.logger = LoggerUtil.getLogger(DnReturn.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int dnReturn() {
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

            Locator returnButtonLocator = page.locator(RETURN_BUTTON);
            locatorVisibleHandler(returnButtonLocator);
            returnButtonLocator.click();

            Locator remarksLocator = page.locator(REMARKS_FIELD);
            locatorVisibleHandler(remarksLocator);
            remarksLocator.fill("Returned");

            Locator acceptLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptLocator);

            Response dnResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/DispatchNotes/") && response.status() == 200,
                    acceptLocator.first()::click
            );
            status = dnResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Dispatch Notes Return", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Dispatch Notes Return function: {}", exception.getMessage());
        }
        return status;
    }
}