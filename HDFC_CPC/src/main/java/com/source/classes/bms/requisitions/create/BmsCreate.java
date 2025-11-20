package com.source.classes.bms.requisitions.create;

import static com.enums.bms.requisitions.create.LBmsPrCreate.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.bms.requisitions.create.IBmsPrCreate;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.factory.PlaywrightFactory.attachScreenshotWithName;
import static com.utils.ClearVendorEmails.clearVendorEmails;
import static com.utils.SaveToJsonUtil.saveToJson;

public class BmsCreate implements IBmsPrCreate {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    ObjectMapper objectMapper;

    private BmsCreate() {
    }

    //TODO Constructor
    public BmsCreate(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout, ObjectMapper objectMapper) {
        this.page = page;
        this.jsonNode = jsonNode;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(BmsCreate.class);
        this.objectMapper = objectMapper;
    }

    public void requesterLogin() {
        try {
            String requesterEmail = jsonNode.get("mailIds").get("requesterEmail").asText();
            iLogin.performLogin(requesterEmail);
        } catch (Exception exception) {
            logger.error("Exception in Requester Login PR Create function: {}", exception.getMessage());
        }
    }

    public void createButton() {
        try {
            page.waitForSelector(REQUISITION_BMS.getSelector()).click();
            page.waitForSelector(CREATE_BUTTON.getSelector()).click();
        } catch (Exception exception) {
            logger.error("Exception in Create Button function: {}", exception.getMessage());
        }
    }

    public void location() {
        try {
            String locationCode = jsonNode.get("locationDetails").get("locationCode").asText();
            String locationName = jsonNode.get("locationDetails").get("locationName").asText();
            String locationSelection = jsonNode.get("locationDetails").get("locationSelection").asText();
            String locationSelectionType = jsonNode.get("locationDetails").get("locationSelectionType").asText();
            String selectingFloor = jsonNode.get("locationDetails").get("selectingFloor").asText();
            String selectingTierType = jsonNode.get("locationDetails").get("selectingTierType").asText();
            String rbiClassification = jsonNode.get("locationDetails").get("RBIClassification").asText();
            String contactPersonCode = jsonNode.get("locationDetails").get("contactPersonCode").asText();
            String contactPersonName = jsonNode.get("locationDetails").get("contactPersonName").asText();

            page.waitForSelector(LOCATION_SEARCH.getSelector()).fill(locationCode);
            String locationNameLocator = getSelector(locationName);
            page.waitForSelector(locationNameLocator).click();

            page.waitForSelector(SELECT_LOCATION.getSelector()).click();
            String locationSelectionLocator = getSelector(locationSelection);
            page.waitForSelector(locationSelectionLocator).click();

            page.waitForSelector(LOCATION_TYPE.getSelector()).click();
            String locationSelectionTypeLocator = getSelector(locationSelectionType);
            page.waitForSelector(locationSelectionTypeLocator).click();

            page.waitForSelector(FLOOR_SELECTION.getSelector()).click();
            String selectingFloorLocator = getSelector(selectingFloor);
            page.waitForSelector(selectingFloorLocator).click();

            page.waitForSelector(TIER_TYPE.getSelector()).click();
            String selectingTierTypeLocator = getSelector(selectingTierType);
            page.waitForSelector(selectingTierTypeLocator).click();

            page.waitForSelector(RBI_CLASSIFICATION.getSelector()).click();
            String rbiClassificationLocator = getSelector(rbiClassification);
            page.waitForSelector(rbiClassificationLocator).click();

            page.waitForSelector(CONTACT_PERSON.getSelector()).fill(contactPersonCode);
            String contactPersonNameLocator = getSelector(contactPersonName);
            page.waitForSelector(contactPersonNameLocator).click();

            page.waitForSelector(NEXT_BUTTON.getSelector()).click();
            Thread.sleep(2000);
        } catch (Exception exception) {
            logger.error("Exception in Location function: {}", exception.getMessage());
        }
    }

    public void itemDetails() {
        try {
            for (JsonNode item : jsonNode.get("bmsItems")) {
//                page.waitForSelector(CATEGORY_BUTTON.getSelector()).click();
//                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(item.get("categoryName").asText())).click();
//                Locator categoryNameLocator = page.locator(itemCategorySelector(item)).last();
                Response itemCategoryResponse;
                try {
                    itemCategoryResponse = page.waitForResponse(
                            response -> response.url().startsWith("https://cpc_test_admin.cormsquare.com/api/FormTemplates/questionnairenew/")
                                    && response.status() == 200,
                            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(item.get("categoryName").asText()))::click

                    );
                } catch (Exception e) {
                    itemCategoryResponse = page.waitForResponse(
                            response -> response.url().startsWith("https://cpc_test_admin.cormsquare.com/api/FormTemplates/questionnairenew/")
                                    && response.status() == 200,
                            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(item.get("categoryName").asText()))::click
                    );
                }

                JsonNode itemSpecificationsObject = objectMapper.readTree(itemCategoryResponse.body());

//TODO SPECIAL PO LOGI
                    Locator questionnairePopup = page.locator(ITEM_QUESTIONNAIRE.getSelector()).last();
                    if (questionnairePopup.count() > 0 && questionnairePopup.isVisible()) {
                        JsonNode formTemplateFields = itemSpecificationsObject.get("formTemplateFields");
                        List<String> inputTypes = new ArrayList<>();
                        for (JsonNode node : formTemplateFields) {
                            if (node.has("inputType")) {
                                inputTypes.add(node.get("inputType").asText());
                            }
                        }
                        for (int i = 0; i < inputTypes.size(); i++) {
                            int index = i + 1;
                            String inputType = inputTypes.get(i).toLowerCase();
                            switch (inputType) {
                                case "textbox" ->
                                        page.waitForSelector(getInputTypeSelector(inputType, index)).fill("Textbox Input");
                                case "date" -> {
                                    page.waitForSelector(getInputTypeSelector(inputType, index)).click();
                                    page.waitForSelector(TODAY.getSelector()).click();
                                }
                                case "file" ->
                                        page.waitForSelector(getInputTypeSelector(inputType, index)).setInputFiles(Paths.get(ATTACHMENT_PATH.getSelector()));
                                case "checkbox" ->
                                        page.waitForSelector(getInputTypeSelector(inputType, index)).check();
                                case "upload" -> { /* TODO NO Action Required */ }
                                default -> logger.error("Unknown input type: {}", inputType);
                            }
                        }

                        Thread.sleep(2000);
                        page.waitForSelector(SAVE_BUTTON.getSelector()).click();


                    }
                    if (item.has("subCategoryName")) {
                        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(item.get("subCategoryName").asText())).click();
                        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Close dialog")).click();
                    }
                    Thread.sleep(3000);

