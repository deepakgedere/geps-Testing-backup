package com.source.classes.infra.requisitions.create;

import com.enums.infra.requisitions.create.LPrCreate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microsoft.playwright.*;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.infra.requisitions.create.IPrCreate;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.factory.PlaywrightFactory.attachScreenshotWithName;
import static com.utils.ClearVendorEmails.clearVendorEmails;
import static com.utils.SaveToJsonUtil.saveToJson;

public class Create implements IPrCreate {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    ObjectMapper objectMapper;

    private Create() {
    }

    //TODO Constructor
    public Create(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout, ObjectMapper objectMapper) {
        this.page = page;
        this.jsonNode = jsonNode;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(Create.class);
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
            page.waitForSelector(LPrCreate.REQUISITION_INFRA.getSelector()).click();
            page.waitForSelector(LPrCreate.CREATE_BUTTON.getSelector()).click();
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
            String selectingLockerType = jsonNode.get("locationDetails").get("selectingLockerType").asText();
            String rbiClassification = jsonNode.get("locationDetails").get("RBIClassification").asText();
            String locationOwnerCode = jsonNode.get("locationDetails").get("locationOwnerCode").asText();
            String locationOwnerName = jsonNode.get("locationDetails").get("locationOwnerName").asText();
            String contactPersonCode = jsonNode.get("locationDetails").get("contactPersonCode").asText();
            String contactPersonName = jsonNode.get("locationDetails").get("contactPersonName").asText();

            page.waitForSelector(LPrCreate.LOCATION_SEARCH.getSelector()).fill(locationCode);
            String locationNameLocator = LPrCreate.getSelector(locationName);
            page.waitForSelector(locationNameLocator).click();

            page.waitForSelector(LPrCreate.SELECT_LOCATION.getSelector()).click();
            String locationSelectionLocator = LPrCreate.getSelector(locationSelection);
            page.waitForSelector(locationSelectionLocator).click();

            page.waitForSelector(LPrCreate.LOCATION_TYPE.getSelector()).click();
            String locationSelectionTypeLocator = LPrCreate.getSelector(locationSelectionType);
            page.waitForSelector(locationSelectionTypeLocator).click();

            page.waitForSelector(LPrCreate.FLOOR_SELECTION.getSelector()).click();
            String selectingFloorLocator = LPrCreate.getSelector(selectingFloor);
            page.waitForSelector(selectingFloorLocator).click();

            page.waitForSelector(LPrCreate.LOCATION_OWNER.getSelector()).fill(locationOwnerCode);
            String locationOwnerNameLocator = LPrCreate.getSelector(locationOwnerName);
            page.waitForSelector(locationOwnerNameLocator).click();

            page.waitForSelector(LPrCreate.TIER_TYPE.getSelector()).click();
            String selectingTierTypeLocator = LPrCreate.getSelector(selectingTierType);
            page.waitForSelector(selectingTierTypeLocator).click();

            page.waitForSelector(LPrCreate.LOCKER_TYPE.getSelector()).click();
            String selectingLockerTypeLocator = LPrCreate.getSelector(selectingLockerType);
            page.waitForSelector(selectingLockerTypeLocator).click();

            page.waitForSelector(LPrCreate.RBI_CLASSIFICATION.getSelector()).click();
            String rbiClassificationLocator = LPrCreate.getSelector(rbiClassification);
            page.waitForSelector(rbiClassificationLocator).click();

            page.waitForSelector(LPrCreate.CONTACT_PERSON.getSelector()).fill(contactPersonCode);
            String contactPersonNameLocator = LPrCreate.getSelector(contactPersonName);
            page.waitForSelector(contactPersonNameLocator).click();

            page.waitForSelector(LPrCreate.NEXT_BUTTON.getSelector()).click();
            Thread.sleep(2000);
        } catch (Exception exception) {
            logger.error("Exception in Location function: {}", exception.getMessage());
        }
    }

    public void itemDetails() {
        try {
            for (JsonNode item : jsonNode.get("items")) {
                page.waitForSelector(LPrCreate.CATEGORY_BUTTON.getSelector()).click();
                Locator categoryNameLocator = page.locator(LPrCreate.itemCategorySelector(item)).last();
                Response itemCategoryResponse;
                try {
                    itemCategoryResponse = page.waitForResponse(
                            response -> response.url().startsWith("https://cpc_test_admin.cormsquare.com/api/FormTemplates/questionnairenew/")
                                    && response.status() == 200,
                            categoryNameLocator::click
                    );
                } catch (Exception e) {
                    page.waitForSelector(LPrCreate.CATEGORY_BUTTON.getSelector()).click();
                    categoryNameLocator = page.locator(LPrCreate.getSelector(item.get("categoryName").asText())).last();
                    itemCategoryResponse = page.waitForResponse(
                            response -> response.url().startsWith("https://cpc_test_admin.cormsquare.com/api/FormTemplates/questionnairenew/")
                                    && response.status() == 200,
                            categoryNameLocator::click
                    );
                }

                JsonNode itemSpecificationsObject = objectMapper.readTree(itemCategoryResponse.body());

//TODO SPECIAL PO LOGIC
                if (!item.has("itemName")) {
                    Locator questionnairePopup = page.locator(LPrCreate.ITEM_QUESTIONNAIRE.getSelector()).last();
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
                                        page.waitForSelector(LPrCreate.getInputTypeSelector(inputType, index)).fill("Textbox Input");
                                case "date" -> {
                                    page.waitForSelector(LPrCreate.getInputTypeSelector(inputType, index)).click();
                                    page.waitForSelector(LPrCreate.TODAY.getSelector()).click();
                                }
                                case "file" ->
                                        page.waitForSelector(LPrCreate.getInputTypeSelector(inputType, index)).setInputFiles(Paths.get(LPrCreate.ATTACHMENT_PATH.getSelector()));
                                case "checkbox" ->
                                        page.waitForSelector(LPrCreate.getInputTypeSelector(inputType, index)).check();
                                case "upload" -> { /* TODO NO Action Required */ }
                                default -> logger.error("Unknown input type: {}", inputType);
                            }
                        }
                        Thread.sleep(2000);
                        page.waitForSelector(LPrCreate.SAVE_BUTTON.getSelector()).click();
                        try {
                            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName(LPrCreate.ESTIMATED_AMOUNT_AS_PER_QUOTATION.getSelector())).fill("100000");
                        } catch (Exception e) {
                            page.waitForSelector(LPrCreate.SAVE_BUTTON.getSelector()).click();
                            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName(LPrCreate.ESTIMATED_AMOUNT_AS_PER_QUOTATION.getSelector())).fill("100000");
                        }
                        Locator fileInputLabel = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(LPrCreate.QUOTATION_ATTACHMENT.getSelector()));
                        fileInputLabel.locator(LPrCreate.QUOTATION_ATTACHMENT_BUTTON.getSelector()).setInputFiles(Paths.get(LPrCreate.ATTACHMENT_PATH.getSelector()));

                        page.waitForSelector(LPrCreate.ITEM_ADD_TO_CART.getSelector()).click();
                    }
                }

