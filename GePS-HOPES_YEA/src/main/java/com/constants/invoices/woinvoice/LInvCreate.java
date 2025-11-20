package com.constants.invoices.woinvoice;

public class LInvCreate {

    public static final String INVOICE_NAVIGATION_BAR = "//a[@href='/VendorPortal/Invoices']";
    public static final String INVOICE_SELECT = "//a[@href='/VendorPortal/Invoices/Create']";
    public static final String SELECT_COMPANY_LOCATOR = "#select2-companyId-container";
    public static final String COMPANY_RESULTS_LIST = "#select2-companyId-results";
    public static final String COMPANY_ID_2400 = "2400";
    public static final String COMPANY_ID_5K00 = "5K00";
    public static final String COMPANY_ID_2U00 = "2U00";
    public static final String COMPANY_ID_2W00 = "2W00";
    public static final String SELECT_TYPE = "#select2-typeId-container";
    public static final String SEARCH_FIELD = ".select2-search__field";
    public static final String FOREIGN_CURRENCY_LOCATOR = "#USDsubtotal";
    public static final String PO_LOCATOR = "//li[contains(text(), 'Purchase Order')]";
    public static final String WO_LOCATOR = "//li[contains(text(), 'Work Order')]";
    public static final String SGD_TOTAL_GST_INPUT = "#SGDtotalGSTInput";
    public static final String INVOICE_NUMBER_LOCATOR = "#invoiceNumber";
    public static final String DATE_PLACE_HOLDER = "Select Invoice Date";
    public static final String TODAY = "//span[@class='flatpickr-day today']";
    public static final String FOREGIN_TOTAL_GST = "#USDtotalGST";
    public static final String WO_NUMBER_INPUT = "#select2-woId-container";
    public static final String CURRENCY_CODE = "#currencyCode";
    public static final String RATE_INPUT = "#rate0";
    public static final String GST_LOCATOR = "#USDgstId";
    public static final String DOM_TRIGGER = "document.getElementById('USDgstId').value";
    public static final String SGD_SUB_TOTAL_INPUT = "#SGDsubtotalInput";
    public static final String DOM_TRIGGER_SGD_INPUT = "el => { el.dispatchEvent(new Event('keyup', { bubbles: true })); }";
    public static final String DOM_TRIGGER_GST_INPUT = "el => { el.dispatchEvent(new Event('change', { bubbles: true })); }";
    public static final String DOM_TRIGGER_RATE_INPUT = "el => { el.dispatchEvent(new Event('change', { bubbles: true })); }";
    public static final String DOCUMENT_ID = "#doc1";
    public static final String INVOICE_DOCUMENT_PATH = "documents//Invoice Document.xlsx";
    public static final String CREATE_BUTTON = "#btnCreate";
    public static final String ACCEPT_BUTTON = ".bootbox-accept";

    private LInvCreate(){
    }

    public static String getCompanyId(String companyId){
        return "//li[contains(text(), '" + companyId + "')]";
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '" + title + "')]";
    }

    public static String getWoReferenceId(String woReferenceId){
        return "//li[contains(text(), '" + woReferenceId + "')]";
    }
}