package com.source.classes.bms.requisitions.edit;

import static com.enums.bms.requisitions.edit.LBmsPrEdit.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.source.interfaces.bms.requisitions.create.IBmsPrCreate;
import com.source.interfaces.bms.requisitions.edit.IBmsPrEdit;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import static com.factory.PlaywrightFactory.attachScreenshotWithName;
import static com.utils.GetRowUtil.getRow;

public class BmsEdit implements IBmsPrEdit {
    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    IBmsPrCreate iBmsPrCreate;


    private BmsEdit() {
    }

    //TODO Constructor
    public BmsEdit(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout, IBmsPrCreate iBmsPrCreate) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.iBmsPrCreate = iBmsPrCreate;
        this.logger = LoggerUtil.getLogger(BmsEdit.class);
    }

    public void requesterLoginEditPR() {
        try {
            String requesterMailId = jsonNode.get("mailIds").get("requesterEmail").asText();
            iLogin.performLogin(requesterMailId);
        } catch (Exception exception) {
            logger.error("Exception in Requester Login for Edit PR function: {}", exception.getMessage());
        }
    }

    public void bmsCoordinatorLoginEditPR() {
        try {
            String cpcCoordinatorMailId = jsonNode.get("mailIds").get("BMSCoordinator").asText();
            iLogin.performLogin(cpcCoordinatorMailId);
        } catch (Exception exception) {
            logger.error("Exception in BMS Coordinator Login for Edit PR function: {}", exception.getMessage());
        }
    }

    public void edit(String trnId) {
        try {
            page.waitForSelector(REQUISITION_BMS.getSelector()).click();

            Locator row = getRow(page, trnId);
            Locator editButton = row.locator(PR_EDIT_BUTTON.getSelector());

            editButton.click();
        } catch (Exception exception) {
            logger.error("Exception in Edit PR function: {}", exception.getMessage());
        }
    }

    public void editPageNextButton() {
        try {
            page.waitForSelector(EDIT_NEXT_BUTTON.getSelector()).click();

            page.waitForSelector(EDIT_NEXT_BUTTON_ITEM.getSelector()).click();

            page.waitForSelector(EDIT_NEXT_BUTTON_COSTCODE.getSelector()).click();

            iBmsPrCreate.approvalDetails();

            Thread.sleep(2000);

            attachScreenshotWithName("PR Edit", page);
            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Edit Page Next Button function: {}", exception.getMessage());
        }
    }
}
