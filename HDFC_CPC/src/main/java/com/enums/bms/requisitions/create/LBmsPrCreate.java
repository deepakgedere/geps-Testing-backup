package com.enums.bms.requisitions.create;

import com.fasterxml.jackson.databind.JsonNode;

public enum LBmsPrCreate {

    //TODO Location Details
    REQUISITION_BMS("//span[text()='Purchase Requisitions Bms']"),
    CREATE_BUTTON("//button[@mattooltip='Create PurchaseRequisition']"),
    LOCATION_SEARCH("//input[@placeholder='Enter Location Code/Name to search']"),
    SELECT_LOCATION("//mat-select[@formcontrolname='locationType']"),
    LOCATION_TYPE("//mat-select[@formcontrolname='entityType']"),
    FLOOR_SELECTION("//mat-select[@formcontrolname='floorNumber']"),
    TIER_TYPE("//mat-select[@formcontrolname='tierType']"),
    RBI_CLASSIFICATION("//mat-select[@formcontrolname='rbiClassification']"),
    CONTACT_PERSON("//input[@placeholder='Search Contact Person by Name, Code']"),
    NEXT_BUTTON("//span[text()=' Next ']"),

    //TODO Item Details
    ITEM_QUESTIONNAIRE("//h2[contains(text(), 'Item Questionnaires')]"),
    TODAY("//div[contains(@class,'mat-calendar-body-today')]"),
    SAVE_BUTTON("//span[text()='Save']"),
    ATTACHMENT_PATH("documents\\Attachment.pdf"),
    ITEM_CHECKBOX("//mat-checkbox"),
    APPLY_APPROVERS("Apply Approvers"),
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
    PR_CREATE_BUTTON("//mat-icon[text()='done_all']"),
    YES_BUTTON("//span[text()=' Yes ']"),

    //TODO Transaction ID Locator
    TRANSACTION_ID("(//td[@data-label='Transaction Id'])[1]"),

    //TODO Reference ID Locator
    REFERENCE_ID("(//td[@data-label=' Reference ID'])[1]");

    private final String selector;

    //TODO Constructor
    LBmsPrCreate(String selector) {
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