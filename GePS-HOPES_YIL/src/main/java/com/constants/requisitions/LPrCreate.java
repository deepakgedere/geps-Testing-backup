package com.constants.requisitions;

public class LPrCreate {

    public static final String CREATE_BUTTON = "//button[@data-bs-toggle='modal']";
    public static final String TITLE = "//input[@placeholder=\"Please Enter Title\"]";
    public static final String SHIP_TO_YOKOGAWA = "#shipToYokogawa";
    public static final String COMPANY = "#select2-companyId-container";
    public static final String COMPANY_SEARCH = ".select2-search__field";
    public static final String BUSINESS_UNIT = "#select2-businessunitId-container";
    public static final String BUSINESS_UNIT_SEARCH = ".select2-search__field";
    public static final String SALES_REFERENCE_ID = "#crmReference";
    public static final String PROJECT = "#select2-projectId-container";
    public static final String PROJECT_SEARCH = ".select2-search__field";
    public static final String SALES_ORDER = "#select2-salesOrderId-container";
    public static final String SALES_ORDER_SEARCH = ".select2-search__field";
    public static final String SERVICE_ORDER = "#select2-serviceOrder-container";
    public static final String SERVICE_ORDER_SEARCH = ".select2-search__field";
    public static final String BILLABLE_TO_CUSTOMER = "#select2-billableToCustomer-container";
    public static final String BILLABLE_TO_CUSTOMER_SEARCH = ".select2-search__field";
    public static final String CASE_MARKING = "#caseMarking";
    public static final String MESSAGE_TO_SOURCING = "#messageToSourcing";
    public static final String DEPT_PIC = "#select2-departmentPicId-container";
    public static final String DEPT_PIC_SEARCH = ".select2-search__field";
    public static final String WBS = "#select2-wbsId-container";
    public static final String WBS_SEARCH = ".select2-search__field";
    public static final String VENDOR = "#select2-vendorId-container";
    public static final String VENDOR_SEARCH = ".select2-search__field";
    public static final String RATE_CONTRACT = "#select2-rateContractId-container";
    public static final String RATE_CONTRACT_SEARCH = ".select2-search__field";
    public static final String INCOTERM = "//span[contains(text(), '-- Select Incoterm --')]";
    public static final String INCOTERM_SEARCH = ".select2-search__field";
    public static final String SHIPPING_ADDRESS = "#select2-shippingaddressId-container";
    public static final String SHIPPING_ADDRESS_ENDUSERS_OR_OTHERS = "#select2-endusersId-container";
    public static final String SHIPPING_ADDRESS_ENDUSERS_SEARCH = ".select2-search__field";
    public static final String SHIPPING_MODE = "//span[contains(text(), '-- Select Shipping Mode --')]";
    public static final String SHIPPING_MODE_SEARCH = ".select2-search__field";
    public static final String QUOTATION_REQUIRED_BY = "//*[@id='dates']/div[1]/input[2]";
    public static final String EXPECTED_PO_ISSUE_CATALOG = "//*[@id='dates']/div[1]/input[2]";
    public static final String EXPECTED_PO_ISSUE_NON_CATALOG = "//*[@id='dates']/div[2]/input[2]";
    public static final String EXPECTED_DELIVERY_CATALOG = "//*[@id='dates']/div[2]/input[2]";
    public static final String EXPECTED_DELIVERY_NON_CATALOG = "//*[@id='dates']/div[3]/input[2]";
    public static final String TODAY = "(//span[@class=\"flatpickr-day today\"])";
    public static final String DAYS_OF_NEXT_MONTH = "//div[contains(@class,'open')]//div[@class='dayContainer']/span[(contains(@class,'next'))]";
    public static final String DAYS_OF_MONTH = "//div[contains(@class,'open')]//div[@class='dayContainer']/span[not(contains(@class,'next'))]";
    public static final String EXPECTED_PO_ISSUE_LABEL = "//label[contains(text(),'Issue')]";
    public static final String BUYER_MANAGER = "#select2-buyerManagerId-container";
    public static final String BUYER_MANAGER_SEARCH = ".select2-search__field";
    public static final String PROJECT_MANAGER = "#select2-projectManagerId-container";
    public static final String PROJECT_MANAGER_SEARCH = ".select2-search__field";
    public static final String ROHS_COMPLIANCE = "#rohsnotcomplianceid";
    public static final String OI_AND_TP_CURRENCY = "#select2-oiTpCurrencyId-container";
    public static final String OI_AND_TP_CURRENCY_SEARCH = ".select2-search__field";
    public static final String OI_AND_TP_CURRENCY_LABEL = "//label[contains(text(),'Currency')]";
    public static final String ORDER_INTAKE = "#orderintakeid";
    public static final String SALES_ORDER_INTAKE = "#orderintakeId";
    public static final String TARGET_PRICE = "#targetpriceid";
    public static final String SALES_TARGET_PRICE = "#targetpriceId";
    public static final String WARRANTY_REQUIREMENTS = "#select2-warrantyrequirementsid-container";
    public static final String SALES_WARRANTY_REQUIREMENTS = "#select2-warrantyrequirementsId-container";
    public static final String WARRANTY_REQUIREMENTS_SEARCH = ".select2-search__field";
    public static final String PRICE_VALIDITY = "#select2-pricevalidityid-container";
    public static final String SALES_PRICE_VALIDITY = "#select2-pricevalidityId-container";
    public static final String PRICE_VALIDITY_SEARCH = ".select2-search__field";
    public static final String CATALOG_INSPECTION_REQUIRED = "#inspectRequired";
    public static final String NON_CATALOG_INSPECTION_REQUIRED = "#inspectrequired";
    public static final String SALES_NON_CATALOG_INSPECTION_REQUIRED = "#inspectrequiredId";
    public static final String LIQUIDATED_DAMAGES_SELECT = "#isLDStandardNoId";
    public static final String LIQUIDATED_DAMAGES = "#liquidatedamageTextId";
    public static final String TCAS_COMPLIANCE_APPLICABLE = "#tcasApplicable";
    public static final String TCAS_QUESTION_NUMBER = "#q";
    public static final String TCAS_ADD_BUTTON = "#tcasSubmit";
    public static final String TCAS_FILE_UPLOAD_BUTTON = "#tcasFilePreupload";
    public static final String ADD_LINE_ITEM_BUTTON = "#addLineRequisitionItems";
    public static final String CATALOG_ITEMS_DROPDOWN = "#select2-itemId-container";
    public static final String NON_CATALOG_ITEMS_DROPDOWN = "#select2-itemid-container";
    public static final String ITEM_SEARCH = ".select2-search__field";
    public static final String QUANTITY = "#quantity";
    public static final String ITEM_IMPORT_POPUP = "#itemImport";
    public static final String CHOOSE_FILE = "#formFile";
    public static final String UPLOAD_BUTTON = "#btnUpload";
    public static final String SHIPPING_POINT_LOCATOR = "#select2-itemShippingPointId-container";
    public static final String SHIPPING_POINT_SEARCH_FIELD = ".select2-search__field";
    public static final String ADD_ITEM_BUTTON = "#saveRequisitionItem";
    public static final String ITEM_SPECIFICATIONS_TEXT_FIELD_LOCATORS = "//div[@id=\"itemspec-container\"]//input[@type=\"text\"]";
    public static final String ITEM_SPECIFICATIONS_SELECTION_FIELD_LOCATORS = "//div[@id=\"itemspec-container\"]//span[@class=\"select2-selection__rendered\"]";
    public static final String ITEM_SPECIFICATIONS_SELECTION_FIELD_RESULT_LOCATOR = "//span[@class=\"select2-results\"]//li[@class=\"select2-results__option select2-results__option--highlighted\"]";
    public static final String ITEM_SPECIFICATIONS_CHECKBOX_FIELD_LOCATORS = "//div[@id=\"itemspec-container\"]//input[@type=\"checkbox\"]";
    public static final String NOTES = "#notes";
    public static final String ATTACHMENTS = "#attachDocs";
    public static final String FILE_UPLOAD = "#formFilePreupload";
    public static final String EXTERNAL_RADIO_BUTTON = "#radioInActive";
    public static final String ATTACH_FILE_BUTTON = "#saveAttachments1";
    public static final String CONTINUE_BUTTON = "#submitAttachmentsId";
    public static final String CREATE_DRAFT_BUTTON = "//button[contains(text(), 'Create')]";
    public static final String YES = ".bootbox-accept";

//TODO Constructor
    private LPrCreate() {
    }

//TODO Methods to get dynamic locators
    public static String getPsType(String type) {
        return "//a[@href='/Procurement/Requisitions/POC_" + type + "_Create']";
    }

    public static String getSalesPrType(String type) {
        return "//a[@href='/Procurement/Requisitions/Sales_" + type + "_Create']";
    }

    public static String getSdPrType(String type) {
        return "//a[@href='/Procurement/Requisitions/NonPOC_" + type + "_Create']";
    }

    public static String getLocator(String locator) {
        return "//li[contains(text(), \"" + locator + "\")]";
    }
}