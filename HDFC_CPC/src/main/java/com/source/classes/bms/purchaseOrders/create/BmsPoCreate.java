package com.source.classes.bms.purchaseOrders.create;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.source.classes.bms.requisitions.approve.BmsApprove;
import com.source.interfaces.bms.purchaseOrders.create.IBmsPoCreate;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import static com.enums.bms.purchaseOrders.create.LBmsPoCreate.*;
import static com.factory.PlaywrightFactory.attachScreenshotWithName;
import static com.utils.GetRowUtil.getRow;

public class BmsPoCreate implements IBmsPoCreate {
    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;

    //TODO Constructor
    public BmsPoCreate(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(BmsApprove.class);
    }

    public void requesterLogin() {
        try {
            String requesterMailId = jsonNode.get("mailIds").get("BMSCoordinator").asText();
            iLogin.performLogin(requesterMailId);
        } catch (Exception exception) {
            logger.error("Exception in Requester Login for Edit PR function: {}", exception.getMessage());
        }
    }

    public void createPO(String trnId){
        try {
            page.waitForSelector(REQUISITION_BMS.getSelector()).click();

            Locator row = getRow(page, trnId);
            Locator detailsButton = row.locator(DETAILS_PAGE.getSelector());
            detailsButton.click();

            page.waitForSelector(PO_CREATE_BUTTON.getSelector()).click();

            Thread.sleep(4000);
            page.waitForSelector(POCREATE_STATUS.getSelector());
            attachScreenshotWithName("PO Created", page);
            iLogout.performLogout();

        } catch (Exception exception) {
            logger.error("Exception in Requester Login for Edit PR function: {}", exception.getMessage());
        }
    }
}
