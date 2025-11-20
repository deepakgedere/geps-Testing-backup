package com.constants.invoices.poinvoice;

public class LInvApproval {

    public static final String INVOICE_NAVIGATION_BAR = "//a[@href='/OrderProcessing/Invoices']";
    public static final String LIST_CONTAINER = "#listContainer tr td";
    public static final String INVOICE_SELECT = ".btn btn-sm btn-link p-0 text-primary";
    public static final String APPROVE_BUTTON = "#btnApprove";
    public static final String ACCEPT_BUTTON = ".bootbox-accept";
    public static final String UPDATE_FINANCE_FIELDS = "#btnfinfields";
    public static final String ACCOUNT_TYPE = "#select2-accountTypeId-container";
    public static final String DOCUMENT_TYPE = "#select2-documentTypeId-container";
    public static final String GENERAL_LEDGER = "#select2-generalLedgerId-container";
    public static final String SAVE_FINANCE_FIELDS = "#updateFinanceFieldsDetails";
    public static final String BANK_ACCOUNT = "#select2-bankAccountId-container";
    public static final String TEXT = "#text";
    public static final String TAX_CODE = "#select2-taxCodeId-container";

//TODO Constructor
    private LInvApproval(){
    }

    public static String getAccountType(String accountType) {
        return "//li[contains(text(), '"+ accountType +"')]";
    }

    public static String getDocumentType(String documentType) {
        return "//li[contains(text(), '"+ documentType +"')]";
    }

    public static String getGeneralLedger(String generalLedger) {
        return "//li[contains(text(), '"+ generalLedger +"')]";
    }

    public static String getBankAccount(String bankAccount) {
        return "//li[contains(text(), '"+ bankAccount +"')]";
    }

    public static String getTaxCode(String taxCode) {
        return "//li[contains(text(), '"+ taxCode +"')]";
    }
}