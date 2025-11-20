package com.source.classes.infra.billofquantity.create;
import com.enums.infra.billofquantity.create.LBoqCreate;
import com.enums.infra.requisitions.create.LPrCreate;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.source.interfaces.infra.billofquantity.create.IBoqCreate;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.util.Properties;
import static com.enums.infra.billofquantity.create.LBoqCreate.*;
import static com.factory.PlaywrightFactory.attachScreenshotWithName;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.utils.GetRowUtil.getRow;
import static com.utils.SaveToJsonUtil.saveToJson;

public class BOQCreate implements IBoqCreate {

    Logger logger;
    JsonNode jsonNode;
    Properties properties;
    Page page;
    ILogin iLogin;
    ILogout iLogout;

    private BOQCreate() {
    }

//TODO Constructor
    public BOQCreate(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(BOQCreate.class);
    }

    public void PrApproveFirstLevelProcurementManagerWestLogin() {
        try {
            String mailId = jsonNode.get("mailIds").get("PrApproveFirstLevelProcurementManagerWest").asText();
            iLogin.performLogin(mailId);
            page.waitForSelector(REQUISITION_INFRA_CREATE_BOQ.getSelector()).click();
        } catch (Exception exception) {
            logger.error("Exception in BOQ Create function: {}", exception.getMessage());
        }
    }

    public void CpcCoordinatorLogin() {
        try {
            String mailId = jsonNode.get("mailIds").get("CPCCoordinatorWest").asText();
            iLogin.performLogin(mailId);
            page.waitForSelector(REQUISITION_INFRA_CREATE_BOQ.getSelector()).click();
        } catch (Exception exception) {
            logger.error("Exception in BOQ Create function: {}", exception.getMessage());
        }
    }



    public void createBoqButton(String trnId, boolean revision) {
        try {
            Locator row = getRow(page, trnId);
            Locator detailsButton = row.locator(DETAILS_PAGE.getSelector());
            detailsButton.click();

            Locator locationAdminLocator = page.locator(LOCATION_ADMIN_LOCATOR.getSelector());
            String locationAdmin = jsonNode.get("locationDetails").get("locationOwnerName").textValue();
            String locationAdminName = locationAdmin.split(" ")[0];

            assertThat (locationAdminLocator).containsText(locationAdminName);

            String currentItemCategory;

            if(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("SPECIAL WORKFLOW ITEMS")).isVisible()){
                currentItemCategory = page.locator(ITEMCATEGORY_LOCATOR.getSelector()).first().textContent().trim();
            }
            else{
                currentItemCategory = page.locator(NORMAL_ITEMCATEGORY_LOCATOR.getSelector()).first().textContent().trim();
            }

            JsonNode items = jsonNode.get("items");
            JsonNode item = null;

            for(JsonNode x: items){
                if (x.get("categoryName").asText().equals(currentItemCategory)){
                    item= x;
                    break;
                }
            }

            String vendorName = item.get("vendorName").asText();
            String vendorAddress = item.get("vendorAddress").asText();


            Thread.sleep(3000);


            //TODO Special PR Logic START
            if (page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("SPECIAL WORKFLOW ITEMS")).isVisible() && !revision) {
                Locator addItemsButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Items")).last();
                addItemsButton.click(new Locator.ClickOptions().setTimeout(3000));

                if(item.has("specialDetails")){
                    if(item.get("specialDetails").has("subCategoryName")){
                        Locator subCategoryBox = page.getByRole(AriaRole.LISTBOX, new Page.GetByRoleOptions().setName("Select Sub Category"));
                        subCategoryBox.click();
                        String subCategoryNameLocator = LPrCreate.getSelector(item.get("specialDetails").get("subCategoryName").asText());
                        page.waitForSelector(subCategoryNameLocator).click();
                    }

                    String itemCategoryName = item.get("specialDetails").get("itemName").asText();
                    Locator itemRow = page.locator(getItemRow(itemCategoryName));
                    itemRow.locator(ITEM_CHECKBOX.getSelector()).click();

                    itemRow.locator(ITEM_QUANTITY.getSelector()).fill(String.valueOf(item.get("quantity").asInt()));
                    itemRow.locator(ITEM_QUANTITY.getSelector()).press("Enter");

                    page.waitForSelector(LPrCreate.ITEM_ADD_TO_CART.getSelector()).click();

                    String itemPopupButtonText = LPrCreate.ITEM_POPUP_BUTTON_TEXT.getSelector();
                    if (page.waitForSelector(itemPopupButtonText).isVisible()) {
                        page.waitForSelector(LPrCreate.ITEM_POPUP_BUTTON_CLOSE.getSelector()).click();
                    }

                    page.getByRole(AriaRole.BUTTON , new Page.GetByRoleOptions().setName("Submit")).click(new Locator.ClickOptions().setTimeout(3000));
                    page.getByRole(AriaRole.BUTTON , new Page.GetByRoleOptions().setName("Yes")).click(new Locator.ClickOptions().setTimeout(3000));
                }
            }
            //TODO Special PR Logic END


