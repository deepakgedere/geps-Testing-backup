package com.enums.infra.billofquantity.reject;

public enum LBoqReject {

    DETAILS_PAGE("//mat-icon[text()='details']"),
    SEARCH_PR_NUMBER("//input[contains(@class, 'form-control')]"),
    REQUISITION_INFRA_BOQ_REJECT("//span[text()='Purchase Requisitions Infra']"),
    REJECT_BUTTON("button[color='warn']:not([disabled])"),
    REJECTED_STATUS("//span[contains(text(),'BOQRejected')]");

    private final String selector;

//TODO Constructor
    LBoqReject(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}