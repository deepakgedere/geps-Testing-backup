package com.source.classes.infra.requisitions.edit;
import com.enums.infra.requisitions.edit.LPrEdit;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.source.interfaces.infra.requisitions.create.IPrCreate;
import com.source.interfaces.infra.requisitions.edit.IPrEdit;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import static com.factory.PlaywrightFactory.attachScreenshotWithName;
import static com.utils.GetRowUtil.getRow;

public class Edit implements IPrEdit {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    IPrCreate iPrCreate;

    private Edit() {
    }

//TODO Constructor
    public Edit(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout, IPrCreate iPrCreate) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.iPrCreate = iPrCreate;
        this.logger = LoggerUtil.getLogger(Edit.class);
    }

    public void requesterLoginEditPR() {
        try {
            String requesterMailId = jsonNode.get("mailIds").get("requesterEmail").asText();
            iLogin.performLogin(requesterMailId);
        } catch (Exception exception) {
            logger.error("Exception in Requester Login for Edit PR function: {}", exception.getMessage());
        }
    }

    public void cpcCoordinatorLoginEditPR() {
        try {
            String cpcCoordinatorMailId = jsonNode.get("mailIds").get("CPCCoordinatorWest").asText();
            iLogin.performLogin(cpcCoordinatorMailId);
        } catch (Exception exception) {
            logger.error("Exception in CPC Coordinator Login for Edit PR function: {}", exception.getMessage());
        }
    }

    public void edit(String trnId) {
        try {
            page.waitForSelector(LPrEdit.REQUISITION_INFRA_LIST_PAGE_EDIT.getSelector()).click();

            Locator row = getRow(page, trnId);
            Locator editButton = row.locator(LPrEdit.PR_EDIT_BUTTON.getSelector());

            editButton.click();
        } catch (Exception exception) {
            logger.error("Exception in Edit PR function: {}", exception.getMessage());
        }
    }

    public void editPageNextButton() {
        try {
            page.waitForSelector(LPrEdit.EDIT_NEXT_BUTTON.getSelector()).click();

            page.waitForSelector(LPrEdit.EDIT_NEXT_BUTTON_ITEM.getSelector()).click();

            page.waitForSelector(LPrEdit.EDIT_NEXT_BUTTON_COSTCODE.getSelector()).click();

            iPrCreate.approvalDetails();

            Thread.sleep(2000);

            attachScreenshotWithName("PR Edit", page);
            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Edit Page Next Button function: {}", exception.getMessage());
        }
    }
}