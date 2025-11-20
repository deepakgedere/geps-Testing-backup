package com.source.classes.bms.requisitions.selectVendor;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.source.classes.infra.requisitions.edit.Edit;
import com.source.interfaces.bms.requisitions.create.IBmsPrCreate;
import com.source.interfaces.bms.requisitions.selectVendor.IBmsSelectVendor;
import com.source.interfaces.infra.requisitions.create.IPrCreate;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import static com.enums.bms.requisitions.selectVendor.LBmsSelectVendor.*;
import static com.enums.infra.billofquantity.create.LBoqCreate.BOQCREATED_STATUS;
import static com.enums.infra.billofquantity.create.LBoqCreate.SAVE_VENDOR_ADDRESS;
import static com.enums.infra.billofquantity.create.LBoqCreate.vendorAddressRadioButton;
import static com.factory.PlaywrightFactory.attachScreenshotWithName;
import static com.utils.GetRowUtil.getRow;

public class BmsSelectVendor implements IBmsSelectVendor {
    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    IPrCreate iPrCreate;

    private BmsSelectVendor() {
    }

    //TODO Constructor
    public BmsSelectVendor(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.iPrCreate = iPrCreate;
        this.logger = LoggerUtil.getLogger(Edit.class);
    }

    public void requesterLoginPRDetails(String trnId) {
        try {
            String requesterMailId = jsonNode.get("mailIds").get("requesterEmail").asText();
            iLogin.performLogin(requesterMailId);

            page.waitForSelector(REQUISITION_BMS.getSelector()).click();

            Locator row = getRow(page, trnId);
            Locator detailsButton = row.locator(DETAILS_PAGE.getSelector());
            detailsButton.click();

        } catch (Exception exception) {
            logger.error("Exception in Requester Login for Edit PR function: {}", exception.getMessage());
        }
    }

    public void selectVendor(){
        try{
            String vendorAddress = jsonNode.get("bmsItems").get(0).get("vendorAddress").asText();
            String vendorName = jsonNode.get("bmsItems").get(0).get("vendorName").asText();

            page.waitForSelector(ADD_VENDOR_BUTTON.getSelector()).click();
//            page.waitForSelector(SELECT_VENDOR_LABEL.getSelector()).click();
            page.waitForSelector(SEARCH_VENDOR.getSelector()).fill(vendorName);
            page.waitForSelector(returnSpanContainsText(vendorName)).click();

            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();

            page.waitForSelector(vendorAddressRadioButton(vendorAddress)).click();

            page.waitForSelector(SAVE_VENDOR_ADDRESS.getSelector()).click();

            Thread.sleep(2000);

            page.waitForSelector(BOQCREATED_STATUS.getSelector(), new Page.WaitForSelectorOptions().setTimeout(30000));
            attachScreenshotWithName("BOQCreated", page);

            page.getByRole(AriaRole.GRIDCELL, new Page.GetByRoleOptions().setName(vendorName)).scrollIntoViewIfNeeded();
            attachScreenshotWithName("Vendor Selected", page);

            iLogout.performLogout();
        } catch(Exception exception) {
            logger.error("Exception in Select Vendor function: {}", exception.getMessage());
        }
    }
}
