package com.source.classes.requisitions.create;

import com.enums.Types;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.requisitions.IPrCreate;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

import static com.constants.requisitions.LPrCreate.*;
import static com.constants.requisitions.LPunchout.*;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class Create implements IPrCreate {

    Logger logger;
    PlaywrightFactory playwrightFactory;
    ObjectMapper objectMapper;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    JsonNode jsonNode;
    double randomNumber;
    String appUrl;
    String projectId;
    int vendorId = 0;
    String companyIdForSales;
    String salesOrderId;
    String companyIdForSD;

    //TODO Constructor
    public Create(PlaywrightFactory playwrightFactory, ObjectMapper objectMapper, ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout) {
        this.logger = LoggerUtil.getLogger(Create.class);
        this.playwrightFactory = playwrightFactory;
        this.objectMapper = objectMapper;
        this.page = page;
        this.jsonNode = jsonNode;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public void requesterLoginPRCreate() {
        try {
            String emailId = jsonNode.get("mailIds").get("requesterEmail").asText();
            iLogin.performLogin(emailId);
        } catch (Exception exception) {
            logger.error("Exception in Requester Login Function: {}", exception.getMessage());
        }
    }

    public void createButton() {
        try {
            Locator createButton = page.locator(CREATE_BUTTON);
            locatorVisibleHandler(createButton);
            createButton.click();
        } catch (Exception exception) {
            logger.error("Exception in Create Button Function: {}", exception.getMessage());
        }
    }

    public void purchaseType(String type, String purchaseType) {
        try {
            String prTypeLocator = "";
            String getType = Types.getType(type);
            if (purchaseType.equalsIgnoreCase("Punchout")) {
                prTypeLocator = getPunchoutUrl(type);
            } else {
                switch (getType) {
                    case "Sales" -> prTypeLocator = getSalesPrType(purchaseType);
                    case "PS" -> prTypeLocator = getPsType(purchaseType);
                    case "SD" -> prTypeLocator = getSdPrType(purchaseType);
                }
            }

            Locator prType = page.locator(prTypeLocator);
            locatorVisibleHandler(prType);
            prType.click();
        } catch (Exception exception) {
            logger.error("Exception in Purchase Type Function: {}", exception.getMessage());
        }
    }

    public void catalogTypeHandler(String type, String purchaseType) {
        try {
            if (purchaseType.equalsIgnoreCase("Catalog BOP2/BOP5")) {
                Locator bop2bop5Locator = page.locator(BOP2BOP5_RADIO_BUTTON);
                locatorVisibleHandler(bop2bop5Locator);
                bop2bop5Locator.click();
            }
        } catch (Exception exception) {
            logger.error("Exception in BOP2/BOP5 Radio Button Function: {}", exception.getMessage());
        }
    }

    public void punchoutCreate() {
        try {
            int timeout = 10000;

            Locator bechtleVendorLocator = page.locator(BECHTLE_PUNCHOUT_VENDOR_LOCATOR);
            page.waitForSelector(BECHTLE_PUNCHOUT_VENDOR_LOCATOR, new Page.WaitForSelectorOptions().setTimeout(timeout));
            locatorVisibleHandler(bechtleVendorLocator, timeout);
            bechtleVendorLocator.first().click();

            Locator acceptAllButton = page.locator(ACCEPT_ALL_BUTTON);
            page.waitForSelector(ACCEPT_ALL_BUTTON, new Page.WaitForSelectorOptions().setTimeout(timeout));
            locatorVisibleHandler(acceptAllButton, timeout);
            acceptAllButton.click();

            Locator productLink = page.locator(PRODUCT_LINK);
            page.waitForSelector(PRODUCT_LINK, new Page.WaitForSelectorOptions().setTimeout(timeout));
            locatorVisibleHandler(productLink, timeout);
            productLink.first().click();

            Locator addToBasket = page.locator(ADD_TO_BASKET);
            page.waitForSelector(ADD_TO_BASKET, new Page.WaitForSelectorOptions().setTimeout(timeout));
            locatorVisibleHandler(addToBasket, timeout);
            addToBasket.first().click();

            Locator viewCart = page.locator(VIEW_CART);
            page.waitForSelector(VIEW_CART, new Page.WaitForSelectorOptions().setTimeout(timeout));
            locatorVisibleHandler(viewCart, timeout);
            viewCart.first().click();

            Locator checkout = page.locator(CHECKOUT);
            page.waitForSelector(CHECKOUT, new Page.WaitForSelectorOptions().setTimeout(timeout));
            locatorVisibleHandler(checkout, timeout);
            checkout.first().click();
        } catch (Exception exception) {
            logger.error("Exception in Punchout Create Function: {}", exception.getMessage());
        }
    }

    public void title(String type, String purchaseType) {
        try {
            String getTitle = jsonNode.get("requisition").get("orderTitle").asText();
            randomNumber = Math.round(Math.random() * 1000);
            String title = type.toUpperCase() + getTitle + "-" + purchaseType.toUpperCase() + "-" + randomNumber;
            Locator titleLocator = page.locator(TITLE);
            locatorVisibleHandler(titleLocator);
            titleLocator.fill(title);

            String getType = "";

            if (type.equalsIgnoreCase("PS")) {
                if (purchaseType.equalsIgnoreCase("catalog") || (purchaseType.equalsIgnoreCase("catalog bop2/bop5"))) {
                    getType = "psCatalogTitle";
                } else if (purchaseType.equalsIgnoreCase("punchout")) {
                    getType = "punchOutCatalogTitle";
                } else {
                    getType = "psNonCatalogTitle";
                }
            } else if (type.equalsIgnoreCase("Sales")) {
                getType = purchaseType.equalsIgnoreCase("catalog") ? "salesCatalogTitle" : "salesNonCatalogTitle";
            } else {
                if (purchaseType.equalsIgnoreCase("catalog") || (purchaseType.equalsIgnoreCase("catalog bop2/bop5"))) {
                    getType = "sdCatalogTitle";
                } else if (purchaseType.equalsIgnoreCase("punchout")) {
                    getType = "punchOutCatalogTitle";
                } else {
                    getType = "sdNonCatalogTitle";
                }
            }

            playwrightFactory.savePropertiesIntoJsonFile("requisition", getType, title);
        } catch (Exception exception) {
            logger.error("Exception in Title Function: {}", exception.getMessage());
        }
    }

    public void shipToYokogawa() {
        try {
            String value = jsonNode.get("requisition").get("shipToYokogawa").asText().toLowerCase();
            if (value.equals("no")) {
                Locator shipToYokogawa = page.locator(SHIP_TO_YOKOGAWA);
                locatorVisibleHandler(shipToYokogawa);
                shipToYokogawa.click();
            }
        } catch (Exception exception) {
            logger.error("Exception in Ship To Yokogawa Function: {}", exception.getMessage());
        }
    }

    public List<String> project() {
        List<String> wbsValues = new ArrayList<>();
        try {
            String projectCodeValue = jsonNode.get("requisition").get("projectCode").asText();

            Locator project = page.locator(PROJECT);
            locatorVisibleHandler(project);
            project.click();

            Locator projectValue = page.locator(PROJECT_SEARCH);
            locatorVisibleHandler(projectValue);
            projectValue.fill(projectCodeValue);

            String projectSelectLocator = getLocator(projectCodeValue);
            Locator projectSelect = page.locator(projectSelectLocator);
            locatorVisibleHandler(projectSelect);
            projectSelect.click();

            APIResponse projectResponse = page.request().fetch(appUrl + "/api/Projects/searchByUserId?keyword=" + projectCodeValue, RequestOptions.create());
            JsonNode projectCodeJson = objectMapper.readTree(projectResponse.body());
            JsonNode firstProjectObject = projectCodeJson.get(0);
            projectId = firstProjectObject.get("id").asText();
            String companyId = firstProjectObject.get("companyId").asText();

            APIResponse wbsResponse = page.request().fetch(appUrl + "/api/workBreakdownStructures/search?projectid=" + projectId + "&companyId=" + companyId, RequestOptions.create());
            JsonNode wbsCodeJson = objectMapper.readTree(wbsResponse.body());

            for (JsonNode wbs : wbsCodeJson) {
                if (wbs.has("text")) {
                    wbsValues.add(wbs.get("text").asText());
                }
            }
        } catch (Exception exception) {
            logger.error("Exception in Project Function: {}", exception.getMessage());
        }
        return wbsValues;
    }

    public List<String> salesOrder(String purchaseType, int soNumber, String type) {
        List<String> serviceOrders = new ArrayList<>();
        String salesOrderValue = jsonNode.get("requisition").get("salesOrder").asText();
        try {
            Locator salesOrder;
            if (purchaseType.equalsIgnoreCase("Catalog BOP2/BOP5")) {
                salesOrder = page.locator(BOP2BOP5_SALES_ORDER);
            } else {
                salesOrder = page.locator(SALES_ORDER);
            }
            locatorVisibleHandler(salesOrder);
            salesOrder.click();

            Locator salesOrderLocator = page.locator(SALES_ORDER_SEARCH);
            String salesOrderSelectLocator;
            locatorVisibleHandler(salesOrderLocator);
            if (purchaseType.equalsIgnoreCase("catalog bop2/bop5")) {
                salesOrderLocator.fill(String.valueOf(soNumber));
                salesOrderSelectLocator = getLocator(String.valueOf(soNumber));
            } else {
                salesOrderLocator.fill(salesOrderValue);
                salesOrderSelectLocator = getLocator(salesOrderValue);
            }

            if (type.equalsIgnoreCase("SD")) {
                APIResponse salesOrderResponse = page.request().fetch(appUrl + "/api/SalesOrders/searchByCompany?keyword=" + salesOrderValue, RequestOptions.create());
                JsonNode salesOrderJson = objectMapper.readTree(salesOrderResponse.body());
                JsonNode firstSalesOrderObject = salesOrderJson.get(0);
                companyIdForSD = firstSalesOrderObject.get("companyId").asText();
            }

            Locator salesOrderSelect = page.locator(salesOrderSelectLocator);
            locatorVisibleHandler(salesOrderSelect);
            salesOrderSelect.click();

            APIResponse salesOrderResponse = page.request().fetch(appUrl + "/api/SalesOrders/search?keyword=" + salesOrderValue, RequestOptions.create());
            JsonNode salesOrderJson = objectMapper.readTree(salesOrderResponse.body());
            JsonNode firstSalesOrderObject = salesOrderJson.get(0);
            salesOrderId = firstSalesOrderObject.get("id").asText();
            String companyId = firstSalesOrderObject.get("companyId").asText();

            APIResponse departmentPicResponse = page.request().fetch(appUrl + "/api/DepartmentPic/search?salesOrderId=" + salesOrderId, RequestOptions.create());
            JsonNode departmentPicJson = objectMapper.readTree(departmentPicResponse.body());

            for (JsonNode departmentPic : departmentPicJson) {
                if (departmentPic.has("text")) {
                    String departmentPicMail = departmentPic.get("text").asText();
                    playwrightFactory.savePropertiesIntoJsonFile("requisition", "sdDepartmentPic", departmentPicMail);
                    break;
                }
            }

            APIResponse serviceOrderResponse = page.request().fetch(appUrl + "/api/ServiceOrders/search?salesOrderId=" + salesOrderId, RequestOptions.create());
            JsonNode serviceOrderJson = objectMapper.readTree(serviceOrderResponse.body());

            for (JsonNode serviceOrder : serviceOrderJson) {
                if (serviceOrder.has("text")) {
                    serviceOrders.add(serviceOrder.get("text").asText());
                }
            }
        } catch (Exception exception) {
            logger.error("Exception in Sales Order Function: {}", exception.getMessage());
        }
        return serviceOrders;
    }

    public void svoItemNumber() {
        try {
            String svoItemNumber = jsonNode.get("requisition").get("svoItemNumber").asText();

            Locator svoItemNumberLocator = page.locator(SVO_ITEM_NUMBER);
            locatorVisibleHandler(svoItemNumberLocator);
            svoItemNumberLocator.fill(svoItemNumber);
        } catch (Exception exception) {
            logger.error("Exception in SVO Item Number Function: {}", exception.getMessage());
        }
    }

    public void serviceOrder(List<String> serviceOrders) {
        try {
            String serviceOrdersFromProperties = jsonNode.get("requisition").get("serviceOrder").asText();

            for (String getServiceOrders : serviceOrders) {
                if (getServiceOrders.equals(serviceOrdersFromProperties)) {
                    Locator serviceOrderLocator = page.locator(SERVICE_ORDER);
                    locatorVisibleHandler(serviceOrderLocator);
                    serviceOrderLocator.click();

                    Locator serviceOrderSearch = page.locator(SERVICE_ORDER_SEARCH);
                    locatorVisibleHandler(serviceOrderSearch);
                    serviceOrderSearch.fill(serviceOrdersFromProperties);

                    String serviceOrderSelectLocator = getLocator(serviceOrdersFromProperties);
                    Locator serviceOrderSelect = page.locator(serviceOrderSelectLocator);
                    locatorVisibleHandler(serviceOrderSelect);
                    serviceOrderSelect.click();
                    break;
                }
            }
        } catch (Exception exception) {
            logger.error("Exception in Service Order Function: {}", exception.getMessage());
        }
    }

    public void departmentPic(String type) {
        try {
            String departmentPic = jsonNode.get("requisition").get("departmentPic").asText();

            Locator departmentPicLocator;
            if (type.equalsIgnoreCase("Sales")) {
                departmentPicLocator = page.locator(SALES_DEPT_PIC);
            } else {
                departmentPicLocator = page.locator(DEPT_PIC);
            }
            locatorVisibleHandler(departmentPicLocator);
            departmentPicLocator.click();

            Locator departmentPicSearch = page.locator(DEPT_PIC_SEARCH);
            locatorVisibleHandler(departmentPicSearch);
            departmentPicSearch.fill(departmentPic);

            String departmentPicSelectLocator = getLocator(departmentPic);
            Locator departmentPicSelect = page.locator(departmentPicSelectLocator);
            locatorVisibleHandler(departmentPicSelect);
            departmentPicSelect.click();
        } catch (Exception exception) {
            logger.error("Exception in Department PIC Function: {}", exception.getMessage());
        }
    }

    public void purchaseGroup() {
        try {
            String purchaseGroup = jsonNode.get("requisition").get("purchaseGroup").asText();

            Locator purchaseGroupLocator = page.locator(PURCHASE_GROUP);
            locatorVisibleHandler(purchaseGroupLocator);
            purchaseGroupLocator.fill(purchaseGroup);
        } catch (Exception exception) {
            logger.error("Exception in Purchase Group Function: {}", exception.getMessage());
        }
    }

    public void poType() {
        try {
            String poType = jsonNode.get("requisition").get("poType").asText();

            Locator poTypeLocator = page.locator(PO_TYPE);
            locatorVisibleHandler(poTypeLocator);
            poTypeLocator.click();

            Locator poTypeSearch = page.locator(PO_TYPE_SEARCH);
            locatorVisibleHandler(poTypeSearch);
            poTypeSearch.fill(poType);

            String poTypeSelectLocator = getLocator(poType);
            Locator poTypeSelect = page.locator(poTypeSelectLocator);
            locatorVisibleHandler(poTypeSelect);
            poTypeSelect.click();
        } catch (Exception exception) {
            logger.error("Exception in PO Type Function: {}", exception.getMessage());
        }
    }

    public void wbs(List<String> wbs) {
        try {
            String wbsFromProperties = jsonNode.get("requisition").get("wbsCode").asText();

            for (String getWbs : wbs) {
                if (getWbs.equals(wbsFromProperties)) {
                    Locator wbsLocator = page.locator(WBS);
                    locatorVisibleHandler(wbsLocator);
                    wbsLocator.click();

                    Locator wbsSearch = page.locator(WBS_SEARCH);
                    locatorVisibleHandler(wbsSearch);
                    wbsSearch.fill(wbsFromProperties);

                    String wbsSelectLocator = getLocator(wbsFromProperties);
                    Locator wbsSelect = page.locator(wbsSelectLocator);
                    locatorVisibleHandler(wbsSelect);
                    wbsSelect.click();
                    break;
                }
            }
        } catch (Exception exception) {
            logger.error("Exception in WBS Function: {}", exception.getMessage());
        }
    }

    public void company() {
        try {
            String company = jsonNode.get("requisition").get("companyName").asText();

            Locator companyLocator = page.locator(COMPANY);
            locatorVisibleHandler(companyLocator);
            companyLocator.click();

            Locator companySearch = page.locator(COMPANY_SEARCH);
            locatorVisibleHandler(companySearch);
            companySearch.fill(company);

            APIResponse companyResponse = page.request().fetch(appUrl + "/api/CompaniesByUserId/search?keyword=" + company, RequestOptions.create());
            JsonNode companyJson = objectMapper.readTree(companyResponse.body());
            JsonNode firstCompanyObject = companyJson.get(0);
            companyIdForSales = firstCompanyObject.get("id").asText();

            String companySelectLocator = getLocator(company);
            Locator companySelect = page.locator(companySelectLocator);
            locatorVisibleHandler(companySelect);
            companySelect.click();
        } catch (Exception exception) {
            logger.error("Exception in Company Function: {}", exception.getMessage());
        }
    }

    public void billableToCustomer() {
        try {
            String billableTocustomer = jsonNode.get("requisition").get("billableTocustomer").asText();

            Locator billableTocustomerLocator = page.locator(BILLABLE_TO_CUSTOMER);
            locatorVisibleHandler(billableTocustomerLocator);
            billableTocustomerLocator.click();

            Locator billableTocustomerSearch = page.locator(BILLABLE_TO_CUSTOMER_SEARCH);
            locatorVisibleHandler(billableTocustomerSearch);
            billableTocustomerSearch.fill(billableTocustomer);

            String billableTocustomerSelectLocator = getLocator(billableTocustomer);
            Locator billableTocustomerSelect = page.locator(billableTocustomerSelectLocator);
            locatorVisibleHandler(billableTocustomerSelect);
            billableTocustomerSelect.click();
        } catch (Exception exception) {
            logger.error("Exception in Billable To Customer Function: {}", exception.getMessage());
        }
    }

    public void caseMarking() {
        try {
            String caseMarking = jsonNode.get("requisition").get("caseMarking").asText();

            Locator caseMarkingLocator = page.locator(CASE_MARKING);
            locatorVisibleHandler(caseMarkingLocator);
            caseMarkingLocator.click();
            caseMarkingLocator.fill(caseMarking);
        } catch (Exception exception) {
            logger.error("Exception in Case Marking Function: {}", exception.getMessage());
        }
    }

    public void messageToSourcing() {
        try {
            String messageToSourcing = jsonNode.get("requisition").get("messageToSourcing").asText();

            Locator messageToSourcingLocator = page.locator(MESSAGE_TO_SOURCING);
            locatorVisibleHandler(messageToSourcingLocator);
            messageToSourcingLocator.click();
            messageToSourcingLocator.fill(messageToSourcing);
        } catch (Exception exception) {
            logger.error("Exception in Message To Sourcing Function: {}", exception.getMessage());
        }
    }

    public void businessUnit() {
        try {
            String businessUnit = jsonNode.get("requisition").get("businessUnit").asText();

            Locator businessUnitLocator = page.locator(BUSINESS_UNIT);
            locatorVisibleHandler(businessUnitLocator);
            businessUnitLocator.click();

            Locator businessUnitSearch = page.locator(BUSINESS_UNIT_SEARCH);
            locatorVisibleHandler(businessUnitSearch);
            businessUnitSearch.fill(businessUnit);

            String businessUnitSelectLocator = getLocator(businessUnit);
            Locator businessUnitSelect = page.locator(businessUnitSelectLocator);
            locatorVisibleHandler(businessUnitSelect);
            businessUnitSelect.click();
        } catch (Exception exception) {
            logger.error("Exception in Business Unit Function: {}", exception.getMessage());
        }
    }

    public void salesReferenceId(String purchaseType, String type) {
        try {
            String salesReferenceId = jsonNode.get("requisition").get("salesReferenceId").asText();

            Locator salesReferenceIdLocator;
            if (purchaseType.equalsIgnoreCase("Catalog BOP2/BOP5") && type.equalsIgnoreCase("SD")) {
                salesReferenceIdLocator = page.locator("#select2-crmReference-container");
            } else if (purchaseType.equalsIgnoreCase("Punchout") && type.equalsIgnoreCase("Sales")) {
                salesReferenceIdLocator = page.locator(SALES_PUNCHOUT_REFERENCE_ID);
            } else {
                salesReferenceIdLocator = page.locator(SALES_REFERENCE_ID);
            }

            if (purchaseType.equalsIgnoreCase("Catalog BOP2/BOP5") && type.equalsIgnoreCase("SD")) {
                locatorVisibleHandler(salesReferenceIdLocator);
                salesReferenceIdLocator.click();
                page.locator("//ul[@role='listbox']//li[1]").click();
            } else {
                locatorVisibleHandler(salesReferenceIdLocator);
                salesReferenceIdLocator.fill(salesReferenceId);
            }
        } catch (Exception exception) {
            logger.error("Exception in Sales Reference Id Function: {}", exception.getMessage());
        }
    }

    public void incoterm() {
        try {
            Locator incotermLocator = page.locator(INCOTERM);
            locatorVisibleHandler(incotermLocator);
            incotermLocator.click();

            String incotermValue = jsonNode.get("requisition").get("incoterm").asText();
            Locator incotermSearch = page.locator(INCOTERM_SEARCH);
            locatorVisibleHandler(incotermSearch);
            incotermSearch.fill(incotermValue);

            String incotermOptionLocator = getLocator(incotermValue);
            Locator incotermSelect = page.locator(incotermOptionLocator);
            locatorVisibleHandler(incotermSelect);
            incotermSelect.click();
        } catch (Exception exception) {
            logger.error("Exception in Incoterm Function: {}", exception.getMessage());
        }
    }

    public void liquidatedDamages() {
        try {
            Locator liquidatedDamagesLocator = page.locator(LIQUIDATED_DAMAGES);
            locatorVisibleHandler(liquidatedDamagesLocator);
            liquidatedDamagesLocator.click();

            String liquidatedDamagesValue = jsonNode.get("requisition").get("liquidatedDamages").asText();
            Locator liquidatedDamagesSearch = page.locator(LIQUIDATED_DAMAGES_SEARCH);
            locatorVisibleHandler(liquidatedDamagesSearch);
            liquidatedDamagesSearch.fill(liquidatedDamagesValue);

            String liquidatedDamagesOptionLocator = getLocator(liquidatedDamagesValue);
            Locator liquidatedDamagesSelect = page.locator(liquidatedDamagesOptionLocator);
            locatorVisibleHandler(liquidatedDamagesSelect);
            liquidatedDamagesSelect.click();
        } catch (Exception exception) {
            logger.error("Exception in Liquidated Damages Function: {}", exception.getMessage());
        }
    }

    public void warrantyRequirements(String type) {
        try {
            String warrantyRequirement = jsonNode.get("requisition").get("warrantyRequirement").asText();

            if (type.equalsIgnoreCase("PS") || type.equalsIgnoreCase("SD")) {
                Locator warrantyRequirements = page.locator(WARRANTY_REQUIREMENTS);
                locatorVisibleHandler(warrantyRequirements);
                warrantyRequirements.click();
            } else {
                Locator salesWarrantyRequirements = page.locator(SALES_WARRANTY_REQUIREMENTS);
                locatorVisibleHandler(salesWarrantyRequirements);
                salesWarrantyRequirements.click();
            }
            Locator warrantyRequirementsSearch = page.locator(WARRANTY_REQUIREMENTS_SEARCH);
            locatorVisibleHandler(warrantyRequirementsSearch);
            warrantyRequirementsSearch.fill(warrantyRequirement);

            String warrantyRequirementSelector = getLocator(warrantyRequirement);
            Locator warrantyRequirementSelect = page.locator(warrantyRequirementSelector);
            locatorVisibleHandler(warrantyRequirementSelect);
            warrantyRequirementSelect.click();
        } catch (Exception exception) {
            logger.error("Exception in Warranty Requirements Function: {}", exception.getMessage());
        }
    }

    public void priceValidity(String type) {
        try {
            String priceValidity = jsonNode.get("requisition").get("priceValidity").asText();

            if (type.equalsIgnoreCase("PS") || type.equalsIgnoreCase("SD")) {
                Locator priceValidityLocator = page.locator(PRICE_VALIDITY);
                locatorVisibleHandler(priceValidityLocator);
                priceValidityLocator.click();
            } else {
                Locator salesPriceValidity = page.locator(SALES_PRICE_VALIDITY);
                locatorVisibleHandler(salesPriceValidity);
                salesPriceValidity.click();
            }

            Locator priceValiditySearch = page.locator(PRICE_VALIDITY_SEARCH);
            locatorVisibleHandler(priceValiditySearch);
            priceValiditySearch.fill(priceValidity);

            String priceValiditySelector = getLocator(priceValidity);
            Locator priceValiditySelect = page.locator(priceValiditySelector);
            locatorVisibleHandler(priceValiditySelect);
            priceValiditySelect.click();
        } catch (Exception exception) {
            logger.error("Exception in Price Validity Function: {}", exception.getMessage());
        }
    }

    public void shippingAddress() {
        try {
            String shipToYokogawa = jsonNode.get("requisition").get("shipToYokogawa").asText();
            String shippingAddressValue = jsonNode.get("requisition").get("shippingAddress").asText();
            String shippingAddressEnduser = jsonNode.get("requisition").get("shippingAddressEnduser").asText();

            if (shipToYokogawa.equalsIgnoreCase("yes")) {
                Locator shippingAddressLocator = page.locator(SHIPPING_ADDRESS);
                locatorVisibleHandler(shippingAddressLocator);
                shippingAddressLocator.click();

                String shippingAddressOptionLocator = getLocator(shippingAddressValue);

                Locator shippingAddressSelect = page.locator(shippingAddressOptionLocator);
                locatorVisibleHandler(shippingAddressSelect);
                shippingAddressSelect.last().click();
            } else {
                Locator shippingAddressEnduserLocator = page.locator(SHIPPING_ADDRESS_ENDUSERS_OR_OTHERS);
                locatorVisibleHandler(shippingAddressEnduserLocator);
                shippingAddressEnduserLocator.click();

                Locator shipingAddressEndUserSearchLocator = page.locator(SHIPPING_ADDRESS_ENDUSERS_SEARCH);
                locatorVisibleHandler(shippingAddressEnduserLocator);
                shipingAddressEndUserSearchLocator.fill(shippingAddressEnduser);

                String shippingAddressEnduserOptionLocator = getLocator(shippingAddressEnduser);
                Locator shippingAddressEnduserSelect = page.locator(shippingAddressEnduserOptionLocator);
                locatorVisibleHandler(shippingAddressEnduserSelect);
                shippingAddressEnduserSelect.click();
            }
        } catch (Exception exception) {
            logger.error("Exception in Shipping Address Function: {}", exception.getMessage());
        }
    }

    public void shippingMode(String type, String purchaseType) {
        try {
            String getShippingMode = jsonNode.get("requisition").get("shippingMode").asText();

            if (type.equalsIgnoreCase("PS")) {
                Locator shippingModeLocator = page.locator(SHIPPING_MODE);
                locatorVisibleHandler(shippingModeLocator);
                shippingModeLocator.click();
            } else if (type.equalsIgnoreCase("SD")) {
                Locator shippingModeLocator = page.locator(SHIPPING_MODE_SD);
                locatorVisibleHandler(shippingModeLocator);
                shippingModeLocator.click();
            }

            Locator shippingModeSearchLocator = page.locator(SHIPPING_MODE_SEARCH);
            locatorVisibleHandler(shippingModeSearchLocator);
            shippingModeSearchLocator.fill(getShippingMode);

            String finalShippingMode = getLocator(getShippingMode);
            Locator shippingMode = page.locator(finalShippingMode);
            locatorVisibleHandler(shippingMode);
            shippingMode.click();
        } catch (Exception exception) {
            logger.error("Exception in Shipping Mode Function: {}", exception.getMessage());
        }
    }

    public void quotationRequiredBy() {
        try {
            Locator quotationRequiredBy = page.locator(QUOTATION_REQUIRED_BY);
            locatorVisibleHandler(quotationRequiredBy);
            quotationRequiredBy.click();

            Locator daysOfMonth = page.locator(DAYS_OF_MONTH);
            locatorVisibleHandler(daysOfMonth);
            daysOfMonth.last().click();

            Locator expectedPoIssueLabelLocator = page.locator(EXPECTED_PO_ISSUE_LABEL);
            locatorVisibleHandler(expectedPoIssueLabelLocator);
            expectedPoIssueLabelLocator.click();
        } catch (Exception exception) {
            logger.error("Exception in Quotation Required By Function: {}", exception.getMessage());
        }
    }

    public void expectedPOIssue(String type, String purchaseType) {
        try {
            Locator expectedPoIssueField;
            Locator todayOption;
            if (purchaseType.equalsIgnoreCase("catalog") || purchaseType.equalsIgnoreCase("Punchout") || purchaseType.equalsIgnoreCase("catalog bop2/bop5")) {
                expectedPoIssueField = page.locator(EXPECTED_PO_ISSUE_CATALOG);
            } else {
                expectedPoIssueField = page.locator(EXPECTED_PO_ISSUE_NON_CATALOG);
            }
            locatorVisibleHandler(expectedPoIssueField);
            expectedPoIssueField.click();
            todayOption = page.locator(DAYS_OF_NEXT_MONTH).first();
            locatorVisibleHandler(todayOption);
            todayOption.click();
        } catch (Exception exception) {
            logger.error("Exception in Expected PO Function: {}", exception.getMessage());
        }
    }

    public void expectedDelivery(String type, String purchaseType) {
        try {
            Locator expectedDeliveryField;
            Locator todayOption;
            if (purchaseType.equalsIgnoreCase("catalog") || purchaseType.equalsIgnoreCase("Punchout") || purchaseType.equalsIgnoreCase("catalog bop2/bop5")) {
                expectedDeliveryField = page.locator(EXPECTED_DELIVERY_CATALOG);
            } else {
                expectedDeliveryField = page.locator(EXPECTED_DELIVERY_NON_CATALOG);
            }
            locatorVisibleHandler(expectedDeliveryField);
            expectedDeliveryField.click();
            todayOption = page.locator(DAYS_OF_MONTH).last();
            locatorVisibleHandler(todayOption);
            todayOption.click();
        } catch (Exception exception) {
            logger.error("Exception in Expected Delivery Function: {}", exception.getMessage());
        }
    }

    public void rohsCompliance() {
        try {
            String compliance = jsonNode.get("requisition").get("rohsCompliance").asText();

            if (compliance.equalsIgnoreCase("no")) {
                Locator rohsComplianceLocator = page.locator(ROHS_COMPLIANCE);
                locatorVisibleHandler(rohsComplianceLocator);
                rohsComplianceLocator.click();
            }
        } catch (Exception exception) {
            logger.error("Exception in RoHS Compliance Function: {}", exception.getMessage());
        }
    }

    public void inspectionRequired(String type, String purchaseType) {
        try {
            Locator inspectionRequiredLocator;
            String isInspectionRequired = jsonNode.get("requisition").get("inspectionRequired").asText();

            if (isInspectionRequired.equalsIgnoreCase("yes")) {
                if (purchaseType.equalsIgnoreCase("catalog") || purchaseType.equalsIgnoreCase("Punchout") || purchaseType.equalsIgnoreCase("catalog bop2/bop5")) {
                    inspectionRequiredLocator = page.locator(CATALOG_INSPECTION_REQUIRED);
                } else if (type.equals("PS") || type.equals("SD")) {
                    inspectionRequiredLocator = page.locator(NON_CATALOG_INSPECTION_REQUIRED);
                } else {
                    inspectionRequiredLocator = page.locator(SALES_NON_CATALOG_INSPECTION_REQUIRED);
                }
                locatorVisibleHandler(inspectionRequiredLocator);
                inspectionRequiredLocator.click();
            }
        } catch (Exception exception) {
            logger.error("Exception in Inspection Required Function: {}", exception.getMessage());
        }
    }

    public void oiAndTpCurrency() {
        try {
            String currency = jsonNode.get("requisition").get("oiAndTpCurrency").asText();

            Locator oiAndTpCurrencyLocator = page.locator(OI_AND_TP_CURRENCY);
            locatorVisibleHandler(oiAndTpCurrencyLocator);
            oiAndTpCurrencyLocator.click();

            Locator oiAndTpCurrencySearchLocator = page.locator(OI_AND_TP_CURRENCY_SEARCH);
            locatorVisibleHandler(oiAndTpCurrencySearchLocator);
            oiAndTpCurrencySearchLocator.fill(currency);

            String currencySelect = getLocator(currency);
            Locator curremcySelectLocator = page.locator(currencySelect);
            locatorVisibleHandler(curremcySelectLocator);
            curremcySelectLocator.click();

            Locator oiAndTpCurrencyLabelLocator = page.locator(OI_AND_TP_CURRENCY_LABEL);
            locatorVisibleHandler(oiAndTpCurrencyLabelLocator);
            oiAndTpCurrencyLabelLocator.click();
        } catch (Exception exception) {
            logger.error("Exception in OI/TP Currency Function: {}", exception.getMessage());
        }
    }

    public void orderIntake(String type) {
        try {
            String orderIntake = jsonNode.get("requisition").get("orderIntake").asText();
            if (type.equalsIgnoreCase("PS") || type.equalsIgnoreCase("SD")) {
                Locator orderIntakeLocator = page.locator(ORDER_INTAKE);
                locatorVisibleHandler(orderIntakeLocator);
                orderIntakeLocator.fill(orderIntake);
            } else {
                Locator salesOrderIntakeLocator = page.locator(SALES_ORDER_INTAKE);
                locatorVisibleHandler(salesOrderIntakeLocator);
                salesOrderIntakeLocator.fill(orderIntake);
            }
        } catch (Exception exception) {
            logger.error("Exception in Order Intake Function: {}", exception.getMessage());
        }
    }

    public void targetPrice(String type, String purchaseType) {
        try {
            if (purchaseType.equalsIgnoreCase("NonCatalog")) {
                String targetPrice = jsonNode.get("requisition").get("targetPrice").asText();
                if (type.equalsIgnoreCase("PS") || type.equalsIgnoreCase("SD")) {
                    Locator targetPriceLocator = page.locator(TARGET_PRICE);
                    locatorVisibleHandler(targetPriceLocator);
                    targetPriceLocator.fill(targetPrice);
                } else {
                    Locator salesTargetPriceLocator = page.locator(SALES_TARGET_PRICE);
                    locatorVisibleHandler(salesTargetPriceLocator);
                    salesTargetPriceLocator.fill(targetPrice);
                }
            }
        } catch (Exception exception) {
            logger.error("Exception in Target Price Function: {}", exception.getMessage());
        }
    }

    public void addLineRequisitionItemsNonCatalog() {
        try {
            boolean itemImport = jsonNode.get("requisition").get("itemImport").asBoolean();
            if (itemImport) {
                Locator importPopUpButton = page.locator(ITEM_IMPORT_POPUP);
                locatorVisibleHandler(importPopUpButton);
                importPopUpButton.click();

                Locator itemFile = page.locator(CHOOSE_FILE);
                String filePath = jsonNode.get("configSettings").get("nonCatalogItemImportFilePath").asText();
                locatorVisibleHandler(itemFile);
                itemFile.setInputFiles(Paths.get(filePath));

                Locator uploadButton = page.locator(UPLOAD_BUTTON);
                locatorVisibleHandler(uploadButton);
                uploadButton.click();
            } else {
                String idValue;
                List<String> inputTypes = new ArrayList<>();
                String[] itemNames = jsonNode.get("requisition").get("items").asText().split(",");
                String[] quantities = jsonNode.get("requisition").get("quantityList").asText().split(",");

                Locator addLineItemButton = page.locator(ADD_LINE_ITEM_BUTTON);
                locatorVisibleHandler(addLineItemButton);
                addLineItemButton.click();

                for (int i = 0; i < itemNames.length; i++) {
                    Locator nonCatalogItemsDropdownLocator = page.locator(NON_CATALOG_ITEMS_DROPDOWN);
                    locatorVisibleHandler(nonCatalogItemsDropdownLocator);
                    nonCatalogItemsDropdownLocator.click();

                    Locator itemSearchLocator = page.locator(ITEM_SEARCH);
                    locatorVisibleHandler(itemSearchLocator);
                    itemSearchLocator.fill(itemNames[i]);

                    String itemName = itemNames[i];
                    String encodedName = itemName.replace(" ", "%20");

                    APIResponse itemSpecificationResponse = page.request().fetch(appUrl + "/api/Itemcategory/search?keyword=" + encodedName + "&purchaseMethod=NonCatalog");
                    JsonNode itemSpecificationsObject = objectMapper.readTree(itemSpecificationResponse.body());
                    idValue = itemSpecificationsObject.get(0).get("id").asText();

                    Locator itemNameLocator = page.locator(getLocator(itemNames[i]));
                    locatorVisibleHandler(itemSearchLocator);
                    itemNameLocator.first().click();

                    APIResponse getItemSpecifications = page.request().fetch(appUrl + "/api/Items/Spefications?itemId=" + idValue);
                    JsonNode getItemSpecificationsJson = objectMapper.readTree(getItemSpecifications.body());

                    if (!getItemSpecificationsJson.isNull()) {
                        for (int j = 0; j < getItemSpecificationsJson.size(); j++) {
                            JsonNode item = getItemSpecificationsJson.get(j);
                            if (item.has("inputType")) {
                                inputTypes.add(item.get("inputType").asText());
                            }
                        }

                        for (String inputType : inputTypes) {
                            switch (inputType) {
                                case "Text" -> {
                                    List<Locator> textFields = page.locator(ITEM_SPECIFICATIONS_TEXT_FIELD_LOCATORS).all();
                                    for (Locator textField : textFields) {
                                        String idLocator = textField.getAttribute("id");
                                        Locator textFieldLocator = page.locator("#" + idLocator);
                                        locatorVisibleHandler(textFieldLocator);
                                        if (textFieldLocator.isEnabled()) {
                                            textFieldLocator.fill("2000");
                                        }
                                    }
                                }
                                case "Selection" -> {
                                    List<Locator> selectionFields = page.locator(ITEM_SPECIFICATIONS_SELECTION_FIELD_LOCATORS).all();
                                    for (Locator selectionField : selectionFields) {
                                        String idLocator = selectionField.getAttribute("id");
                                        Locator selectionFieldLocator = page.locator("#" + idLocator);
                                        locatorVisibleHandler(selectionFieldLocator);
                                        if (selectionFieldLocator.isEnabled()) {
                                            selectionFieldLocator.click();
                                            Locator itemSpecificationLocator = page.locator(ITEM_SPECIFICATIONS_SELECTION_FIELD_RESULT_LOCATOR);
                                            locatorVisibleHandler(itemSpecificationLocator);
                                            itemSpecificationLocator.click();
                                        }
                                    }
                                }
                                case "CheckBox" -> {
                                    List<Locator> checkBoxFields = page.locator(ITEM_SPECIFICATIONS_CHECKBOX_FIELD_LOCATORS).all();
                                    for (Locator checkBoxField : checkBoxFields) {
                                        String idLocator = checkBoxField.getAttribute("id");
                                        Locator checkBoxFieldLocator = page.locator("#" + idLocator);
                                        locatorVisibleHandler(checkBoxFieldLocator);
                                        if (checkBoxFieldLocator.isEnabled()) {
                                            checkBoxFieldLocator.click();
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Locator quantity = page.locator(QUANTITY);
                    locatorVisibleHandler(quantity);
                    quantity.fill(quantities[i]);

                    String shippingPoint = jsonNode.get("requisition").get("shippingPoint").asText();
                    Locator shippingPointLocator = page.locator(SHIPPING_POINT_LOCATOR);
                    locatorVisibleHandler(shippingPointLocator);
                    shippingPointLocator.click();

                    Locator shippingPointSearchField = page.locator(SHIPPING_POINT_SEARCH_FIELD);
                    locatorVisibleHandler(shippingPointLocator);
                    shippingPointSearchField.fill(shippingPoint);

                    String shippingPointOptionLocator = getLocator(shippingPoint);
                    Locator shippingPointOptionSelect = page.locator(shippingPointOptionLocator);
                    locatorVisibleHandler(shippingPointOptionSelect);
                    shippingPointOptionSelect.last().click();

                    Locator addItemButtonLocator = page.locator(ADD_ITEM_BUTTON);
                    locatorVisibleHandler(addItemButtonLocator);
                    addItemButtonLocator.click();

                    if (i < itemNames.length - 1) {
                        addLineItemButton.click();
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception exception) {
            logger.error("Exception in Non-Catalog Requisition Items IMPORT Function: {}", exception.getMessage());
        }
    }

    public void editItemsForPunchout() {
        try {
            String itemName = jsonNode.get("requisition").get("punchoutItem").asText();
            String itemQuantity = jsonNode.get("requisition").get("punchoutItemQuantity").asText();
            Locator editItemButton = page.locator(ITEM_EDIT_BUTTON);
            locatorVisibleHandler(editItemButton);
            editItemButton.click();

            Locator itemsDropdownLocator = page.locator(CATALOG_ITEMS_DROPDOWN);
            locatorVisibleHandler(itemsDropdownLocator);
            itemsDropdownLocator.click();

            Locator itemSearchLocator = page.locator(ITEM_SEARCH);
            locatorVisibleHandler(itemSearchLocator);
            itemSearchLocator.fill(itemName);

            Locator itemNameLocator = page.locator(getLocator(itemName));
            locatorVisibleHandler(itemSearchLocator);
            itemNameLocator.first().click();

            Locator quantity = page.locator(QUANTITY);
            locatorVisibleHandler(quantity);
            quantity.fill(itemQuantity);

            Locator recipientNameLocator = page.locator(RECIPIENT_NAME);
            locatorVisibleHandler(recipientNameLocator);
            recipientNameLocator.fill("Recipient Name");

            Locator addItemButtonLocator = page.locator(ADD_ITEM_BUTTON);
            locatorVisibleHandler(addItemButtonLocator);
            addItemButtonLocator.click();
        } catch (Exception exception) {
            logger.error("Exception in Edit Items For Punchout Function: {}", exception.getMessage());
        }
    }

    public void addItemsForBOP2BOP5(String type) {
        List<String> bop2Bop5Items = new ArrayList<>();
        Locator addItemButton;
        try {
            if (type.equalsIgnoreCase("PS")) {
                Locator addBOP2BOP5ItemButton = page.locator(ADD_BOP2BOP5_ITEM_BUTTON);
                locatorVisibleHandler(addBOP2BOP5ItemButton);
                addBOP2BOP5ItemButton.click();

                Locator addBOP2BOP5ItemDropDownLocator = page.locator(BOP2BOP5_CATALOG_ITEMS_DROPDOWN);
                locatorVisibleHandler(addBOP2BOP5ItemDropDownLocator);
                addBOP2BOP5ItemDropDownLocator.click();

                APIResponse bop2Bop5ItemApiResponse = page.request().fetch(appUrl + "/api/BoprcItems/search?vendorId=" + vendorId, RequestOptions.create());
                JsonNode jsonNode = objectMapper.readTree(bop2Bop5ItemApiResponse.body());

                for (JsonNode bopItem : jsonNode) {
                    if (bopItem.has("bopCode")) {
                        String bopCode = bopItem.get("bopCode").asText();
                        bop2Bop5Items.add(bopCode);
                    }
                }

                for (int i = 0; i < bop2Bop5Items.size(); i++) {
                    Locator itemSearch = page.locator(ITEM_SEARCH);
                    locatorVisibleHandler(itemSearch);
                    itemSearch.fill(bop2Bop5Items.get(i));

                    String itemDropDownOptionSelect = getLocator(bop2Bop5Items.get(i));
                    Locator itemSelect = page.locator(itemDropDownOptionSelect);
                    locatorVisibleHandler(itemSelect);
                    itemSelect.click();

                    Locator quantity = page.locator(BOP2BOP5_QUANTITY);
                    locatorVisibleHandler(quantity);
                    quantity.fill(String.valueOf(i + 10000));

                    Locator receipientNameLocator = page.locator(BOP2BOP5_RECIPIENT_NAME);
                    locatorVisibleHandler(receipientNameLocator);
                    receipientNameLocator.fill("Recipient Name" + i);

                    addItemButton = page.locator(BOP2BOP5_ADD_ITEM_BUTTON);
                    locatorVisibleHandler(addItemButton);
                    addItemButton.click();

                    if (i < bop2Bop5Items.size() - 1) {
                        addBOP2BOP5ItemButton.click();
                        addBOP2BOP5ItemDropDownLocator.click();
                    } else {
                        break;
                    }
                }
            } else if (type.equalsIgnoreCase("SD")) {
                boolean bop2ItemSelectFlag = jsonNode.get("requisition").get("bop2ItemSelectFLag").asBoolean();
                boolean bop5ItemSelectFlag = jsonNode.get("requisition").get("bop5ItemSelectFLag").asBoolean();

                Locator addBOP2BOP5SoItemButton = page.locator(ADD_BOP2BOP5_SO_ITEM_BUTTON);
                locatorVisibleHandler(addBOP2BOP5SoItemButton);
                addBOP2BOP5SoItemButton.click();

                int soLineItemRows = page.locator(SO_LINE_ITEM_ROWS).count();
                List<String> soLineItemColumnTextContents = page.locator(SO_LINE_ITEM_COLUMN).allTextContents();

                for (int i = 1; i <= soLineItemRows; i++) {
                    if (bop2ItemSelectFlag) {
                        if (soLineItemColumnTextContents.get(i).startsWith("BOP2")) {
                            page.locator(getSoLineItemRowCheckBox(i)).click();
                            break;
                        }
                    } else if (bop5ItemSelectFlag) {
                        if (soLineItemColumnTextContents.get(i).startsWith("BOP5")) {
                            page.locator(getSoLineItemRowCheckBox(i)).click();
                            break;
                        }
                    }
                }
                Locator soLineItemAddButton = page.locator(SO_LINE_ITEM_ADD_BUTTON);
                locatorVisibleHandler(soLineItemAddButton);
                soLineItemAddButton.click();
            }
        } catch (Exception exception) {
            logger.error("Exception in Add Items For BOP2/BOP5 Function: {}", exception.getMessage());
        }
    }

    public Map<String, String> vendor(String type, String purchaseType) {
        Map<String, String> rateContractArray = new HashMap<>();
        try {
            String vendorNameValue = jsonNode.get("requisition").get("vendorName").asText();
            Locator vendorLocator = page.locator(VENDOR);
            locatorVisibleHandler(vendorLocator);
            vendorLocator.click();

            Locator vendorSearchLocator = page.locator(VENDOR_SEARCH);
            locatorVisibleHandler(vendorSearchLocator);
            vendorSearchLocator.fill(vendorNameValue);

            if (type.equalsIgnoreCase("PS")) {
                String encodedVendorName = URLEncoder.encode(vendorNameValue, StandardCharsets.UTF_8).replace("+", "%20");
                APIResponse vendorApiResponse = page.request().fetch(appUrl + "/api/Vendors/GetAllVendorsByKeyword/" + projectId + "?keyword=" + encodedVendorName, RequestOptions.create());
                JsonNode vendorJson = objectMapper.readTree(vendorApiResponse.body());
                JsonNode vendorIdJson = vendorJson.get(0);
                vendorId = vendorIdJson.get("id").asInt();
            } else if (type.equalsIgnoreCase("Sales") || type.equalsIgnoreCase("SD")) {
                String apiType = type.equalsIgnoreCase("SD") ? companyIdForSD : companyIdForSales;
                APIResponse vendorApiResponse = page.request().fetch(appUrl + "/api/Vendors/VendorsBasedOnCompanyLinked/" + apiType + "?keyword=" + vendorNameValue, RequestOptions.create());
                JsonNode vendorJson = objectMapper.readTree(vendorApiResponse.body());
                JsonNode vendorIdJson = vendorJson.get(0);
                vendorId = vendorIdJson.get("id").asInt();
            }

            String vendorOptionLocator = getLocator(vendorNameValue);
            Locator vendorOptionSelectLocator = page.locator(vendorOptionLocator);
            locatorVisibleHandler(vendorOptionSelectLocator);
            vendorOptionSelectLocator.click();

            if (purchaseType.equalsIgnoreCase("catalog")) {
                APIResponse rateContractApiResponse = page.request().fetch(appUrl + "/api/RateContractsByVendorId?vendorId=" + vendorId, RequestOptions.create());
                JsonNode rateContractJson = objectMapper.readTree(rateContractApiResponse.body());

                for (JsonNode rateContract : rateContractJson) {
                    if (rateContract.has("id") && rateContract.has("text")) {
                        String rateContractId = rateContract.get("id").asText();
                        String rateContractText = rateContract.get("text").asText();
                        rateContractArray.put(rateContractId, rateContractText);
                    }
                }
            }
        } catch (Exception exception) {
            logger.error("Exception in Vendor Function: {}", exception.getMessage());
        }
        return rateContractArray;
    }

    public List<String> rateContract(Map<String, String> rateContractArray) {
        List<String> rateContractItems = new ArrayList<>();
        try {
            String rateContractValue = jsonNode.get("requisition").get("rateContract").asText();

            Locator rateContractLocator = page.locator(RATE_CONTRACT);
            locatorVisibleHandler(rateContractLocator);
            rateContractLocator.click();

            for (Map.Entry<String, String> rateContract : rateContractArray.entrySet()) {
                if (rateContract.getValue().equals(rateContractValue)) {
                    String rateContractId = rateContract.getKey();
                    Locator rateContractSearchLocator = page.locator(RATE_CONTRACT_SEARCH);
                    locatorVisibleHandler(rateContractLocator);
                    rateContractSearchLocator.fill(rateContractValue);

                    String rateContractOptionLocator = getLocator(rateContractValue);
                    Locator rateContractOptionSelectLocator = page.locator(rateContractOptionLocator);
                    locatorVisibleHandler(rateContractOptionSelectLocator);
                    rateContractOptionSelectLocator.click();

                    APIResponse rateContractResponse = page.request().fetch(appUrl + "/api/RateContracts/ratecontract?rateContractId=" + rateContractId, RequestOptions.create());
                    JsonNode rateContractJsonResponse = objectMapper.readTree(rateContractResponse.body());
                    JsonNode itemsArray = rateContractJsonResponse.get("items");
                    for (JsonNode item : itemsArray) {
                        if (item.has("name")) {
                            rateContractItems.add(item.get("name").asText());
                        }
                    }
                    break;
                }
            }
        } catch (Exception exception) {
            logger.error("Exception in Rate Contract Function: {}", exception.getMessage());
        }
        return rateContractItems;
    }

    public void buyerManager() {
        try {
            String buyerManagerName = jsonNode.get("mailIds").get("buyerManagerEmail").asText();
            Locator buyerManager = page.locator(BUYER_MANAGER);
            locatorVisibleHandler(buyerManager);
            buyerManager.click();

            Locator buyerManagerSearch = page.locator(BUYER_MANAGER_SEARCH);
            locatorVisibleHandler(buyerManagerSearch);
            buyerManagerSearch.fill(buyerManagerName);

            String buyerManagerLocator = getLocator(buyerManagerName);
            Locator buyerManagerSelectLocator = page.locator(buyerManagerLocator);
            locatorVisibleHandler(buyerManagerSelectLocator);
            buyerManagerSelectLocator.click();
        } catch (Exception exception) {
            logger.error("Exception in Buyer Manager Function: {}", exception.getMessage());
        }
    }

    public void projectManager() {
        try {
            String projectManagerName = jsonNode.get("mailIds").get("projectManagerEmail").asText();
            Locator projectManager = page.locator(PROJECT_MANAGER);
            locatorVisibleHandler(projectManager);
            projectManager.click();

            Locator projectManagerSearch = page.locator(PROJECT_MANAGER_SEARCH);
            locatorVisibleHandler(projectManagerSearch);
            projectManagerSearch.fill(projectManagerName);

            String projectManagerLocator = getLocator(projectManagerName);
            Locator projectManagerSelectLocator = page.locator(projectManagerLocator);
            locatorVisibleHandler(projectManagerSelectLocator);
            projectManagerSelectLocator.click();
        } catch (Exception exception) {
            logger.error("Exception in Project Manager Function: {}", exception.getMessage());
        }
    }

    public void checker(String type, String purchaseType) {
        try {
            String checkerName = jsonNode.get("mailIds").get("checkerEmail").asText();

            Locator checker;
            if (type.equalsIgnoreCase("PS")) {
                checker = page.locator(CHECKER);
            } else if (type.equalsIgnoreCase("SD") && purchaseType.equalsIgnoreCase("catalog bop2/bop5")) {
                checker = page.locator(BOP2BOP5_CHECKER);
            } else if (type.equalsIgnoreCase("SD")) {
                checker = page.locator(SD_CHECKER);
            } else {
                checker = page.locator(BOP2BOP5_CHECKER);
            }
            locatorVisibleHandler(checker);
            checker.click();

            Locator checkerSearch = page.locator(CHECKER_SEARCH);
            locatorVisibleHandler(checkerSearch);
            checkerSearch.fill(checkerName);

            String checkerLocator = getLocator(checkerName);
            Locator checkerSelectLocator = page.locator(checkerLocator);
            locatorVisibleHandler(checkerSelectLocator);
            checkerSelectLocator.click();
        } catch (Exception exception) {
            logger.error("Exception in Checker Function: {}", exception.getMessage());
        }
    }

    public void tcasCompliance() {
        String tcasCompliance = jsonNode.get("requisition").get("tcasComplianceApplicable").asText();
        String[] tcasQuestionNumbers = jsonNode.get("requisition").get("tcasQuestionNumber").asText().split(",");
        try {
            if (tcasCompliance.equalsIgnoreCase("yes")) {
                Locator tcasComplianceApplicable = page.locator(TCAS_COMPLIANCE_APPLICABLE);
                locatorVisibleHandler(tcasComplianceApplicable);
                tcasComplianceApplicable.click();

                for (String tcasQuestionNumber : tcasQuestionNumbers) {
                    Locator tcasQuestionNumberLocator = page.locator(TCAS_QUESTION_NUMBER + tcasQuestionNumber);
                    locatorVisibleHandler(tcasQuestionNumberLocator);
                    tcasQuestionNumberLocator.click();
                }

                Locator tcasAddButtonLocator = page.locator(TCAS_ADD_BUTTON);
                locatorVisibleHandler(tcasAddButtonLocator);
                tcasAddButtonLocator.click();

                String tcasFilePath = jsonNode.get("configSettings").get("tcasFilePath").asText().trim();
                Locator tcasFileUploadButtonLocator = page.locator(TCAS_FILE_UPLOAD_BUTTON);
                locatorVisibleHandler(tcasFileUploadButtonLocator);
                tcasFileUploadButtonLocator.setInputFiles(Paths.get(tcasFilePath));
            }
        } catch (Exception exception) {
            logger.error("Exception in TCAS Function: {}", exception.getMessage());
        }
    }

    public void addLineRequisitionItemsCatalog(List<String> rateContractItems) {
        try {
            boolean itemImport = jsonNode.get("requisition").get("itemImport").asBoolean();
            if (itemImport) {
                Locator importPopUpButton = page.locator(ITEM_IMPORT_POPUP);
                locatorVisibleHandler(importPopUpButton);
                importPopUpButton.click();

                Locator itemFile = page.locator(CHOOSE_FILE);
                String filePath = jsonNode.get("configSettings").get("catalogItemImportFilePath").asText();
                locatorVisibleHandler(itemFile);
                itemFile.setInputFiles(Paths.get(filePath));

                Locator uploadButton = page.locator(UPLOAD_BUTTON);
                locatorVisibleHandler(uploadButton);
                uploadButton.click();
            } else {
                Locator addLineItemButton;
                Locator addItemButton;

                addLineItemButton = page.locator(ADD_LINE_ITEM_BUTTON);
                locatorVisibleHandler(addLineItemButton);
                addLineItemButton.click();

                for (int i = 0; i < rateContractItems.size(); i++) {
                    Locator itemDropDown = page.locator(CATALOG_ITEMS_DROPDOWN);
                    locatorVisibleHandler(itemDropDown);
                    itemDropDown.click();

                    Locator itemSearch = page.locator(ITEM_SEARCH);
                    locatorVisibleHandler(itemSearch);
                    itemSearch.fill(rateContractItems.get(i));

                    String itemDropDownOptionSelect = getLocator(rateContractItems.get(i));
                    Locator itemSelect = page.locator(itemDropDownOptionSelect);
                    locatorVisibleHandler(itemSelect);
                    itemSelect.click();

                    Locator quantity = page.locator(QUANTITY);
                    locatorVisibleHandler(quantity);
                    quantity.fill(String.valueOf(i + 10000));

                    String shippingPoint = jsonNode.get("requisition").get("shippingPoint").asText();
                    Locator shippingPointLocator = page.locator(SHIPPING_POINT_LOCATOR);
                    locatorVisibleHandler(shippingPointLocator);
                    shippingPointLocator.click();

                    Locator shippingPointSearch = page.locator(SHIPPING_POINT_SEARCH_FIELD);
                    locatorVisibleHandler(shippingPointSearch);
                    shippingPointSearch.fill(shippingPoint);

                    String shippingPointOptionLocator = getLocator(shippingPoint);
                    Locator shippingPointOptionSelect = page.locator(shippingPointOptionLocator);
                    locatorVisibleHandler(shippingPointLocator);
                    shippingPointOptionSelect.last().click();

                    addItemButton = page.locator(ADD_ITEM_BUTTON);
                    locatorVisibleHandler(addItemButton);
                    addItemButton.click();

                    if (i < rateContractItems.size() - 1) {
                        addLineItemButton.click();
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception exception) {
            logger.error("Exception in Catalog Requisition Items Function: {}", exception.getMessage());
        }
    }

    public void notes() {
        try {
            String notesText = jsonNode.get("requisition").get("requisitionNotes").asText();
            Locator notes = page.locator(NOTES);
            notes.fill(notesText);
        } catch (Exception exception) {
            logger.error("Exception in Requisition Notes Function: {}", exception.getMessage());
        }
    }

    public void attachments() {
        try {
            String[] requisitionAttachmentsTypes = jsonNode.get("requisition").get("requisitionAttachmentsTypes").asText().split(",");
            String internalFilePath = jsonNode.get("configSettings").get("internalAttachmentFilePath").asText();
            String externalFilePath = jsonNode.get("configSettings").get("externalAttachmentFilePath").asText();

            Locator attachmentsButton = page.locator(ATTACHMENTS);
            locatorVisibleHandler(attachmentsButton);
            attachmentsButton.click();

            for (String requisitionAttachmentsType : requisitionAttachmentsTypes) {
                if (requisitionAttachmentsType.equalsIgnoreCase("internal")) {
                    Locator fileUpload = page.locator(FILE_UPLOAD);
                    locatorVisibleHandler(fileUpload);
                    fileUpload.setInputFiles(Paths.get(internalFilePath));

                    Locator attachFileButton = page.locator(ATTACH_FILE_BUTTON);
                    locatorVisibleHandler(attachFileButton);
                    attachFileButton.click();
                } else if (requisitionAttachmentsType.equalsIgnoreCase("external")) {
                    Locator fileUpload = page.locator(FILE_UPLOAD);
                    locatorVisibleHandler(fileUpload);
                    fileUpload.setInputFiles(Paths.get(externalFilePath));

                    Locator externalRadioButton = page.locator(EXTERNAL_RADIO_BUTTON);
                    locatorVisibleHandler(externalRadioButton);
                    externalRadioButton.click();

                    Locator attachFileButton = page.locator(ATTACH_FILE_BUTTON);
                    locatorVisibleHandler(attachFileButton);
                    attachFileButton.click();
                }
            }
            Locator continueButton = page.locator(CONTINUE_BUTTON);
            locatorVisibleHandler(continueButton);
            continueButton.click();
        } catch (Exception exception) {
            logger.error("Exception in Requisition Attachments Function: {}", exception.getMessage());
        }
    }

    public int prCreate(String type, String purchaseType) {
        int status = 0;
        try {
            Locator createDraftButton = page.locator(CREATE_DRAFT_BUTTON);
            locatorVisibleHandler(createDraftButton);
            createDraftButton.click();

            Locator yesButton = page.locator(YES);
            locatorVisibleHandler(yesButton);

            String reqType;
            if (type.equalsIgnoreCase("sales")) {
                reqType = "/api/RequisitionsSales/";
            } else if (type.equalsIgnoreCase("ps")) {
                reqType = "/api/Requisitions/";
            } else if (type.equalsIgnoreCase("SD")) {
                reqType = "/api/RequisitionsNonPoc/";
            } else {
                reqType = "/api/RequisitionsOthers/";
            }

            Response statusResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + reqType) && response.status() == 200,
                    yesButton::click
            );

            JsonNode response = objectMapper.readTree(statusResponse.body());

            String requisitionStatus = "";
            if (response.has("status")) {
                requisitionStatus = response.get("status").asText();
            }

            playwrightFactory.savePropertiesIntoJsonFile("requisition", "requisitionStatus", requisitionStatus);

            status = statusResponse.status();

            String url = page.url();
            String[] urlArray = url.split("=");
            String getUid = urlArray[1];
            playwrightFactory.savePropertiesIntoJsonFile("requisition", "requisitionUid", getUid);
            playwrightFactory.savePropertiesIntoJsonFile("requisition", "purchaseType", type);

//TODO Save Transaction number
            if (type.equalsIgnoreCase("sales")) {
                APIResponse apiResponse = page.request().fetch(appUrl + "/api/RequisitionsSales/" + getUid, RequestOptions.create());
                JsonNode jsonNode1 = objectMapper.readTree(apiResponse.body());
                String requisitionId = jsonNode1.get("requisitionId").asText();
                String transactionId = jsonNode1.get("transactionId").asText();
                playwrightFactory.savePropertiesIntoJsonFile("requisition", "requisitionId", requisitionId);
                playwrightFactory.savePropertiesIntoJsonFile("requisition", "salesTransactionNumber", transactionId);
            } else if (type.equalsIgnoreCase("PS")) {
                APIResponse apiResponse = page.request().fetch(appUrl + "/api/Requisitions/" + getUid, RequestOptions.create());
                JsonNode jsonNode1 = objectMapper.readTree(apiResponse.body());
                String requisitionId = jsonNode1.get("requisitionId").asText();
                String transactionId = jsonNode1.get("transactionId").asText();
                playwrightFactory.savePropertiesIntoJsonFile("requisition", "requisitionId", requisitionId);
                playwrightFactory.savePropertiesIntoJsonFile("requisition", "psTransactionNumber", transactionId);
            } else {
                APIResponse apiResponse = page.request().fetch(appUrl + "/api/RequisitionsNonPoc/" + getUid, RequestOptions.create());
                JsonNode jsonNode1 = objectMapper.readTree(apiResponse.body());
                String requisitionId = jsonNode1.get("requisitionId").asText();
                String transactionId = jsonNode1.get("transactionId").asText();
                playwrightFactory.savePropertiesIntoJsonFile("requisition", "requisitionId", requisitionId);
                playwrightFactory.savePropertiesIntoJsonFile("requisition", "sdTransactionNumber", transactionId);
            }

            PlaywrightFactory.attachScreenshotWithName("Requisition Create", page);

            assertDetailsPage();

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Requisition Create Function: {}", exception.getMessage());
        }
        return status;
    }

    public void adminLogin() {
        try {
            String emailId = jsonNode.get("mailIds").get("adminEmail").asText();
            iLogin.performLogin(emailId);
        } catch (Exception exception) {
            logger.error("Exception in Requester Login Function: {}", exception.getMessage());
        }
    }

    public void adminLogout() {
        try {
            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Requester Login Function: {}", exception.getMessage());
        }
    }

    public void assertDetailsPage() {
        Locator elements =
                page.locator("//div[@id='requisition-Info-Container']/div[@class='row']/div[not(contains(@style,'display:none'))]");
        int count = elements.count();

        for (int i = 0; i < count; i++) {
            Locator element = elements.nth(i); // Get the i-th element
            int x = i+1;
            if (element.locator("//div").count() > 1) {
                Locator divs = element.locator("//div");
                int divCount = divs.count();
                for (int j = 0; j < divCount; j++) {
                    Locator divElement = divs.nth(j);
                    Locator label = divElement.locator("//span[1]").first();
                    String labelText = label.innerText().trim(); // Get its label text
                    int y = x+j;
                    System.out.print(y +". "+ labelText);

                    if (divElement.locator("//a").isVisible()) {
                        Locator link = divElement.locator("//a").first();
                        String linkText = link.innerText().trim(); // Get its link text
                        System.out.println(" " + linkText);
                        continue;
                    }
                    Locator value = divElement.locator("//span[2]");
                    String valueText = value.innerText().trim(); // Get its value text
                    System.out.println(" " + valueText);
                }
            } else {
                Locator label = element.locator("//span[1]").first();
                String labelText = label.innerText().trim(); // Get its label text
                System.out.print(x +". "+ labelText);

                if (labelText.contains("Status :")) {
                    Locator value = element.locator("//span[2]//span");
                    String valueText = value.innerText().trim(); // Get its value text
                    System.out.println(" "+ valueText);
                    continue;
                }
                Locator value = element.locator("//span[2]");
                String valueText = value.innerText().trim(); // Get its value text
                System.out.println(" " + valueText);
            }
        }

    }

}