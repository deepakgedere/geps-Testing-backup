package com.constants.invoices.poinvoice;

public class LInvCreate {

    public static final String INVOICE_NAVIGATION_BAR = "//a[@href='/VendorPortal/Invoices']";
    public static final String INVOICE_SELECT = "//a[@href='/VendorPortal/Invoices/Create']";
    public static final String SELECT_COMPANY_LOCATOR = "#select2-companyId-container";
    public static final String COMPANY_RESULTS_LIST = "#select2-companyId-results";
    public static final String LIST_CONTAINER = "#listContainer tr";
    public static final String PO_REFERENCE_ID = "td:nth-child(3)";
    public static final String COMPANY_ID_2400 = "2400";
    public static final String COMPANY_ID_5K00 = "5K00";
    public static final String COMPANY_ID_2U00 = "2U00";
    public static final String COMPANY_ID_2W00 = "2W00";
    public static final String SELECT_TYPE = "#select2-typeId-container";
    public static final String SEARCH_FIELD = ".select2-search__field";
    public static final String PO_LOCATOR = "//li[contains(text(), 'Purchase Order')]";
    public static final String INVOICE_NUMBER_LOCATOR = "#invoiceNumber";
    public static final String DATE_PLACE_HOLDER = "Select Invoice Date";
    public static final String TODAY = "//span[@class='flatpickr-day today']";
    public static final String PO_NUMBER_INPUT = "#select2-poId-container";
    public static final String CURRENCY_CODE = "#currencyCode";
    public static final String GST_LOCATOR = "#USDgstId";
    public static final String FOREIGN_CURRENCY_LOCATOR = "#USDsubtotal";
    public static final String FOREGIN_TOTAL_GST = "#USDtotalGST";
    public static final String DOM_TRIGGER = "document.getElementById('USDgstId').value";
    public static final String SGD_SUB_TOTAL_INPUT = "#SGDsubtotalInput";
    public static final String SGD_TOTAL_GST_INPUT = "#SGDtotalGSTInput";
    public static final String DOM_TRIGGER_SGD_INPUT = "el => { el.dispatchEvent(new Event('keyup', { bubbles: true })); }";
    public static final String DOCUMENT_ID = "#doc1";
    public static final String INVOICE_DOCUMENT_PATH = "documents\\Invoice Document.xlsx";
    public static final String CREATE_BUTTON = "#btnCreate";
    public static final String ACCEPT_BUTTON = ".bootbox-accept";
    public static final String ADVANCEPAYMENT_AND_MILESTONEPAYMENT_BUTTON = "#advpayinvdetailsOpenPopUp";
    public static final String ADVANCEPAYMENT_CHECKBOX = "#isAdvancePymentChecked";
    public static final String MILESTONEPAYMENT_CHECKBOX = "#isMileStoneChecked-";
    public static final String SAVE_BUTTON = "#saveAdvancePaymentMilePO";


//TODO Constructor
    private LInvCreate(){
    }

    public static String get2400Id(){
        return "//li[contains(text(), '2400')]";
    }

    public static String get5K00Id(){
        return "//li[contains(text(), '5K00')]";
    }

    public static String get2U00Id(){
        return "//li[contains(text(), '2U00')]";
    }

    public static String get2W00Id(){
        return "//li[contains(text(), '2W00')]";
    }

    public static String getPoReferenceId(String poReferenceId){
        return "//li[text()='" + poReferenceId + "']";
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '" + title + "')]";
    }

}