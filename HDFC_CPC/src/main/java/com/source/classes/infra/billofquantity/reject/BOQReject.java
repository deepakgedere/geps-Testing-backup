package com.source.classes.infra.billofquantity.reject;
import com.enums.infra.billofquantity.create.LBoqCreate;
import com.enums.infra.billofquantity.reject.LBoqReject;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.source.interfaces.infra.billofquantity.create.IBoqCreate;
import com.source.interfaces.infra.billofquantity.reject.IBoqReject;
import com.source.interfaces.infra.requisitions.reject.IPrReject;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import static com.enums.infra.billofquantity.reject.LBoqReject.*;
import static com.factory.PlaywrightFactory.attachScreenshotWithName;
import static com.utils.GetRowUtil.getRow;

public class BOQReject implements IBoqReject {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    IPrReject iPrReject;
    IBoqCreate iBoqCreate;

    private BOQReject() {
    }

//TODO Constructor
    public BOQReject(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout, IPrReject iPrReject, IBoqCreate iBoqCreate) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.iPrReject = iPrReject;
        this.iBoqCreate = iBoqCreate;
        this.logger = LoggerUtil.getLogger(BOQReject.class);
    }

    public void rejectBoq(String trnId) {
        try {
            String procurementManagerMailId = jsonNode.get("mailIds").get("procurementManagerWest").asText();
            String remarks = jsonNode.get("commonRemarks").get("rejectRemarks").asText();

            iLogin.performLogin(procurementManagerMailId);

            page.waitForSelector(REQUISITION_INFRA_BOQ_REJECT.getSelector()).click();


            Locator row = getRow(page, trnId);
            Locator detailsButton = row.locator(DETAILS_PAGE.getSelector());
            detailsButton.click();


            page.waitForSelector(REJECT_BUTTON.getSelector()).click();


            page.getByRole(AriaRole.TEXTBOX).click();

            page.getByRole(AriaRole.TEXTBOX).fill(remarks);

            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();

            Thread.sleep(3000);
            page.waitForSelector(REJECTED_STATUS.getSelector());

            attachScreenshotWithName("BOQ Reject", page);

            iLogout.performLogout();

            iBoqCreate.CpcCoordinatorLogin();
            iBoqCreate.createBoqButton(trnId, true);
            iBoqCreate.approvalDetailsBOQCreate();
        } catch (Exception exception) {
            logger.error("Exception in BOQ Reject function: {}", exception.getMessage());
        }
    }
}