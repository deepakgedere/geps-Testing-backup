package com.constants.requestforquotations;

public class LQuoSubmit {

    public static final String RFQ_NAVIGATION_BAR = "//span[contains(text(), 'Request For Quotations')]";
    public static final String INVITE_VENDORS = "#addRequestForQuotationVendors";
    public static final String VENDOR_SEARCH_FIELD = "#select2-vendorId-container";
    public static final String VENDOR_SEARCH = ".select2-search__field";
    public static final String INVITE_BUTTON = "#saveRequestForQuotationVendor";
    public static final String VENDOR_EMAIL_POP_UP = "#vendorSendMailBtnId";
    public static final String SEND_QUOTE_BUTTON = "#btnSendQuote";
    public static final String INCOTERM_LOCATION = "#incotermLocation";
    public static final String QUOTATION_REFERENCE_NUMBER = "#quotationReferenceNumber";
    public static final String VALIDITY_DATE = "#dates";
    public static final String TODAY = "//span[@class='flatpickr-day today']";
    public static final String FIRST_DAY_OF_NEXT_MONTH = ".flatpickr-day.nextMonthDay";
    public static final String LIQUIDATED_DAMAGES = "#liquidatedComplyId";
    public static final String ROHS_COMPLIANCE = "#rohsComplyId";
    public static final String WARRANTY_REQUIREMENTS = "#warrantyRequirementsComplyId";
    public static final String RFQ_ITEM_LIST = "#rfqVendorItems-container tr td label[id^='lineNumber-']";
    public static final String HS_CODE = "#hsCode-";
    public static final String MAKE = "#make-";
    public static final String MODEL = "#model-";
    public static final String PART_NUMBER = "#partNumber-";
    public static final String COUNTRY_OF_ORIGIN = "#countryOfOrigin-";
    public static final String RATE = "#rate-";
    public static final String DISCOUNT = "#discount-";
    public static final String LEAD_TIME = "#leadTime-";
    public static final String QUOTATION_NOTES = "#notes-";
    public static final String GST = "#gstId";
    public static final String ATTACH_FILE = "#attachFile";
    public static final String FILE_INPUT = "#formFilePreupload";
    public static final String ATTACHMENT_TYPE_DROPDOWN = "#select2-attachmentTypeId-container";
    public static final String SAVE_ATTACHMENTS = "#attachmentSaveId";
    public static final String CREATE_BUTTON = "#btnCreate";
    public static final String ACCEPT_BUTTON_LOCATOR = ".bootbox-accept";

    private LQuoSubmit(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '"+ title +"')]";
    }

    public static String getVendor(String vendor){
        return "//li[contains(text(), '"+ vendor +"')]";
    }

    public static String getThirtyOne(int num){
        return "//span[contains(text(), '" + num + "')]";
    }

    public static String getNextDay(int num){
        return "//span[contains(text(), '" + num + "')]";
    }

    public static String getAttatmentType(String type){
        return "//li[contains(text(), '" + type + "')]";
    }
}