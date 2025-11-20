package com.utils;

public class VendorDetails {

    String vendorName;
    String emailId;

    public VendorDetails(String vendorName, String emailId) {
        this.vendorName = vendorName;
        this.emailId = emailId;
    }

    public String getVendorName(){
        return vendorName;
    }
    public String getEmailId() {
        return emailId;
    }
}