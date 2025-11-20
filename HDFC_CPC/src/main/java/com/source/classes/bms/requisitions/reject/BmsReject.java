package com.source.classes.bms.requisitions.reject;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.source.interfaces.bms.requisitions.reject.IBmsPrReject;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import static com.enums.bms.requisitions.reject.LBmsPrReject.*;
import static com.factory.PlaywrightFactory.attachScreenshotWithName;
import static com.utils.GetRowUtil.getRow;

public class BmsReject implements IBmsPrReject {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;

    private BmsReject() {
    }

//TODO Constructor
    public BmsReject(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(BmsReject.class);
    }

    public void procurementManagerLogin() {
        try {
            String procurementManagerMailId = jsonNode.get("mailIds").get("BMSCoordinator").asText();
            iLogin.performLogin(procurementManagerMailId);
        } catch (Exception exception) {
            logger.error("Exception in Procurement Manager Login function: {}", exception.getMessage());
        }
    }

    public void detailsPR(String trnId) {
        try {
            page.waitForSelector(REQUISITION_BMS.getSelector()).click();

            Locator row = getRow(page, trnId);
            Locator detailsButton = row.locator(DETAILS_PAGE.getSelector());
            detailsButton.click();
        } catch (Exception exception) {
            logger.error("Exception in Details PR function: {}", exception.getMessage());
        }
    }

    public void rejectPR() {
        try {
            String remarks = jsonNode.get("commonRemarks").get("rejectRemarks").asText();

            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Reject")).click();

            page.getByRole(AriaRole.TEXTBOX).click();

            page.getByRole(AriaRole.TEXTBOX).fill(remarks);

            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();

            page.waitForSelector(REJECTED_MESSAGE.getSelector());
            attachScreenshotWithName("PR Reject" , page);
            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Reject PR function: {}", exception.getMessage());
        }
    }
}