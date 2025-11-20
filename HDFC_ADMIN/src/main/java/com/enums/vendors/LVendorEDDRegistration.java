package com.enums.vendors;

public enum LVendorEDDRegistration {

    VENDORS_MODULE("//span[contains(text(), 'Vendors')]"),
    BUTTON_FOR_DETAILS("(//button[.//mat-icon[contains(text(), 'details')]])[1]"),
    EMAIL_LINK("//p[contains(text(),'https')]"),
    SUBMIT_BUTTON("//button/span[contains(text(),'Submit')]"),
    EMAIL_SENT_VALIDATION("//div[contains(text(), 'Email has sent Successfully')]"),
    ROW_COUNT("//table[@role=\"table\"]//tbody//tr"),
    TODAY("//div[@class=\"mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-today\"]"),
    SUBMIT("//span[contains(text(), \"Submit\")]"),
    YES("//span[contains(text(), \"Yes\")]"),
    SUCCESS_MESSAGE("//*[contains(text(),'Document Submitted Successfully!')]"),
    APPROVE_BUTTON("//button[@color='primary'][./*[text()=' Approve ']]"),
    SUBMIT_BUTTON_FOR_REMARKS("//button[@color='primary']//*[text()=' Submit ']"),
    APPROVAL_PENDING_STATUS("//div[./div[./div[./h4[contains(text(),'Vendor Approvals')]]]]//div/span[contains(text(),'Pending')]");

    private final String value;

//TODO Constructor
    LVendorEDDRegistration(String value) {
        this.value = value;
    }

    public String getSelector() {
        return value;
    }

    public static String getAttachFileRowCount(int index) {
        return "(//td[contains(@class,'Action')]//input[2])["+index+"]";
    }

    public static String getStartDate(int index) {
        return "(//td[contains(@class,'StartDate')]//button)["+index+"]";
    }

    public static String getEndDate(int index) {
        return "(//td[contains(@class,'EndDate')]//button)["+index+"]";
    }

    public static String getRemarks(int index) {
        return "//table[@role=\"table\"]//tbody//tr[" + index + "]//td[8]//input[@data-placeholder=\"Enter remarks\"]";
    }

    public static String detailsButtonForVendorName(String vendorName) {
        return "//tr[.//td[(@data-label='Name') and (contains(text(),'"+vendorName+"'))]]//button[@aria-label='Button for details']";
    }

    public static String myAppprovalsDetailsButtonForVendorName(String vendorName) {
        return "//tr[.//td[(contains(text(),'"+vendorName+"'))]]//button[@mattooltip='view details']";
    }
    public static String notifyButtonForVendorName(String vendorName) {
        return "//tr[.//td[(@data-label='Name') and (contains(text(),'"+vendorName+"'))]]//button[@mattooltip='Send Vendor Due Diligence and Empanelment Notification']";
    }
}
