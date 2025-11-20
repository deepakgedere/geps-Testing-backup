package com.source.classes.bms.requisitions.approve;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.source.interfaces.bms.requisitions.approve.IBmsPrApprove;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import static com.enums.bms.requisitions.approve.LBmsPrApprove.*;
import static com.factory.PlaywrightFactory.attachScreenshotWithName;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.utils.GetRowUtil.getRow;

public class BmsApprove implements IBmsPrApprove {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;

    //TODO Constructor
    public BmsApprove(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(BmsApprove.class);
    }

    public void approve(String trnId) {
        try {
            String[] approvers = {
                    jsonNode.get("mailIds").get("BMSCoordinator").asText(),
                    jsonNode.get("mailIds").get("BmsPrSecondLevelApprover").asText()
            };

            for (int i = 0; i < approvers.length; i++) {
                iLogin.performLogin(approvers[i]);

                page.waitForSelector(REQUISITION_BMS.getSelector()).click();

                Locator row = getRow(page, trnId);
                Locator detailsButton = row.locator(DETAILS_PAGE.getSelector());
                detailsButton.click();

                String remarks = jsonNode.get("commonRemarks").get("approveRemarks").asText();

                page.waitForSelector(APPROVE_PR_BUTTON.getSelector()).click();

                page.waitForSelector(REASON_APPROVE.getSelector()).fill(remarks);
                page.waitForSelector(SUBMIT_APPROVE.getSelector()).click();
                Thread.sleep(2000);

                int approverCount = i + 1;

                page.waitForSelector(approverStatus(approvers[i])).scrollIntoViewIfNeeded();

                attachScreenshotWithName("PR Approve", page);

                if (i == approvers.length - 1) {
                    page.waitForSelector(CREATE_PO.getSelector()).click();
                    Thread.sleep(4000);
                    page.waitForSelector(po_created(trnId)).scrollIntoViewIfNeeded();
                    attachScreenshotWithName("PO Created", page);
                }
                iLogout.performLogout();
            }
        } catch (Exception exception) {
            logger.error("Exception in Approve function: {}", exception.getMessage());
        }
    }
}