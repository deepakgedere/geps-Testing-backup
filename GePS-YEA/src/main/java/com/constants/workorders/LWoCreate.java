package com.constants.workorders;

public class LWoCreate {

    public static final String DN_NAVIGATION_BAR = "//*[contains(text(), 'Dispatch Notes')]";
    public static final String LIST_CONTAINER = "#listContainer tr td";
    public static final String DETAILS_BUTTON = ".btn-link";
    public static final String ACTION_DROPDOWN = "#dnActionDropdown";
    public static final String CREATE_WORK_ORDER_BUTTON = "#btnToCreateWorkOrder";
    public static final String FREIGHT_FORWARDER_DROPDOWN = "#select2-freightForwarderId-container";
    public static final String SEARCH_FIELD = ".select2-search__field";
    public static final String PRIORITY_DROPDOWN = "#select2-priority-container";
    public static final String DATE_LOCATOR = "//input[@placeholder=\"Select Date\"]";
    public static final String TODAY = "//span[@class='flatpickr-day today']";
    public static final String DESTINATION_FIELD = "#destinationTermLocationId";
    public static final String PROCEED_BUTTON = "//button[contains(text(), 'Proceed')]";
    public static final String SEND_MAIL_BUTTON = "#vendorSendMailBtnId";
    public static final String ACCEPT_BUTTON = ".bootbox-accept";

// TODO Constructor
    private LWoCreate() {
    }

    public static String getVendor(String id) {
        return "//li[contains(text(), '" + id + "')]";
    }

    public static String getPriority(String priority) {
        return "//li[contains(text(), '" + priority + "')]";
    }
}