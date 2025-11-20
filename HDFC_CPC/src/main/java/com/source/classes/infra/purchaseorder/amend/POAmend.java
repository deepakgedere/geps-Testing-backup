package com.source.classes.infra.purchaseorder.amend;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.source.classes.infra.purchaseorder.approve.POApprove;
import com.source.interfaces.infra.billofquantity.approve.IBoqApprove;
import com.source.interfaces.infra.billofquantity.create.IBoqCreate;
import com.source.interfaces.infra.purchaseorder.amend.IPoAmend;
import com.source.interfaces.infra.purchaseorder.approve.IPoApprove;
import com.source.interfaces.infra.requisitions.approve.IPrApprove;
import com.source.interfaces.infra.requisitions.edit.IPrEdit;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import static com.enums.infra.purchaseorders.amend.LPoAmend.*;
import static com.factory.PlaywrightFactory.attachScreenshotWithName;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.utils.GetRowUtil.getPoRow;

public class POAmend implements IPoAmend {
    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    IPrEdit iPrEdit;
    IPrApprove iPrApprove;
    IBoqCreate iBoqCreate;
    IBoqApprove iBoqApprove;
    IPoApprove iPoApprove;

    private POAmend() {
    }

    //TODO Constructor
    public POAmend(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout, IPrEdit iPrEdit, IPrApprove iPrApprove, IBoqCreate iBoqCreate, IBoqApprove iBoqApprove, IPoApprove iPoApprove) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.iPrEdit = iPrEdit;
        this.iPrApprove = iPrApprove;
        this.iBoqCreate = iBoqCreate;
        this.iBoqApprove = iBoqApprove;
        this.iPoApprove = iPoApprove;
        this.logger = LoggerUtil.getLogger(POApprove.class);
    }

    public void poAmmendLogin() {
        try {
            String poAmmendMailId = jsonNode.get("mailIds").get("PrApproveFirstLevelProcurementManagerWest").asText();
            iLogin.performLogin(poAmmendMailId);
        } catch (Exception exception) {
            logger.error("Exception in PO Ammend Login function: {}", exception.getMessage());
        }
    }

    public void ammendPo(String trnId) {
        try {
            page.waitForSelector(PO_LIST_PAGE_INFRA_AMEND.getSelector()).click();

            Locator row = getPoRow(page, trnId);
            Locator detailsButton = row.locator(DETAILS_PAGE.getSelector());
            detailsButton.click();

            String remarks = jsonNode.get("commonRemarks").get("amendRemarks").asText();

            page.waitForSelector(AMEND_PO_BUTTON.getSelector()).click();

            page.waitForSelector(AMEND_PO_REASON.getSelector()).fill(remarks);

            page.waitForSelector(CLICKING_AMEND_PO_BUTTON.getSelector()).click();

            page.waitForSelector(OK_APPLICABLE_RATECONTRACT.getSelector()).click();

            page.waitForSelector(AMEND_PO_CONFIRMATION.getSelector()).click();

            // Verify the success message
            Locator statusLocator  = row.locator(ROW_STATUS.getSelector());
            statusLocator.waitFor(new Locator.WaitForOptions().setTimeout(60000));
            assertThat(statusLocator).containsText("Amend Initiated");
            attachScreenshotWithName("PO Ammend Initiated" , page);

            iLogout.performLogout();

//            PR Edit
            iPrEdit.cpcCoordinatorLoginEditPR();
            iPrEdit.edit(trnId);
            iPrEdit.editPageNextButton();

            //PR Approve
            iPrApprove.approve(trnId);

//            //BOQ Create
            iBoqCreate.CpcCoordinatorLogin();
            iBoqCreate.createBoqButton(trnId, false);
            iBoqCreate.approvalDetailsBOQCreate();

            //BOQ Approve
            iBoqApprove.boqApprove(trnId);

            //PO Approve
            iPoApprove.poApproverLogin();
            iPoApprove.poDetails(trnId);
            iPoApprove.approve();

        } catch (Exception exception) {
            logger.error("Exception in Amend PO function: {}", exception.getMessage());
        }
    }
}
