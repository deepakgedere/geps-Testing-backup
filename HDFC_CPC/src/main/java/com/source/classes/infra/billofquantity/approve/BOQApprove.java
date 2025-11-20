package com.source.classes.infra.billofquantity.approve;
import com.enums.infra.billofquantity.approve.LBoqApprove;
import com.enums.infra.billofquantity.create.LBoqCreate;
import com.enums.infra.requisitions.approve.LPrApprove;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.source.interfaces.infra.billofquantity.approve.IBoqApprove;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;

import static com.enums.infra.billofquantity.approve.LBoqApprove.*;

import static com.enums.infra.billofquantity.create.LBoqCreate.BOQCREATED_STATUS;
import static com.enums.infra.billofquantity.create.LBoqCreate.BOQ_APPROVALPENDING_STATUS;
import static com.factory.PlaywrightFactory.attachScreenshotWithName;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.utils.GetRowUtil.getRow;

public class BOQApprove implements IBoqApprove {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    ObjectMapper objectMapper = new ObjectMapper();

    private BOQApprove() {
    }

//TODO Constructor
    public BOQApprove(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(BOQApprove.class);
    }

    public void boqApprove(String trnId) {
        try {
            String prNumber = jsonNode.get("purchaseRequisitionNumber").get("prNumber").asText();
            String remarks = jsonNode.get("commonRemarks").get("approveRemarks").asText();
            String[] approvers = {
                    jsonNode.get("mailIds").get("PrApproveFirstLevelProcurementManagerWest").asText(),
                    jsonNode.get("mailIds").get("FunctionalHead").asText()
            };

            for (int i = 0; i < approvers.length; i++) {
                iLogin.performLogin(approvers[i]);

                page.waitForSelector(REQUISITION_INFRA_LIST_PAGE_BOQ_APPROVE.getSelector()).click();

                Locator row = getRow(page, trnId);
                Locator detailsButton = row.locator(DETAILS_PAGE.getSelector());
                detailsButton.click();

                page.waitForSelector(APPROVE_PR_BUTTON.getSelector()).click();

                page.waitForSelector(REASON_APPROVE.getSelector()).fill(remarks);

                page.waitForSelector(SUBMIT_APPROVE.getSelector()).click();

                int currentApproverCount = i+1;
                Locator status = page.locator(approverStatus(currentApproverCount));

                int maxRetries = 3;
                boolean found = false;
                for (int attempt = 1; attempt <= maxRetries; attempt++) {
                    try {
//                        page.waitForSelector(BOQ_APPROVALPENDING_STATUS.getSelector(), new Page.WaitForSelectorOptions().setTimeout(4000));
                        Thread.sleep(4000);
                        status.scrollIntoViewIfNeeded();
                        assertThat(status).hasText("Approved");
                        attachScreenshotWithName("BOQ Approved", page);
                        found = true;
                        break;
                    } catch (Exception e1) {
                        try {
//                            page.waitForSelector(BOQCREATED_STATUS.getSelector(), new Page.WaitForSelectorOptions().setTimeout(2000));
                            Thread.sleep(4000);
                            status.scrollIntoViewIfNeeded();
                            assertThat(status).hasText("Approved");
                            attachScreenshotWithName("BOQ Approved", page);
                            found = true;
                            break;
                        } catch (Exception e2) {
                            // Continue to next attempt
                        }
                    }
                }
                if (!found) {
                    throw new RuntimeException("Status is still pending after 3 retries.");
                }

//TODO Create PO Only After Final Approver
                if (i == approvers.length - 1) {
                    page.waitForSelector(CREATE_PO.getSelector()).click();

                    Thread.sleep(4000);
                    page.waitForSelector(po_created(trnId)).scrollIntoViewIfNeeded();
                    attachScreenshotWithName("BOQ Approve and PO Created", page);
                }
                iLogout.performLogout();
            }

        } catch (Exception exception) {
            logger.error("Exception in BOQ Approve function: {}", exception.getMessage());
        }
    }
}