package com.enums.infra.invoices.create;

public enum LInvCreate {

    INVOICE_LIST_CONTAINER(".example-table-container"),

    INVOICE_NAVIGATION_BAR("//a[@href='/VendorPortal/Invoices']"),
    INVOICE_SELECT("//a[@href='/VendorPortal/Invoices/Create']"),
    SELECT_COMPANY_LOCATOR("#select2-companyId-container"),
    COMPANY_RESULTS_LIST("#select2-companyId-results"),
    LIST_CONTAINER("#listContainer tr"),
    PO_REFERENCE_ID("td:nth-child(3)"),
    COMPANY_ID_2400("2400"),
    COMPANY_ID_5K00("5K00"),
    COMPANY_ID_2U00("2U00"),
    COMPANY_ID_2W00("2W00"),
    SELECT_TYPE("#select2-typeId-container"),
    SEARCH_FIELD(".select2-search__field"),
    PO_LOCATOR("//li[contains(text(), 'Purchase Order')]"),
    INVOICE_NUMBER_LOCATOR("#invoiceNumber"),
    DATE_PLACE_HOLDER("Select Invoice Date"),
    TODAY("//span[@class='flatpickr-day today']"),
    PO_NUMBER_INPUT("#select2-poId-container"),
    CURRENCY_CODE("#currencyCode"),
    GST_LOCATOR("#USDgstId"),
    FOREIGN_CURRENCY_LOCATOR("#USDsubtotal"),
    FOREGIN_TOTAL_GST("#USDtotalGST"),
    DOM_TRIGGER("document.getElementById('USDgstId').value"),
    SGD_SUB_TOTAL_INPUT("#SGDsubtotalInput"),
    SGD_TOTAL_GST_INPUT("#SGDtotalGSTInput"),
    DOM_TRIGGER_SGD_INPUT("el => { el.dispatchEvent(new Event('keyup', { bubbles: true })); }"),
    DOCUMENT_ID("#doc1"),
    INVOICE_DOCUMENT_PATH("documents\\Invoice Document.xlsx"),
    CREATE_BUTTON("#btnCreate"),
    ACCEPT_BUTTON(".bootbox-accept"),
    ADVANCEPAYMENT_AND_MILESTONEPAYMENT_BUTTON("#advpayinvdetailsOpenPopUp"),
    ADVANCEPAYMENT_CHECKBOX("#isAdvancePymentChecked"),
    MILESTONEPAYMENT_CHECKBOX("#isMileStoneChecked-"),
    SAVE_BUTTON("#saveAdvancePaymentMilePO");

    private final String selector;

//TODO Constructor
    LInvCreate(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }

    public static String getSelector(String selector) {
        return "//span[contains(text(), '" + selector + "')]";
    }
}