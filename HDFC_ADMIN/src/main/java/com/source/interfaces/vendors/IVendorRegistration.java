package com.source.interfaces.vendors;

import java.util.List;

public interface IVendorRegistration {
    void superAdminLogin();
    void clickVendorsModule();
    void clicksInviteVendorEmailButton();
    void vendorDetails();
    void vendorDetailsRadioButton();
    void vendorLocationDetails();
    void vendorBankDetails();
    void taxDetails();
    void serviceEscalationMatrix();
    void formFields();
    void vendorLoginDetails();
    void vendorSubmitsVendorRegistrationForm();
    List<String> clusterHeadAssignApprovers();
    void approversApproveVendorRegistration(List<String> approvers);
    void verifyVendorRegistrationFormSubmission();
    void verifyRegistrationIsApproved();
    void registrationFullApprove(String approver);
}