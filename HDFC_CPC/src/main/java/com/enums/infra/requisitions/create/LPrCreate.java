package com.enums.infra.requisitions.create;

import com.fasterxml.jackson.databind.JsonNode;

public enum LPrCreate {

//TODO Location Details
    REQUISITION_INFRA("//span[text()='Purchase Requisitions Infra']"),
    CREATE_BUTTON("//span[contains(text(), ' Create ')]"),
    LOCATION_SEARCH("//input[@placeholder='Enter Location Code/Name to search']"),
    SELECT_LOCATION("//mat-select[@formcontrolname='locationType']"),
    LOCATION_TYPE("//mat-select[@formcontrolname='entityType']"),
    FLOOR_SELECTION("//mat-select[@formcontrolname='floorNumber']"),
    TIER_TYPE("//mat-select[@formcontrolname='tierType']"),
    LOCKER_TYPE("//mat-select[@formcontrolname='lockerType']"),
    RBI_CLASSIFICATION("//mat-select[@formcontrolname='rbiClassification']"),
    LOCATION_OWNER("//input[@placeholder='Search Location Admin Owner']"),
    CONTACT_PERSON("//input[@placeholder='Search Contact Person by Name, Code']"),
    NEXT_BUTTON("//span[text()=' Next ']"),

//TODO Item Details
    CATEGORY_BUTTON("//mat-select[@formcontrolname='categoryName']"),
    ITEM_QUESTIONNAIRE("//h2[contains(text(), 'Item Questionnaires')]"),
    TODAY("//div[contains(@class,'mat-calendar-body-today')]"),
    SAVE_BUTTON("//button//span[text()='Save']"),
    ESTIMATED_AMOUNT_AS_PER_QUOTATION("Estimated Amount as per Quotation"),
    QUOTATION_ATTACHMENT("Quotation Attachment"),
    QUOTATION_ATTACHMENT_BUTTON("xpath=following-sibling::input[1]"),
    ATTACHMENT_PATH("documents\\Attachment.pdf"),
    SELECT_SUBCATEGORY("Select Sub Category"),
    ITEM_CHECKBOX("//mat-checkbox"),
    APPLY_APPROVERS("Apply Approvers"),
    SEARCH_BUTTON("//input[@placeholder='Enter Item to search']"),
    ITEM_CLICK_BOX("//mat-checkbox[@class='mat-checkbox mat-accent']"),
    ITEM_QUANTITY("//input[@formcontrolname='quantity']"),
    ITEM_ADD_TO_CART("//span[text()='Add To Cart ']"),
    ITEM_POPUP_BUTTON_TEXT("//h2[contains(text(), ' Following Purchase Requisitions has created for this Location and Items ')]"),
    ITEM_POPUP_BUTTON_CLOSE("//span[text()='OK']"),
    ITEM_LEVEL_NEXT_BUTTON("(//span[@class='mat-button-wrapper' and normalize-space(text())='Next']/mat-icon[@role='img' and text()='arrow_forward'])[2]"),

//TODO CostCode Details
    COSTCODE_SEARCH("//input[@placeholder='Search Cost Code by name,code']"),
    COSTCODE_PERCENTAGE("//input[@formcontrolname='value']"),
    COSTCODE_ADD_BUTTON("//span[text()=' Add Cost Code ']"),
    COSTCODE_LEVEL_NEXT_BUTTON("(//span[@class='mat-button-wrapper' and normalize-space(text())='Next']/mat-icon[@role='img' and text()='arrow_forward'])[3]"),

//TODO Approval Details
    ADDING_APPROVAL_CREATE("//span[contains(text(),'Add Approvers')]"),
    APPROVAL_SEARCH("input[placeholder='Search by Employee Code, Employee Name, Email']"),
    APPLY_APPROVAL_CREATE("//span[text()=' Apply Approvers ']"),
    PR_CREATE_BUTTON("//mat-icon[text()='done_all']"),
    YES_BUTTON("//span[text()=' Yes ']"),

//TODO Transaction ID Locator
    TRANSACTION_ID("(//td[@data-label='Transaction Id'])[1]"),

//TODO Reference ID Locator
    REFERENCE_ID("(//td[@data-label='Reference ID'])[1]");

    private final String selector;

//TODO Constructor
    LPrCreate(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }

    public static String getSelector(String selector) {
        return "//span[contains(text(), '" + selector + "')]";
    }

    public static String getInputTypeSelector(String inputType, int index) {
        String selector = "";
        switch (inputType) {
            case "textbox", "date", "file" -> selector = "//div[@formarrayname='fields']/div[" + index + "]//input";
            case "checkbox" -> selector = "(//div[@formarrayname='fields']/div[" + index + "]//label)[1]";
            default -> System.out.println("Unknown input type...");
        }
        return selector;
    }

    public static String getItemRowSelector(String itemName) {
        return "//tr[td[div[contains(text(),'" + itemName + "')]]]";
    }

    public static String itemCategorySelector(JsonNode item) {
        return "//span[text()='"+ " "+item.get("categoryName").asText()+ " "+"']";
    }
}