                    String itemName = item.get("itemName").asText();
                    String quantity = item.get("quantity").asText();
                    Locator itemRow = page.locator(getItemRowSelector(itemName));

                    page.waitForLoadState(LoadState.NETWORKIDLE);

                    itemRow.locator(ITEM_CHECKBOX.getSelector()).click();
                    Thread.sleep(1000);
                    itemRow.locator(ITEM_QUANTITY.getSelector()).fill(quantity);
                    Thread.sleep(1000);
                    itemRow.locator(ITEM_QUANTITY.getSelector()).press("Enter");
                    Thread.sleep(1000);


                    Thread.sleep(2000);
                    page.waitForSelector(ITEM_ADD_TO_CART.getSelector()).click();

                    String itemPopupButtonText = ITEM_POPUP_BUTTON_TEXT.getSelector();
                    if (page.waitForSelector(itemPopupButtonText).isVisible()) {
                        page.waitForSelector(ITEM_POPUP_BUTTON_CLOSE.getSelector()).click();
                    }
                }

            page.waitForSelector(ITEM_LEVEL_NEXT_BUTTON.getSelector()).click();
        } catch (Exception exception) {
            logger.error("Exception in Item Details function: {}", exception.getMessage());
        }
    }
    public void costCodeDetails() {
        try {
            JsonNode costCode = jsonNode.get("costCode").get("costCode");
            JsonNode costCodeName = jsonNode.get("costCode").get("costCodeName");
            JsonNode percentageCostCode = jsonNode.get("costCode").get("percentageCostCode");

            for (int y = 0; y < costCode.size(); y++) {
                String costCodeKey = costCode.get(y).asText();
                String costCodeNameKey = costCodeName.get(y).asText();
                String percentageKey = percentageCostCode.get(y).asText();

                page.waitForSelector(COSTCODE_SEARCH.getSelector()).fill(costCodeKey);

                String costCodeNameLocator = getSelector(costCodeNameKey);
                page.waitForSelector(costCodeNameLocator).click();

                page.waitForSelector(COSTCODE_PERCENTAGE.getSelector()).fill(percentageKey);

                page.waitForSelector(COSTCODE_ADD_BUTTON.getSelector()).click();
            }
            page.waitForSelector(COSTCODE_LEVEL_NEXT_BUTTON.getSelector()).click();
        } catch (Exception error) {
            logger.error("Exception in Cost Code Details function: {}", error.getMessage());
        }
    }

    public void approvalDetails() {
        try {
            String businessHeadMailId = jsonNode.get("mailIds").get("BmsPrSecondLevelApprover").asText();

            String addApproversButton = ADDING_APPROVAL_CREATE.getSelector();
            page.locator(addApproversButton).last().click();

            page.waitForSelector(APPROVAL_SEARCH.getSelector()).fill(businessHeadMailId);
            String selectingApprovalEmailLocator = getSelector(businessHeadMailId);
            page.waitForSelector(selectingApprovalEmailLocator).click();

            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(APPLY_APPROVERS.getSelector())).click();

            String prCreateButton = PR_CREATE_BUTTON.getSelector();
            page.locator(prCreateButton).last().click();

            page.waitForSelector(YES_BUTTON.getSelector()).click();

            page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        } catch (Exception exception) {
            logger.error("Exception in Approval Details PR Create function: {}", exception.getMessage());
        }
    }

    public void savePRNumber() {
        try {
            String trnNumber = page.locator(TRANSACTION_ID.getSelector()).textContent();
            String prReferenceNumber = page.locator(REFERENCE_ID.getSelector()).textContent().trim();
            int numberOfTrn = 0;
            String trnId;
            if (trnNumber.contains(" - ")) {
                trnId = trnNumber.split(" - ")[0].trim();
                numberOfTrn = Integer.parseInt(trnNumber.split(" - ")[1].trim());
            } else
            {
                trnId = trnNumber;
                numberOfTrn=1;
            }

            saveToJson("purchaseRequisitionNumber", "prNumber", trnId);
            saveToJson("purchaseRequisitionNumber", "prNumberCount", String.valueOf(numberOfTrn));
            saveToJson("purchaseRequisitionNumber", "prReferenceNumber", prReferenceNumber);

            clearVendorEmails(jsonNode);

            attachScreenshotWithName("PR Number", page);
        } catch (Exception exception) {
            logger.error("Exception in Save PR Number function: {}", exception.getMessage());
        }
    }
}
