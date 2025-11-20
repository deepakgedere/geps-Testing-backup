package com.source.interfaces.vendors;
import com.utils.VendorDetails;

public interface IVendorInvite {
    void clusterHeadLogin();
    void clickVendorsModule();
    void clickFilterByStatus();
    void clickOracleVendor();
    VendorDetails searchAndClickVendor();

    VendorDetails clickDetailsButton();
    void inviteVendor();
    void selectVendorDocumentType();
    void selectSkillTechnicalExpertise();
    void selectRegion();
    void fillFirstProjectName();
    void referredBy();
    void vendorType();
    void clickSubmit();
    void clusterHeadLogout();
}