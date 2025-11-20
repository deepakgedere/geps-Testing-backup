package com.constants.purchaseorderrequests;

public class LPorRevision {

    public static final String POR_NAVIGATION_BAR = "//span[contains(text(), 'Purchase Order Requests')]";
    public static final String REQUEST_REVISION_BUTTON = "#btnCreateRevison";
    public static final String JUSTIFICATION_REQUIRED_TEXT_BOX = ".bootbox-input";
    public static final String ACCEPT_BUTTON = ".bootbox-accept";
    public static final String EDIT_BUTTON = "#btnEdit";
    public static final String ADD_ADDITIONAL_ITEM = "#addServiceLineItems";
    public static final String ITEM_NAME_DROPDOWN = "#select2-ItemName-container";
    public static final String ITEM_NAME_SEARCH = ".select2-search__field";
    public static final String RATE = "#rate";
    public static final String HS_CODE = "#hsCode";
    public static final String MAKE = "#make";
    public static final String MODEL = "#model";
    public static final String PART_NUMBER = "#partnumber";
    public static final String COUNTRY_OF_ORIGIN = "#countryoforigin";
    public static final String LEAD_TIME = "#leadtime";
    public static final String SHIPPING_POINT_DROPDOWN = "#select2-shippingPointId-container";
    public static final String SHIPPING_POINT_SEARCH = ".select2-search__field";
    public static final String RECIPIENT_DROPDOWN = "#recipientName";
    public static final String SUBMIT_BUTTON = "#saveAdditionalServiceItem";
    public static final String UPDATE_BUTTON = "#btnUpdate";


    //TODO Constructor
    private LPorRevision(){
    }

    public static String getTitle(String title){
        return "//span[contains(text(), '"+ title +"')]";
    }

    public static String getItemName(String itemName){
        return "//li[contains(text(), '"+ itemName +"')]";
    }

    public static String getShippingPoint(String shippingPoint){
        return "//li[contains(text(), '"+ shippingPoint +"')]";
    }
}
