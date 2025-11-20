package com.source.classes.bms.purchaseOrders.edit;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.source.interfaces.bms.purchaseOrders.edit.IBmsPoEdit;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import static com.enums.infra.billofquantity.create.LBoqCreate.getSelector1;
import static com.enums.bms.purchaseOrders.edit.LBmsPoEdit.*;
import static com.factory.PlaywrightFactory.attachScreenshotWithName;
import static com.utils.GetRowUtil.getPoRow;

public class BmsPOEdit implements IBmsPoEdit {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;

    private BmsPOEdit() {
    }

    //TODO Constructor
    public BmsPOEdit(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(BmsPOEdit.class);
    }

    public void poEditorLogin() {
        try {
            String poApproverMailId = jsonNode.get("mailIds").get("POApproveLogin").asText();
            iLogin.performLogin(poApproverMailId);
        } catch (Exception exception) {
            logger.error("Exception in PO Approver Login function: {}", exception.getMessage());
        }
    }

    public void poEdit(String trnId) {
        try {
            page.waitForSelector(BMS_PO_LIST_PAGE.getSelector()).click();

            Locator rows = getPoRow(page, trnId);
            Locator editButton = rows.locator(EDIT_PAGE.getSelector());
            editButton.click();

            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Notes To Approver")).fill("Notes to Approver after Edit");

            String POApprover = jsonNode.get("mailIds").get("POApproveLogin").asText();
            page.waitForSelector(APPROVAL_ROWS.getSelector());
            Locator approvalRows = page.locator(APPROVAL_ROWS.getSelector());
            int rowCount = approvalRows.count();

            for (int i = 0; i < rowCount; i++) {
                Locator row = approvalRows.nth(i);
                if (row.locator(APPOVER_ROW_CONTENT.getSelector()).count() == 0) {
                    row.locator(APPROVER_ROW_SELECT_APPROVER_BUTTON.getSelector()).click();
                    page.waitForSelector(APPROVAL_SEARCH_BOQ.getSelector()).fill(POApprover);
                    page.waitForSelector(getSelector1(POApprover)).click();
                }
            }

            Thread.sleep(2000);

            page.waitForSelector(APPLY_APPROVERS.getSelector()).click();

            page.waitForSelector(UPDATE_BUTTON.getSelector()).click();

            page.waitForSelector(YES_BUTTON_BOQ.getSelector()).click();

            try {
                page.waitForSelector(PENDING_STATUS.getSelector(), new Page.WaitForSelectorOptions().setTimeout(60000)).scrollIntoViewIfNeeded();
            }
            catch (Exception e){
                System.out.println("PO Edit took more than 1 minute");
            }
            attachScreenshotWithName("PO Edited" , page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in PO Edit function: {}", exception.getMessage());
        }
    }
}
