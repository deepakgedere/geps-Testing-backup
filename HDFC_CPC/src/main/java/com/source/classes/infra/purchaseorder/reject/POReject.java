package com.source.classes.infra.purchaseorder.reject;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.source.interfaces.infra.purchaseorder.reject.IPoReject;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import static com.enums.infra.purchaseorders.reject.LPoReject.*;
import static com.factory.PlaywrightFactory.attachScreenshotWithName;
import static com.utils.GetRowUtil.*;

public class POReject implements IPoReject {
    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;

    private POReject() {
    }

    //TODO Constructor
    public POReject(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(POReject.class);
    }

    public void poApproverLogin() {
        try {
            String poApproverMailId = jsonNode.get("mailIds").get("POApproveLogin").asText();
            iLogin.performLogin(poApproverMailId);
        } catch (Exception exception) {
            logger.error("Exception in PO Approver Login function: {}", exception.getMessage());
        }
    }

    public void poDetails(String trnId) {
        try {
            page.waitForSelector(PO_LIST_PAGE.getSelector()).click();

            Locator row = getPoRow(page, trnId);
            Locator detailsButton = row.locator(DETAILS_PAGE.getSelector());
            detailsButton.click();
        } catch (Exception exception) {
            logger.error("Exception in Details PR function: {}", exception.getMessage());
        }
    }

    public void reject(){
        try {

            String remarks = jsonNode.get("commonRemarks").get("rejectRemarks").asText();

            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Reject")).click();

            page.getByRole(AriaRole.TEXTBOX).click();

            page.getByRole(AriaRole.TEXTBOX).fill(remarks);

            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();

            page.waitForSelector(REJECTED_MESSAGE.getSelector()).scrollIntoViewIfNeeded();

            attachScreenshotWithName("PO Reject" , page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Details PR function: {}", exception.getMessage());
        }
    }
}
