package com.constants.purchaseorderrequests;

public class LPorCreate {

    public static final String PR_NAVIGATION_BAR = "//span[contains(text(), 'Requisitions')]";
    public static final String SEND_TO_Y_QUOTE_BUTTON = "//button[contains(text(), 'Send to Y-Quote')]";
    public static final String YES_BUTTON = ".bootbox-accept";
    public static final String CONVERT_SM_TO_OM_BUTTON = "//button[contains(text(), 'Convert SM To OM')]";
    public static final String NON_CATALOG_CONVERT_SM_TO_OM_BUTTON = "#convertSMtoOM";
    public static final String DEPARTMENT_PIC_DROP_DOWN = "#select2-departmentPICModelId-container";
    public static final String DEPARTMENT_PIC_SEARCH_FIELD =".select2-search__field";
    public static final String UPDATE_BUTTON = "#reqUpdateSubmitBtn";
    public static final String RFQ_UPDATE_BUTTON = "#rfqUpdateSubmitBtn";
    public static final String QUANTITY_MISMATCH_POPUP = "//h5[contains(text(), 'mismatch')]";
    public static final String SUBMIT_BUTTON = "//button[contains(text(), 'Submit')]";
    public static final String RFQ_NAVIGATION_BAR = "//*[contains(text(), 'Request For Quotations')]";
    public static final String CATALOG_POR_CREATE_BUTTON = "//button[contains(text(), 'Create POR')]";
    public static final String NON_CATALOG_POR_CREATE_BUTTON = "//a[contains(text(),'Create POR')]";
    public static final String BELOW_5L = "#below5L";
    public static final String SOLE_SUPPLIER_NO = "#preselectedNo";
    public static final String TAX_CODE = "-- Select Tax Codes --";
    public static final String POR_NOTES = "#notes";
    public static final String CE_CREATE_BUTTON = "#btnCreateCE";
    public static final String POR_CREATE_BUTTON = "#btnCreate";
    public static final String ADVANCE_PAYMENT_BUTTON = "#addAdvancePayment";
    public static final String ADVANCE_PAYMENT_NAME = "#advancePaymentName";
    public static final String ADVANCE_PAYMENT_PERCENTAGE = "#percentage1";
    public static final String ADVANCE_PAYMENT_CREDIT_PERIOD_IN_DAYS = "#creditPeriod1";
    public static final String SUBMIT_ADVANCE_PAYMENT_BUTTON = "#saveAdvancePayment";
    public static final String MILESTONE_PAYMENT_BUTTON = "#addMileStone";
    public static final String CATALOG_MILESTONE_PAYMENT_NAME = "#description";
    public static final String SD_NON_CATALOG_MILESTONE_PAYMENT_PERCENTAGE = "#description";
    public static final String NON_CATALOG_MILESTONE_PAYMENT_NAME = "#Milestonedescription";
    public static final String MILESTONE_PAYMENT_PERCENTAGE = "#percentage";
    public static final String SUBMIT_MILESTONE_PAYMENT_BUTTON = "#saveMileStone";
    public static final String YES = "//button[contains(text(),'Yes')]";

//TODO Constructor
    private LPorCreate(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '"+ title +"')]";
    }

    public static String getTaxCode(String taxCode){
        return "//li[contains(text(), '"+ taxCode +"')]";
    }

    public static String getDepartmentPic(String departmentPicId){
        return "//li[contains(text(), '"+ departmentPicId +"')]";
    }
}