            String remarks = jsonNode.get("commonRemarks").get("createRemarks").asText();

            page.waitForSelector(CREATE_BOQ_BUTTON.getSelector()).click();

            page.locator(getSelector(vendorName)).first().click();

            page.waitForSelector(vendorAddressRadioButton(vendorAddress)).click();

            page.waitForSelector(SAVE_VENDOR_ADDRESS.getSelector()).click();

            page.waitForSelector(ADD_BOQ_ITEM.getSelector()).click();
        } catch (Exception exception) {
            logger.error("Exception in BOQ Create Button function: {}", exception.getMessage());
        }
    }

    public void approvalDetailsBOQCreate() {
        try {
            String functionalHead = jsonNode.get("mailIds").get("FunctionalHead").asText();
            String businessHead = jsonNode.get("mailIds").get("approvalPRCreateBusinessHead").asText();

            page.waitForSelector(APPROVAL_ROWS.getSelector());
            Locator approvalRows = page.locator(APPROVAL_ROWS.getSelector());
            int rowCount = approvalRows.count();

            for (int i = 0; i < rowCount; i++) {
                Locator row = approvalRows.nth(i);
                if (row.locator(APPROVAL_ROW_CONTENT.getSelector()).count() == 0) {
                    row.locator(APPROVAL_ROW_SELECTAPPROVERB_BUTTON.getSelector()).click();
                    try {
                        page.waitForSelector(APPROVAL_SEARCH_BOQ.getSelector()).fill(functionalHead);
                        page.waitForSelector(getSelector1(functionalHead)).click();
                    }
                    catch (Exception exception){
                        page.waitForSelector(APPROVAL_SEARCH_BOQ.getSelector()).fill(businessHead);
                        page.waitForSelector(getSelector1(businessHead)).click();
                    }
                }
            }

            page.waitForSelector(BRANCH_CARPET_AREA.getSelector()).fill("600");
            page.waitForSelector(APPLY_APPROVAL_CREATE_BOQ.getSelector()).click();

            page.waitForSelector(BOQ_SUBMIT_BUTTON.getSelector()).click();

            page.waitForSelector(YES_BUTTON_BOQ.getSelector()).click();

            Thread.sleep(2000);
            int maxRetries = 3;
            boolean found = false;
            for (int attempt = 1; attempt <= maxRetries; attempt++) {
                try {
                    page.waitForSelector(BOQ_APPROVALPENDING_STATUS.getSelector(), new Page.WaitForSelectorOptions().setTimeout(2000));
                    found = true;
                    break;
                } catch (Exception e1) {
                    try {
                        page.waitForSelector(BOQCREATED_STATUS.getSelector(), new Page.WaitForSelectorOptions().setTimeout(2000));
                        found = true;
                        break;
                    } catch (Exception e2) {
                        // Continue to next attempt
                    }
                }
            }
            if (!found) {
                throw new RuntimeException("Neither BOQApprovalPending nor BOQCreated appeared after 3 retries.");
            }

            attachScreenshotWithName("BOQ Created", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Approval Details BOQ Create function: {}", exception.getMessage());
        }
    }
}