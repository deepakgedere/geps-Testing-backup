package com.source.classes.freightforwarderrequests.invite;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.dispatchnotes.IDnAssign;
import com.source.interfaces.freightforwarderrequests.IFfrInvite;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.freightforwarderrequests.LFfrInvite.*;
import static com.constants.inspections.LInsCreate.getTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;
import static com.utils.SaveToTestDataJsonUtil.saveReferenceIdFromResponse;

public class FfrInvite implements IFfrInvite {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    IDnAssign iDnAssign;
    String appUrl;

    private FfrInvite(){
    }

//TODO Constructor
    public FfrInvite(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, IDnAssign iDnAssign){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.iDnAssign = iDnAssign;
        this.logger = LoggerUtil.getLogger(FfrInvite.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int invite() {
        int status = 0;
        try {
            iDnAssign.assign();

            String logisticsManager = jsonNode.get("mailIds").get("logisticsManagerEmail").asText();
            iLogin.performLogin(logisticsManager);

            Locator dnNavigationBarLocator = page.locator(DN_NAVIGATION_BAR);
            locatorVisibleHandler(dnNavigationBarLocator);
            dnNavigationBarLocator.click();

            String dnRefId = jsonNode.get("dispatchNotes").get("dispatchNoteReferenceId").asText();
            Locator dnTitle = page.locator(getTitle(dnRefId));
            locatorVisibleHandler(dnTitle);
            dnTitle.click();

            Locator inviteFreightForwarderButtonLocator = page.locator(INVITE_VENDOR_BUTTON);
            locatorVisibleHandler(inviteFreightForwarderButtonLocator);
            inviteFreightForwarderButtonLocator.click();

            Locator dropDownLocator = page.locator(DROP_DOWN);
            locatorVisibleHandler(dropDownLocator);
            dropDownLocator.click();

            String freightVendor = jsonNode.get("freightForwarderRequests").get("freightForwarder").asText();
            Locator searchFieldLocator = page.locator(SEARCH_FIELD);
            locatorVisibleHandler(searchFieldLocator);
            searchFieldLocator.fill(freightVendor);

            Locator freightForwarderLocator = page.locator(getFreightForwarder(freightVendor));
            locatorVisibleHandler(freightForwarderLocator);
            freightForwarderLocator.click();

            Locator saveButtonLocator = page.locator(SAVE_BUTTON);
            locatorVisibleHandler(saveButtonLocator);
            saveButtonLocator.click();

            Locator emailPopUpLocator = page.locator(EMAIL_POP_UP);
            locatorVisibleHandler(emailPopUpLocator);

            Response dnResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/DispatchNotes/") && response.status() == 200,
                    emailPopUpLocator.first()::click
            );
            status = dnResponse.status();

            saveReferenceIdFromResponse(dnResponse, "freightForwarderRequests","freightForwarderRequests", "freightForwarderReferenceId");

            PlaywrightFactory.attachScreenshotWithName("Freight Forwarder Invite", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Freight Forwarder Requests Invite function: {}", exception.getMessage());
        }
        return status;
    }
}