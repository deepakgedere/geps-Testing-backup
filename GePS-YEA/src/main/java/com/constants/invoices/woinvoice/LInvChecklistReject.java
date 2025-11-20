package com.constants.invoices.woinvoice;

public class LInvChecklistReject {

    public static final String INVOICE_NAVIGATION_BAR = "//a[@href='/OrderProcessing/Invoices']";
    public static final String LIST_CONTAINER = "#listContainer tr";
    public static final String INVOICE_SELECT = ".btn btn-sm btn-link p-0 text-primary";
    public static final String CHECKLIST_BUTTON = "//*[contains(text(), 'Check List')]";
    public static final String REJECT_CHECKLIST_BUTTON = "#addToReviewId";
    public static final String ACCEPT_BUTTON = ".bootbox-accept";

//TODO Constructor
    private LInvChecklistReject(){
    }
    public static String getTitle(String title){
        return "//span[contains(text(), '"+ title +"')]";
    }
}