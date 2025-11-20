package com.source.classes.inspections.fail;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.inspections.IInsFail;
import com.source.interfaces.inspections.IInsReadyForInspection;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import org.apache.logging.log4j.Logger;
import java.util.List;
import static com.constants.inspections.LInsFail.*;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class InsFail implements IInsFail {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    IInsReadyForInspection iInsReadyForInspection;
    String appUrl;

    private InsFail() {
    }

//TODO Constructor
    public InsFail(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, IInsReadyForInspection iInsReadyForInspection) {
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.iInsReadyForInspection = iInsReadyForInspection;
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int fail() {
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

            Locator createButtonLocator = page.locator(CREATE_INSPECTION_BUTTON);
            locatorVisibleHandler(createButtonLocator);
            createButtonLocator.click();

            Locator radioButton = page.locator(PHYSICAL_INSPECTION_NOT_REQUIRED);
            locatorVisibleHandler(radioButton);
            radioButton.click();

            Locator failButtonLocator = page.locator(INSPECTION_FAIL_BUTTON);
            locatorVisibleHandler(failButtonLocator);
            failButtonLocator.click();

            Locator createInspectionButtonLocator = page.locator(CREATE_BUTTON);
            locatorVisibleHandler(createInspectionButtonLocator);
            createInspectionButtonLocator.click();

            Locator remarksLocator = page.locator(REMARKS_INPUT);
            locatorVisibleHandler(remarksLocator);
            remarksLocator.fill("Failed");

            Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptButtonLocator);

            Response osResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/buyer/OrderSchedule/") && response.status() == 200,
                    acceptButtonLocator.first()::click
            );
            status = osResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Inspection Fail", page);

            iLogout.performLogout();

            iInsReadyForInspection.readyForInspection();
        } catch (Exception exception) {
            logger.error("Exception in Inspection Fail function: {}", exception.getMessage());
        }
        return status;
    }
}