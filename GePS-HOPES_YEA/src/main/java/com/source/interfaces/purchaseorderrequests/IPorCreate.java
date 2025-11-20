package com.source.interfaces.purchaseorderrequests;

public interface IPorCreate {
    void porCreateButtonForCatalog(String type, String purchaseType);
    void porCreateButtonForNonCatalog(String type, String purchaseType);
    void justification();
    void taxCode();
    void porNotes();
    int createButton(String type);
    int porCreate(String type, String purchaseType);
}