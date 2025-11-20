package com.source.classes.bms.purchaseOrders.amend;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.source.classes.infra.purchaseorder.approve.POApprove;
import com.source.interfaces.bms.purchaseOrders.amend.IBmsPoAmend;
import com.source.interfaces.bms.purchaseOrders.approve.IBmsPoApprove;
import com.source.interfaces.bms.requisitions.approve.IBmsPrApprove;
import com.source.interfaces.bms.requisitions.edit.IBmsPrEdit;
import com.source.interfaces.bms.requisitions.selectVendor.IBmsSelectVendor;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import static com.enums.bms.purchaseOrders.amend.LBmsPoAmend.*;
import static com.factory.PlaywrightFactory.attachScreenshotWithName;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.utils.GetRowUtil.getPoRow;

public class BmsPOAmend implements IBmsPoAmend {
    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    IBmsPrEdit iBmsPrEdit;
    IBmsSelectVendor iBmsSelectVendor;
    IBmsPrApprove iBmsPrApprove;
    IBmsPoApprove iBmsPoApprove;


    private BmsPOAmend() {
    }

    //TODO Constructor
    public BmsPOAmend(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout, IBmsPrEdit iBmsPrEdit, IBmsSelectVendor iBmsSelectVendor, IBmsPrApprove iBmsPrApprove, IBmsPoApprove iBmsPoApprove) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.iBmsPrEdit = iBmsPrEdit;
        this.iBmsSelectVendor = iBmsSelectVendor;
        this.iBmsPrApprove = iBmsPrApprove;
        this.iBmsPoApprove = iBmsPoApprove;
        this.logger = LoggerUtil.getLogger(POApprove.class);
    }

    public void poAmmendLogin() {
        try {
            String poAmmendMailId = jsonNode.get("mailIds").get("POApproveLogin").asText();
            iLogin.performLogin(poAmmendMailId);
        } catch (Exception exception) {
            logger.error("Exception in PO Ammend Login function: {}", exception.getMessage());
        }
    }

    public void ammendPo(String trnId) {
        try {
            page.waitForSelector(BMS_PO_LIST_PAGE.getSelector()).click();

            Locator row = getPoRow(page, trnId);
            Locator detailsButton = row.locator(DETAILS_PAGE.getSelector());
            detailsButton.click();

            String remarks = jsonNode.get("commonRemarks").get("amendRemarks").asText();

            page.waitForSelector(AMEND_PO_BUTTON.getSelector()).click();

            page.waitForSelector(AMEND_PO_REASON.getSelector()).fill(remarks);

            page.waitForSelector(CLICKING_AMEND_PO_BUTTON.getSelector()).click();

            page.waitForSelector(OK_APPLICABLE_RATECONTRACT.getSelector()).click();

            page.waitForSelector(AMEND_PO_CONFIRMATION.getSelector()).click();


            try {
                // Verify the success message
                Locator statusLocator = row.locator(ROW_STATUS.getSelector());
                statusLocator.waitFor(new Locator.WaitForOptions().setTimeout(20000));
                assertThat(statusLocator).containsText("Amend Initiated");
                attachScreenshotWithName("PO Ammend Initiated", page);
            }catch (Exception e) {
                // Verify the success message
                page.waitForSelector(BMS_PO_LIST_PAGE.getSelector()).click();
                Locator statusLocator = row.locator(ROW_STATUS.getSelector());
                statusLocator.waitFor(new Locator.WaitForOptions().setTimeout(60000));
                assertThat(statusLocator).containsText("Amend Initiated");
                attachScreenshotWithName("PO Ammend Initiated", page);
            }
            iLogout.performLogout();


            iBmsPrEdit.bmsCoordinatorLoginEditPR();
            iBmsPrEdit.edit(trnId);
            iBmsPrEdit.editPageNextButton();

            iBmsSelectVendor.requesterLoginPRDetails(trnId);
            iBmsSelectVendor.selectVendor();

            iBmsPrApprove.approve(trnId);

            iBmsPoApprove.poApproverLogin();
            iBmsPoApprove.poDetails(trnId);
            iBmsPoApprove.approve();
        } catch (Exception exception) {
            logger.error("Exception in Amend PO function: {}", exception.getMessage());
        }
    }
}