//TODO NORMAL PO LOGIC
                else {
                    if (item.has("subCategoryName")) {
                        Locator subCategoryBox = page.getByRole(AriaRole.LISTBOX, new Page.GetByRoleOptions().setName(LPrCreate.SELECT_SUBCATEGORY.getSelector()));
                        subCategoryBox.click();
                        String subCategoryNameLocator = LPrCreate.getSelector(item.get("subCategoryName").asText());
                        page.waitForSelector(subCategoryNameLocator).click();
                    }
                    Thread.sleep(3000);

                    String itemName = item.get("itemName").asText();
                    String quantity = item.get("quantity").asText();
                    Locator itemRow = page.locator(LPrCreate.getItemRowSelector(itemName));

                    page.waitForLoadState(LoadState.NETWORKIDLE);

                    itemRow.locator(LPrCreate.ITEM_CHECKBOX.getSelector()).click();
                    Thread.sleep(1000);
                    itemRow.locator(LPrCreate.ITEM_QUANTITY.getSelector()).fill(quantity);
                    Thread.sleep(1000);
                    itemRow.locator(LPrCreate.ITEM_QUANTITY.getSelector()).press("Enter");
                    Thread.sleep(1000);

                    page.waitForSelector(LPrCreate.ITEM_ADD_TO_CART.getSelector()).click();

                    String itemPopupButtonText = LPrCreate.ITEM_POPUP_BUTTON_TEXT.getSelector();
                    if (page.waitForSelector(itemPopupButtonText).isVisible()) {
                        page.waitForSelector(LPrCreate.ITEM_POPUP_BUTTON_CLOSE.getSelector()).click();
                    }
                }
            }
            page.waitForSelector(LPrCreate.ITEM_LEVEL_NEXT_BUTTON.getSelector()).click();
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

                page.waitForSelector(LPrCreate.COSTCODE_SEARCH.getSelector()).fill(costCodeKey);

                String costCodeNameLocator = LPrCreate.getSelector(costCodeNameKey);
                page.waitForSelector(costCodeNameLocator).click();

                page.waitForSelector(LPrCreate.COSTCODE_PERCENTAGE.getSelector()).fill(percentageKey);

                page.waitForSelector(LPrCreate.COSTCODE_ADD_BUTTON.getSelector()).click();
            }
            page.waitForSelector(LPrCreate.COSTCODE_LEVEL_NEXT_BUTTON.getSelector()).click();
        } catch (Exception error) {
            logger.error("Exception in Cost Code Details function: {}", error.getMessage());
        }
    }

    public void approvalDetails() {
        try {
            String businessHeadMailId = jsonNode.get("mailIds").get("approvalPRCreateBusinessHead").asText();

            String addApproversButton = LPrCreate.ADDING_APPROVAL_CREATE.getSelector();
            page.locator(addApproversButton).last().click();

            page.waitForSelector(LPrCreate.APPROVAL_SEARCH.getSelector()).fill(businessHeadMailId);
            String selectingApprovalEmailLocator = LPrCreate.getSelector(businessHeadMailId);
            page.waitForSelector(selectingApprovalEmailLocator).click();

            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(LPrCreate.APPLY_APPROVERS.getSelector())).click();

            String prCreateButton = LPrCreate.PR_CREATE_BUTTON.getSelector();
            page.locator(prCreateButton).last().click();

            page.waitForSelector(LPrCreate.YES_BUTTON.getSelector()).click();

            page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        } catch (Exception exception) {
            logger.error("Exception in Approval Details PR Create function: {}", exception.getMessage());
        }
    }

    public void savePRNumber() {
        try {
            String trnNumber = page.locator(LPrCreate.TRANSACTION_ID.getSelector()).textContent();
            String prReferenceNumber = page.locator(LPrCreate.REFERENCE_ID.getSelector()).textContent().trim();
            int numberOfTrn = 0;
            String trnId;
            if (trnNumber.contains(" - ")) {
                trnId = trnNumber.split(" - ")[0].trim();
                numberOfTrn = Integer.parseInt(trnNumber.split(" - ")[1].trim());
            } else {
                trnId = trnNumber;
                numberOfTrn = 1;
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