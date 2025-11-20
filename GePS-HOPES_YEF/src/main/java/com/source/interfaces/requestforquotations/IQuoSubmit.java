package com.source.interfaces.requestforquotations;

public interface IQuoSubmit {
    void inviteRegisteredVendor(String type);
    void vendorLogin(String type);
    void liquidatedDamages();
    void rohsCompliance();
    void warrantyRequirements();
    void quotationItems();
    void gst();
    void quotationAttachments();
    int quotationSubmitButton(String type);
}