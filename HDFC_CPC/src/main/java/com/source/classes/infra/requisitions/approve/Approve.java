package com.source.classes.infra.requisitions.approve;
import com.enums.infra.requisitions.approve.LPrApprove;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.source.interfaces.infra.requisitions.approve.IPrApprove;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import static com.enums.infra.requisitions.approve.LPrApprove.*;
import static com.factory.PlaywrightFactory.attachScreenshotWithName;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import static com.utils.GetRowUtil.getRow;

public class Approve implements IPrApprove {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;

//TODO Constructor
    public Approve(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(Approve.class);
    }

    public void approve(String trnId) {
        try {
            String[] approvers = {
                    jsonNode.get("mailIds").get("PrApproveFirstLevelProcurementManagerWest").asText(),
                    jsonNode.get("mailIds").get("PrApproveSecondLevelBusinessHead").asText()
            };

            for (int i = 0; i < approvers.length; i++) {
                iLogin.performLogin(approvers[i]);

                page.waitForSelector(REQUISITION_INFRA_LIST_PAGE_APPROVE.getSelector()).click();

                Locator row = getRow(page, trnId);
                Locator detailsButton = row.locator(LPrApprove.DETAILS_PAGE.getSelector());
                detailsButton.click();

                String remarks = jsonNode.get("commonRemarks").get("approveRemarks").asText();


//TODO To Add the CPC-Coordinator
                if (i == 0) {
                    String coordinatorMailId = jsonNode.get("mailIds").get("AddingCoordinator").asText();
                    String mailIdLocator = LPrApprove.getSelector(coordinatorMailId);

                    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("CPC COORDINATOR")).click();
                    Thread.sleep(1000);
                    page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Number")).first().fill(coordinatorMailId);
                    Thread.sleep(1000);
                    page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Number")).first().click();
                    Thread.sleep(1000);
                    page.locator(mailIdLocator).click();

                    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Update")).click();

                    // Click the button and wait for the API response
                    page.waitForResponse(
                            response -> response.url().startsWith("https://cpc_test_admin.cormsquare.com/api/ActivityLogs/list/PurchaseRequisition/") && response.status() == 200,
                            () -> page.waitForSelector(UPDATE_YES.getSelector()).click());

                    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("CPC COORDINATOR")).click();

                    Locator cpcCoordinator = page.locator(CPC_COORDINATOR.getSelector());
                    assertThat(cpcCoordinator).containsText(coordinatorMailId);
                }

                page.waitForSelector(APPROVE_PR_BUTTON.getSelector()).click();

                page.waitForSelector(REASON_APPROVE.getSelector()).fill(remarks);
                page.waitForSelector(SUBMIT_APPROVE.getSelector()).click();
                Thread.sleep(2000);

                int approverCount = i+1;
                Locator approverStatus = page.locator(approverStatus(approverCount));
                approverStatus.scrollIntoViewIfNeeded();
                assertThat(approverStatus).containsText("Approved");

                attachScreenshotWithName("PR Approve", page);
                iLogout.performLogout();
            }
        } catch (Exception exception) {
            logger.error("Exception in Approve function: {}", exception.getMessage());
        }
    }
}