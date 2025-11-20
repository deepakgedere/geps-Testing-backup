package com.source.classes.inspections.readyforinspection;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.inspections.IInsReadyForInspection;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.util.List;
import static com.constants.inspections.LInsCreate.ACCEPT_BUTTON;
import static com.constants.inspections.LInsReadyForInspection.*;
import static com.constants.orderschedules.LOsEdit.getTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class InsReadyForInspection implements IInsReadyForInspection {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    String appUrl;

    private InsReadyForInspection(){
    }

//TODO Constructor
    public InsReadyForInspection(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(InsReadyForInspection.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int readyForInspection(String type, String purchaseType){
        int status =0;
        try {
            String vendorMailId;
            if (purchaseType.equalsIgnoreCase("punchout")) {
                vendorMailId = "punchoutVendorEmail";
            } else {
                vendorMailId = jsonNode.get("mailIds").get("vendorEmail").asText();
            }
            iLogin.performLogin(vendorMailId);

            page.waitForLoadState(LoadState.NETWORKIDLE);

            Locator vendorSelection = page.locator("#vendor-container");
            if(vendorSelection.isVisible()){
                if(purchaseType.equalsIgnoreCase("punchout"))
                    page.locator("//tr[.//td[contains(text(),'" + jsonNode.get("requisition").get("punchoutVendorName").asText() + "')]]//input[@type='radio']").click();
                else
                    page.locator("//tr[.//td[contains(text(),'" + jsonNode.get("requisition").get("vendorName").asText() + "')]]//input[@type='radio']").click();
                page.locator("#glbBtnChangeVendor").click();
            }

            Locator osNavigationBarLocator = page.locator(OS_NAVIGATION_BAR);
            locatorVisibleHandler(osNavigationBarLocator);
            osNavigationBarLocator.click();

            String osRefId = jsonNode.get("orderSchedules").get("orderScheduleReferenceId").asText();
            Locator osTitle = page.locator(getTitle(osRefId));
            locatorVisibleHandler(osTitle);
            osTitle.click();

            Locator readyForInspectionButtonLocator = page.locator(READY_FOR_INSPECTION_BUTTON);
            locatorVisibleHandler(readyForInspectionButtonLocator);
            readyForInspectionButtonLocator.click();

            Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptButtonLocator);

            Response osResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/VP/OrderSchedules/") && response.status() == 200,
                    acceptButtonLocator.first()::click
            );
            status = osResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Ready For Inspection", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Ready for Inspection function: {}", exception.getMessage());
        }
        return status;
    }
}