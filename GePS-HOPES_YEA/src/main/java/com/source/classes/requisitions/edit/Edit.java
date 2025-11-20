package com.source.classes.requisitions.edit;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.RequestOptions;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.requisitions.IPrEdit;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.requisitions.LPrEdit.*;
import static com.utils.GetTitleUtil.getTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class Edit implements IPrEdit {

    Logger logger;
    private ILogin iLogin;
    private ILogout iLogout;
    private JsonNode jsonNode;
    private Page page;
    private String appUrl;

    private Edit(){
    }

//TODO Constructor
    public Edit(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(Edit.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int edit(String type, String purchaseType) {
        int status = 0;
        try {
            String requesterEmailId = jsonNode.get("mailIds").get("requesterEmail").asText();

            iLogin.performLogin(requesterEmailId);

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

            Locator yesButtonLocator = page.locator(YES);
            locatorVisibleHandler(yesButtonLocator);

            String reqType;
            if (type.equalsIgnoreCase("sales")) {
                reqType = "/api/RequisitionsSales/";
            } else if (type.equalsIgnoreCase("ps")) {
                reqType = "/api/Requisitions/";
            } else {
                reqType = "/api/RequisitionsNonPoc/";
            }

            Response updateResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + reqType) && response.status() == 200,
                    yesButtonLocator::click
            );

            status = updateResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Requisition Edit", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Requisition Edit Function: {}", exception.getMessage());
        }
        return status;
    }
}