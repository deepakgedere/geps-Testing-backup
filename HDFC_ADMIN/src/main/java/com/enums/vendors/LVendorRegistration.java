package com.enums.vendors;

public enum LVendorRegistration {

    VENDORS_MODULE("//span[contains(text(), 'Vendors')]"),
    BUTTON_FOR_DETAILS("(//button[.//mat-icon[contains(text(), 'details')]])[1]"),
    EMAIL_ICON("//*[@id=\"cdk-drop-list-0\"]/tbody/tr[1]/td[1]/button[3]/span[1]/mat-icon"),
    EMAIL_LINK("//*[@id=\"mat-dialog-1\"]/app-email-preview/div/form/mat-dialog-content/div[2]/div/quill-editor/div[2]/div[1]/p[9]/text()"),
    SELECT_INITIAL("//span[contains(text(), 'Select initial')]"),
    CONTACT_PERSON("//mat-label[contains(text(), 'Contact Person')]"),
    LANDLINE_NUMBER("//mat-label[contains(text(), 'Landline Number')]"),
    NATURE_OF_TRANSACTION("//div[./span[./label[./mat-label[contains(text(), 'Nature Of Transaction')]]]]"),
    OEM("//mat-label[contains(text(), 'OEM')]"),
    PRODUCT_OR_SERVICE("//mat-label[contains(text(), 'Product/Service')]"),

    ANY_OF_YOUR_RELATIVE_WORKING_IN_HDFC_BANK_YES("//div[./label[contains(text(),'Relative')]]//span[contains(text(),'Yes')]"),
    EMPLOYEE_NAME("//span[contains(text(), 'Employee Name')]"),
    EMPLOYEE_PHONE_NUMBER("//span[contains(text(), 'Employee Phone Number')]"),
    EMPLOYEE_CODE_OR_ID("//span[contains(text(), 'Employee Code/ID')]"),
    EMPLOYEE_EMAIL("//span[contains(text(), 'Employee Email')]"),
    RELATION_WITH_EMPLOYEE("//span[contains(text(), 'Relation With Employee')]"),
    ADD_RELATIVE_BUTTON("//span[contains(text(), 'Add Relatives ')]"),
    VENDOR_DETAILS_UPLOAD_ATTACHMENT("#docfileuploadadditional"),

    VENDOR_LOCATION_EDIT_BUTTON("//table[.//div[text()=' Address ']]//*[contains(text(), 'edit')]"),
    VENDOR_LOCATION_TYPE_DROPDOWN("//*[@id=\"mat-select-value-7\"]"),
    ATTACH_GST_CERTIFICATE("#docfileuploadgst"),
    GST_OPEN_CALENDAR("//input[@formcontrolname='gstINIssuanceDate']"),
    TODAY_DATE("//div[@class='mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-today']"),
    IS_GST_NO("//*[@id=\"mat-radio-5\"]/label/span[1]/span[1]"),
    REMARKS("//mat-label[contains(text(), 'Remark')]"),
    ADD_LOCATIONS_BUTTON("//span[contains(text(), 'Add Locations ')]"),

    HDFC_BANK_ACCOUNT_TYPE_RADIO("//span[text()='HDFC Bank']"),
    HDFC_BANK_ACCOUNT_NUMBER("//input[@formcontrolname='accountNumber']"),
    HDFC_BANK_CONFIRM_ACCOUNT_NUMBER("//mat-label[contains(text(), 'Confirm Account Number')]"),
    HDFC_BANK_ACCOUNT_TYPE("//span[contains(text(), 'Account Type')]"),
    HDFC_BANK_ATTACHMENT("#docfileuploadcbank20"),
    OTHERS_BANK_NAME(" //mat-label[contains(text(), 'Bank Name')]"),
    OTHERS_IFSC_CODE(" //mat-label[contains(text(), 'IFSC Code')]"),
    REASON("//span[contains(text(), 'Reason for not having Hdfc Account')]"),
    OTHERS_BANK_NEFT_ATTACHMENT("#docfileuploadcbank0"),
    OTHERS_BANK_CANCEL_CHEQUE_ATTACHMENT("#docfileuploadcbank20"),

    PAN_HOLDER_NAME("//mat-label[contains(text(), 'PAN Holder Name')]"),
    COMPANY_TYPE("//span[contains(text(), 'Company type')]"),
    ATTACH_PAN_CARD_DOCUMENT("#docfileuploadtax0"),
    QUESTION_NUMBER_TWO_YES("//div[./div[./label[contains(text(),'Composite ')]]]//span[contains(text(),'Yes')]"),
    ATTACH_COMPOSITE_TAX_DOCUMENT("#docfileuploadfeedbacks0"),
    MSME_REGISTRATION_YES("//div[./div[./label[contains(text(),'MSME ACT')]]]//span[contains(text(),'Yes')]"),
    ENTERPRISE_UNDER_MSME("//span[contains(text(), 'Enterprise Under MSME')]"),
    MSME_REGISTRATION_NUMBER("//mat-label[contains(text(), 'MSME Registration Number')]"),
    ATTACH_MSME_CERTIFICATE("//div[./button[.//span[contains(text(),'MSME')]]]/following-sibling::*"),
    MSME_DATE_OPEN_CALENDAR("//input[@formcontrolname='dateofMSMERegistration']"),

    ATTACH_SERVICE_DOCUMENT("#docfileuploadcservice0"),

    SPECIFICATION("//input[@data-placeholder='Specification']"),

    FIRST_NAME("//span[contains(text(), 'First Name')]"),
    LAST_NAME("//span[contains(text(), 'Last Name')]"),
    DESIGNATION("//span[contains(text(), 'Designation')]"),
    PHONE_NUMBER("//span[text()= 'Phone Number']"),
    PASSWORD("//span[contains(text(), 'Password')]"),
    CONFIRM_PASSWORD("//span[contains(text(), 'Confirm Password')]"),

    SUBMIT_BUTTON("//span[contains(text(), 'Submit')]"),
    YES_BUTTON("//button[.//span[contains(text(), 'Yes')]]"),

    APPROVAL_PENDING_STATUS("//div[./div[./div[./h4[text()=' Approvals ']]]]//div/span[contains(text(),'Pending')]"),;

    private final String value;

//TODO Constructor
    LVendorRegistration(String value) {
        this.value = value;
    }

    public String getSelector() {
        return value;
    }

    public static String getInitial(String initial) {
        return "//span[contains(text(), '" + initial + "')]";
    }

    public static String getNatureOfTransaction(String nature) {
        return "//span[contains(text(), '" + nature + "')]";
    }

    public static String getVendorLocationType(String type) {
        return "//span[contains(text(), '" + type + "')]";
    }

    public static String getYesterdayDate(int day) {
        return "//div[@class=\"mat-calendar-body-cell-content mat-focus-indicator\"][contains(text(), '" + day + "')]";
    }

    public static String getAccountType(String type) {
        return "//span[contains(text(), '" + type + "')]";
    }

    public static String getCompanyType(String type) {
        return "//span[contains(text(), '" + type + "')]";
    }

    public static String getEnterpriseUnderMSME(String type) {
        return "//span[contains(text(), '" + type + "')]";
    }

    public static String notifyButtonForVendorName(String vendorName) {
        return "//tr[.//td[(@data-label='Name') and (contains(text(),'"+vendorName+"'))]]//button[@mattooltip='Send Notification']";
    }
    public static String detailsButtonForVendorName(String vendorName) {
        return "//tr[.//td[(@data-label='Name') and (contains(text(),'"+vendorName+"'))]]//button[@aria-label='Button for details']";
    }
}