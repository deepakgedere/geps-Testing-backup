package com.constants.workorders;

public class LWoTrackerStatus {

    public static final String WO_NAVIGATION_BAR = "//*[contains(text(), 'Work Orders')]";
    public static final String LIST_CONTAINER = "#listContainer tr td";
    public static final String DETAILS_BUTTON = ".btn-link";
    public static final String WO_REFERENCE_ID = "#referenceId";
    public static final String[] STATUSES = {"Material_Pick_Up", "ETD", "Arrival_Notification", "Import_Clearance", "Out_for_Delivery", "Delivery_Completed"};
    public static final String DATE_PICKER = ".form-control.form-control-sm.flatpickr-custom.form-control.input";
    public static final String TODAY = "//span[@class='flatpickr-day today']";
    public static final String STATUS_CONTAINER = "#select2-statusId-container";
    public static final String SUBMIT_BUTTON = "#btnSubmit";
    public static final String ACCEPT_BUTTON = ".bootbox-accept";

//TODO Constructor
    private LWoTrackerStatus() {
    }

    public static String getStatus(String status) {
        return "//li[contains(text(), '" + status + "')]";
    }
}