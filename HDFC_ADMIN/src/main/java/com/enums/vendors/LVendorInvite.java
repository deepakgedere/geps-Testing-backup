package com.enums.vendors;

public enum LVendorInvite {

    VENDORS_MODULE("//span[contains(text(), 'Vendors')]"),
    FILTER_BY_STATUS("//span[contains(text(), 'Filter by Status')]"),
    ORACLE_VENDOR("//span[contains(text(), 'OracleVendor')]"),
    FILTER_ORACLE_VENDOR_VALIDATION("//mat-select[@placeholder='Filter by Status']//span[contains(text(),'OracleVendor')]"),
    ORACLE_VENDOR_LIST_VENDOR_TYPE("//td[@data-label='Vendor Type']"),
    VENDOR_SEARCH_BAR("//input"),
    VENDOR_SEARCH_BUTTON("//mat-icon[contains(text(),'search')]"),
    FIRST_BUTTON_FOR_DETAILS("(//tbody/tr/td/button)[1]"),
    VENDOR_NAME("//table//tr[.//td[text()='Vendor']]//td[2]"),
    VENDOR_EMAIL("//table//tr[.//td[text()='Vendor Email']]//td[2]"),
    INVITE_VENDOR("//span[contains(text(), 'Invite Vendor')]"),
    VENDOR_EMAIL_INPUT("//input[@formcontrolname='email']"),
    VENDOR_NAME_INPUT("//input[@formcontrolname='name']"),

    VENDOR_DOCUMENT_TYPE("Vendor Document Type *"),
    SKILL_TECHNICAL_EXPERTISE("(//span[contains(text(),'Skill or Technical Expertise')])[1]"),
    CHECKBOX("mat-pseudo-checkbox"),
    SELECT_REGIONS("Select Regions *"),
    FIRST_PROJECT_NAME("First Project Name"),
    CLICK(".cdk-overlay-container"),
    REFERRED_BY("//input[@formcontrolname='referredBy']"),
    REFERRED_NAME("//input[@formcontrolname='employeeName']"),
    VENDOR_TYPE_REGULAR("//label[./span[contains(text(),'Regular')]]"),
    VENDOR_TYPE_ONE_TIME("//label[./span[contains(text(),'One Time')]]"),
    START_DATE_LOCATOR("//div[./div[./input[@formcontrolname='startDateInvite']]]//button[@aria-label='Open calendar']"),
    END_DATE_LOCATOR("//div[./div[./input[@formcontrolname='endDateInvite']]]//button[@aria-label='Open calendar']"),
    CURRENT_DATE("//button[@aria-current='date']"),
    NEXT_MONTH("//button[@aria-label='Next month']"),
    NEXT_MONTH_27("//tbody[@mat-calendar-body]//div[contains(text(),'27')]"),
    CHOOSE_YEAR("//button[@aria-label='Choose month and year']"),
    NEXT_YEAR_ROW_FIRST("//tr[.//button[@aria-current='date']]/following-sibling::tr[1]/td[1]"),
    DECEMBER_MONTH("//button[contains(@aria-label,'December')]"),
    DATE_1("//tr[1]//td[@role='gridcell'][1]"),
    SUBMIT("//span[contains(text(),'Submit')]"),
    YES("//span[contains(text(), 'Yes')]");

    private final String value;

//TODO Constructor
    LVendorInvite(String value) {
        this.value = value;
    }

    public String getSelector() {
        return value;
    }

    public static String getVendorDocumentType(String type) {
        return "//span[contains(text(),'" + type + "')]";
    }

    public static String getRegion(String region) {
        return "//span[contains(text(),'" + region + "')]";
    }

    public static String getStatusLocatorOfVendor(String vendorName) {
        return "//tr[.//td[contains(text(),'" + vendorName + "')]]//td[@data-label='Status']//span";
    }

    public static String getSkillTechnicalExpertise(String skill) {
        return "//mat-option[.//span[contains(text(),'"+skill+"')]]";
    }

    public static String getReferredBy(String referredBy) {
        return "//span[contains(text(),'" + referredBy + "')]";
    }
}