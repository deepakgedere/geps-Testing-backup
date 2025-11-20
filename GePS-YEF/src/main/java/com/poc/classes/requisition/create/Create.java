package com.poc.classes.requisition.create;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import com.poc.interfaces.login.ILogin;
import com.poc.interfaces.logout.ILogout;
import com.poc.interfaces.requisitions.IPrCreate;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.factory.PlaywrightFactory.waitForLocator;
import static com.constants.requisitions.LPrCreate.*;

public class Create implements IPrCreate {

    private Page page;
    private ILogin iLogin;
    private ILogout iLogout;
    private Properties properties;
    private String prType;

    private Create(){
    }

//TODO Constructor
    public Create(ILogin iLogin, Properties properties, Page page, ILogout iLogout){
        this.page = page;
        this.properties = properties;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
    }

    public void requesterLoginPRCreate() {
        try {
            String requesterEmail = properties.getProperty("requesterEmail");
            iLogin.performLogin(requesterEmail);
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void createButton() {
        try {
            Locator createButton = page.locator(CREATE_BUTTON);
            waitForLocator(createButton);
            createButton.click();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void purchaseType() {
        try {
            String purchaseType = properties.getProperty("purchaseType");
            String prTypeLocator = getPrType(purchaseType);
            page.locator(prTypeLocator).click();
        } catch (Exception error) {
            System.out.println("What is the Error: " + error);
        }
    }

    public void title() {
        try {
            switch (prType) {
                case "Catalog":
                    Locator catalogTitleLocator = page.locator(TITLE);
                    waitForLocator(catalogTitleLocator);
                    String catalogTitle = properties.getProperty("orderTitle");
                    catalogTitleLocator.fill(catalogTitle + "-" + prType);
                    break;
                case "NonCatalog":
                    Locator nonCatalogTitleLocator = page.locator(TITLE);
                    waitForLocator(nonCatalogTitleLocator);
                    String nonCatalogTitle = properties.getProperty("orderTitle");
                    nonCatalogTitleLocator.fill(nonCatalogTitle + "-" + prType);
                    break;
                case "MH":
                    Locator mhTitleLocator = page.locator(TITLE);
                    waitForLocator(mhTitleLocator);
                    String mhTitle = properties.getProperty("orderTitle");
                    mhTitleLocator.fill(mhTitle + "-" + prType);
                    break;
                default:
                    System.out.println("--Enter Proper PR Type--");
                    break;
            }
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void shipToYokogawa() {
        try {
            String shipToYokogawaValue = properties.getProperty("shipToYokogawa").toLowerCase().trim();
            if (shipToYokogawaValue.equals("no")) {
                page.locator(SHIP_TO_YOKOGAWA).check();
            }
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void project() {
        try {
            Locator projectLocator = page.locator(PROJECT);
            waitForLocator(projectLocator);
            projectLocator.click();

            String projectCodeValue = properties.getProperty("projectCode");
            Locator projectSearchLocator = page.locator(PROJECT_SEARCH);
            waitForLocator(projectSearchLocator);
            projectSearchLocator.fill(projectCodeValue);

            String projectSelectLocator = getProject(projectCodeValue);
            Locator projectSelectElement = page.locator(projectSelectLocator);
            waitForLocator(projectSelectElement);
            projectSelectElement.click();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void wbs() {
        try {
            if (prType.toUpperCase().equals("MH")) {
                Locator wbsLocator = page.locator(WBS);
                waitForLocator(wbsLocator);
                wbsLocator.click();

                List<String> wbsResultsList = page.locator(WBS_LIST).allTextContents();
                for (int i = 0; i < wbsResultsList.size(); i++) {
                    String wbsResult = wbsResultsList.get(i);
                    if (wbsResult.endsWith("E")) {
                        String wbsForMh = getWBSForMh(wbsResult);
                        Locator finalWbsLocator = page.locator(wbsForMh);
                        waitForLocator(finalWbsLocator);
                        finalWbsLocator.first().click();
                        break;
                    }
                }
            } else {
                Locator wbsLocator = page.locator(WBS);
                waitForLocator(wbsLocator);
                wbsLocator.click();

                String wbsCodeValue = properties.getProperty("wbsCode");
                Locator wbsSearchLocator = page.locator(WBS_SEARCH);
                waitForLocator(wbsSearchLocator);
                wbsSearchLocator.fill(wbsCodeValue);

                String wbsSelectLocator = getWBSForCandNC(wbsCodeValue);
                Locator finalWbsLocator = page.locator(wbsSelectLocator);
                waitForLocator(finalWbsLocator);
                finalWbsLocator.click();
            }
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void vendor() {
        try {
            Locator vendorLocator = page.locator(VENDOR);
            waitForLocator(vendorLocator);
            vendorLocator.click();

            String vendorNameValue = properties.getProperty("vendorEmail");
            Locator vendorSearchLocator = page.locator(VENDOR_SEARCH);
            waitForLocator(vendorSearchLocator);
            vendorSearchLocator.fill(vendorNameValue);

            String vendorOptionLocator = getVendor(vendorNameValue);
            Locator vendorOption = page.locator(vendorOptionLocator);
            waitForLocator(vendorOption);
            vendorOption.click();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void rateContract() {
        try {
            Locator rateContractLocator = page.locator(RATE_CONTRACT);
            waitForLocator(rateContractLocator);
            rateContractLocator.click();

            String rateContractValue = properties.getProperty("rateContract");
            Locator rateContractSearchLocator = page.locator(RATE_CONTRACT_SEARCH);
            waitForLocator(rateContractSearchLocator);
            rateContractSearchLocator.fill(rateContractValue);

            String rateContractOptionLocator = getRateContract(rateContractValue);
            Locator rateContractOption = page.locator(rateContractOptionLocator);
            waitForLocator(rateContractOption);
            rateContractOption.click();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void incoterm() {
        try {
            Locator incotermLocator = page.locator(INCOTERM);
            waitForLocator(incotermLocator);
            incotermLocator.click();

            String incotermValue = properties.getProperty("incoterm");
            Locator incotermSearchLocator = page.locator(INCOTERM_SEARCH);
            incotermSearchLocator.fill(incotermValue);

            String incotermOptionLocator = getIncoterm(incotermValue);
            Locator incotermOption = page.locator(incotermOptionLocator);
            waitForLocator(incotermOption);
            incotermOption.click();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void shippingAddress() {
        try {
            Locator shippingAddressLocator = page.locator(SHIPPING_ADDRESS);
            waitForLocator(shippingAddressLocator);
            shippingAddressLocator.click();

            String shippingAddressValue = properties.getProperty("shippingAddress");
            String shippingAddressOptionLocator = getShippingAddress(shippingAddressValue);

            Locator shippingAddressOption = page.locator(shippingAddressOptionLocator);
            waitForLocator(shippingAddressOption);
            shippingAddressOption.last().click();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void billingType() {
        try {
            Locator billingTypeLocator = page.locator(BILLING_TYPE);
            waitForLocator(billingTypeLocator);
            billingTypeLocator.click();

            String selectedBillingType = properties.getProperty("billingType");
            String billingTypeOptionLocator = getBillingType(selectedBillingType);

            Locator billingTypeOption = page.locator(billingTypeOptionLocator);
            waitForLocator(billingTypeOption);
            billingTypeOption.last().click();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void shippingMode() {
        try {
            String shippingMode = prType.equals("Catalog") ? CATALOG_SHIPPING_MODE : NON_CATALOG_MH_SHIPPING_MODE;

            Locator shippingModeLocator = page.locator(shippingMode);
            waitForLocator(shippingModeLocator);
            shippingModeLocator.click();

            String getShippingMode = properties.getProperty("shippingMode");

            Locator shippingModeSearch = page.locator(SHIPPING_MODE_SEARCH);
            waitForLocator(shippingModeSearch);
            shippingModeSearch.fill(getShippingMode);

            String finalShippingMode = getShippingMode(getShippingMode);

            Locator finalShippingModeLocator = page.locator(finalShippingMode);
            waitForLocator(finalShippingModeLocator);
            finalShippingModeLocator.click();
        } catch (Exception error) {
            System.out.println("Error encountered: " + error.getMessage());
        }
    }

    public void quotationRequiredBy() {
        try {
            Locator quotationRequiredByField = page.locator(QUOTATION_REQUIRED_BY);
            waitForLocator(quotationRequiredByField);
            quotationRequiredByField.click();

            Locator todayOption = page.locator(TODAY);
            waitForLocator(todayOption);
            todayOption.first().click();
        } catch (Exception error) {
            System.out.println("Error encountered: " + error.getMessage());
        }
    }

    public void expectedPOIssue() {
        try {
            Locator expectedPoIssueField = page.locator(EXPECTED_PO_ISSUE);
            waitForLocator(expectedPoIssueField);
            expectedPoIssueField.click();

            Locator todayOption = page.locator(TODAY);
            waitForLocator(todayOption);
            todayOption.first().click();
        } catch (Exception error) {
            System.out.println("Error encountered: " + error.getMessage());
        }
    }

    public void expectedDelivery() {
        try {
            Locator expectedDeliveryField = page.locator(EXPECTED_DELIVERY);
            waitForLocator(expectedDeliveryField);
            expectedDeliveryField.click();

            Locator todayOption = page.locator(TODAY);
            waitForLocator(todayOption);
            todayOption.first().click();
        } catch (Exception error) {
            System.out.println("Error encountered: " + error.getMessage());
        }
    }

//TODO There is no Buyer Manager in YCA
//    public void buyerManager(){
//        try {
//            Locator buyerManagerDropdown = page.locator(BUYER_MANAGER);
//            waitForLocator(buyerManagerDropdown);
//            buyerManagerDropdown.click();
//
//            String buyerManagerName = properties.getProperty("buyerManagerEmail");
//
//            Locator buyerManagerSearch = page.locator(BUYER_MANAGER_SEARCH);
//            waitForLocator(buyerManagerSearch);
//            buyerManagerSearch.fill(buyerManagerName);
//
//            String buyerManagerLocator = getBuyerManager(buyerManagerName);
//            Locator finalBuyerManagerSelect = page.locator(buyerManagerLocator);
//            waitForLocator(finalBuyerManagerSelect);
//            finalBuyerManagerSelect.click();
//        } catch (Exception error) {
//            System.out.println("Error encountered: " + error.getMessage());
//        }
//    }

//TODO There is no Project Manager in YCA
//    public void projectManager(){
//        try {
//            Locator projectManagerDropdown = page.locator(PROJECT_MANAGER);
//            waitForLocator(projectManagerDropdown);
//            projectManagerDropdown.click();
//
//            String projectManagerName = properties.getProperty("projectManagerEmail");
//
//            Locator projectManagerSearch = page.locator(PROJECT_MANAGER_SEARCH);
//            waitForLocator(projectManagerSearch);
//            projectManagerSearch.fill(projectManagerName);
//
//            String projectManagerLocator = getProjectManager(projectManagerName);
//            Locator finalProjectManager = page.locator(projectManagerLocator);
//            waitForLocator(finalProjectManager);
//            finalProjectManager.click();
//        } catch (Exception error) {
//            System.out.println("Error encountered: " + error.getMessage());
//        }
//    }

    public void rohsCompliance(){
        try {
            String compliance = properties.getProperty("rohsCompliance").toLowerCase().trim();

            if (compliance.equals("no")) {
                Locator rohsComplianceLocator = page.locator(ROHS_COMPLIANCE);
                waitForLocator(rohsComplianceLocator);
                rohsComplianceLocator.click();
            }
        } catch (Exception error) {
            System.out.println("Error encountered: " + error.getMessage());
        }
    }

    public void oiAndTpCurrency(){
        try {
            if (prType.equals("NonCatalog")) {

                Locator oiAndTpCurrencyLocator = page.locator(OI_AND_TP_CURRENCY);
                waitForLocator(oiAndTpCurrencyLocator);
                oiAndTpCurrencyLocator.click();

                String currency = properties.getProperty("oiAndTpCurrency").toLowerCase().trim();

                Locator oiAndTpCurrencySearchLocator = page.locator(OI_AND_TP_CURRENCY_SEARCH);
                waitForLocator(oiAndTpCurrencySearchLocator);
                oiAndTpCurrencySearchLocator.fill(currency);

                String currencySelect = getOiAndTpCurrency(currency);
                Locator currencySelectLocator = page.locator(currencySelect);
                waitForLocator(currencySelectLocator);
                currencySelectLocator.click();
            }
        } catch (Exception error) {
            System.out.println("Error encountered: " + error.getMessage());
        }
    }

    public void orderIntake(){
        try {
            String orderIntake = properties.getProperty("orderIntake");
            Locator orderIntakeLocator = page.locator(ORDER_INTAKE);
            waitForLocator(orderIntakeLocator);
            orderIntakeLocator.fill(orderIntake);
        } catch (Exception error) {
            System.out.println("Error encountered: " + error.getMessage());
        }
    }

    public void targetPrice(){
        try {
            if (prType.equals("NonCatalog")) {
                String targetPrice = properties.getProperty("targetPrice");
                Locator targetPriceLocator = page.locator(TARGET_PRICE);
                waitForLocator(targetPriceLocator);
                targetPriceLocator.fill(targetPrice);
            }
        } catch (Exception error) {
            System.out.println("Error encountered: " + error.getMessage());
        }
    }

    public void warrantyRequirements(){
        try {
            if (prType.equals("NonCatalog")) {
                Locator warrantyRequirementsLocator = page.locator(WARRANTY_REQUIREMENTS);
                waitForLocator(warrantyRequirementsLocator);
                warrantyRequirementsLocator.click();

                String warrantyRequirement = properties.getProperty("warrantyRequirement");

                Locator warrantyRequirementsSearchLocator = page.locator(WARRANTY_REQUIREMENTS_SEARCH);
                waitForLocator(warrantyRequirementsSearchLocator);
                warrantyRequirementsSearchLocator.fill(warrantyRequirement);

                String warrantyRequirementSelector = getWarrantyRequirements(warrantyRequirement);
                Locator warrantyRequirementOptionLocator = page.locator(warrantyRequirementSelector);
                waitForLocator(warrantyRequirementOptionLocator);
                warrantyRequirementOptionLocator.click();
            }
        } catch (Exception error) {
            System.out.println("Error encountered: " + error.getMessage());
        }
    }

    public void priceValidity(){
        try {
            Locator priceValidityLocator = page.locator(PRICE_VALIDITY);
            waitForLocator(priceValidityLocator);
            priceValidityLocator.click();

            String warrantyRequirement = properties.getProperty("priceValidity");

            Locator priceValiditySearchLocator = page.locator(PRICE_VALIDITY_SEARCH);
            waitForLocator(priceValiditySearchLocator);
            priceValiditySearchLocator.fill(warrantyRequirement);

            String priceValiditySelector = getPriceValidity(warrantyRequirement);
            Locator priceValidityOptionLocator = page.locator(priceValiditySelector);
            waitForLocator(priceValidityOptionLocator);
            priceValidityOptionLocator.click();
        } catch (Exception error) {
            System.out.println("Error encountered: " + error.getMessage());
        }
    }

    public void inspectionRequired() {
        try {
            boolean isInspectionRequired = Boolean.parseBoolean(properties.getProperty("inspectionRequired"));

            if (isInspectionRequired) {
                Locator inspectionRequiredLocator = page.locator(INSPECTION_REQUIRED);
                waitForLocator(inspectionRequiredLocator);
                inspectionRequiredLocator.click();
            }
        } catch (Exception error) {
            System.out.println("Error encountered: " + error.getMessage());
        }
    }

    public void liquidatedDamages(){
        try {
            String liquidatedDamages = properties.getProperty("liquidatedDamages").toLowerCase().trim();

            if (liquidatedDamages.equals("special")) {
                Locator liquidatedDamagesSelector = page.locator(LIQUIDATED_DAMAGES_SELECT);
                waitForLocator(liquidatedDamagesSelector);
                liquidatedDamagesSelector.click();

                Locator liquidatedDamagesInput = page.locator(LIQUIDATED_DAMAGES);
                waitForLocator(liquidatedDamagesInput);
                liquidatedDamagesInput.fill("Special Liquidated Damages");
            }
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void addLineRequisitionItems() {
        try {
            Locator addItemButton = null;
            Locator addLineItemButton = page.locator(ADD_LINE_ITEM_BUTTON);
            waitForLocator(addLineItemButton);
            addLineItemButton.click();

            Locator itemsDropdown = page.locator(ITEMS);
            waitForLocator(itemsDropdown);
            itemsDropdown.click();

            if (prType.equals("Catalog")) {
                List<String> itemList = page.locator(ITEMS_LIST).allTextContents();
                for (int i = 1; i <= itemList.size(); i++) {
                    String itemName = itemList.get(i);
                    itemName.split(" - ");

                    if (i > 1) {
                        waitForLocator(addItemButton);
                        addLineItemButton.click();
                        waitForLocator(itemsDropdown);
                        itemsDropdown.click();
                    }

                    Locator itemSearchBox = page.locator(ITEM_SEARCH);
                    waitForLocator(itemSearchBox);
                    itemSearchBox.fill(itemName);

                    Locator itemOption = page.locator(getItem(itemName));
                    waitForLocator(itemOption);
                    itemOption.first().click();

                    Locator quantityField = page.locator(QUANTITY);
                    waitForLocator(quantityField);
                    quantityField.fill(String.valueOf(i));

                    addItemButton = page.locator(ADD_ITEM_BUTTON);
                    waitForLocator(addItemButton);
                    addItemButton.click();
                }
            } else if (prType.equals("NonCatalog")) {
                String[] itemNames = properties.getProperty("items").split(",");
                String[] quantities = properties.getProperty("quantityList").split(",");

                for (int i = 0; i < itemNames.length; i++) {
                    waitForLocator(addItemButton);
                    addLineItemButton.click();

                    waitForLocator(itemsDropdown);
                    itemsDropdown.click();

                    Locator itemSearchBox = page.locator(ITEM_SEARCH);
                    waitForLocator(itemSearchBox);
                    itemSearchBox.fill(itemNames[i]);

                    Locator itemOption = page.locator(getItem(itemNames[i]));
                    waitForLocator(itemOption);
                    itemOption.first().click();

                    Locator quantityField = page.locator(QUANTITY);
                    waitForLocator(quantityField);
                    quantityField.fill(quantities[i]);

                    waitForLocator(addItemButton);
                    addItemButton.click();
                }
            } else if (prType.equalsIgnoreCase("MH")) {
                String mhItemName = properties.getProperty("mhItem");

                Locator itemSearchBox = page.locator(ITEM_SEARCH);
                waitForLocator(itemSearchBox);
                itemSearchBox.fill(mhItemName);

                Locator itemOption = page.locator(getItem(mhItemName));
                waitForLocator(itemOption);
                itemOption.first().click();

                Locator quantityField = page.locator(QUANTITY);
                waitForLocator(quantityField);
                quantityField.fill("20");

                waitForLocator(addItemButton);
                addItemButton.click();
            }
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }

    }

    public void notes() {
        try {
            String notesText = properties.getProperty("requisitionNotes");
            Locator notesField = page.locator(NOTES);
            waitForLocator(notesField);
            notesField.fill(notesText);
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void attachments(){
        try {
//TODO Internal Attachment
            Locator attachmentsButton = page.locator(ATTACHMENTS);
            waitForLocator(attachmentsButton);
            attachmentsButton.click();

            Locator internalFileUpload = page.locator(FILE_UPLOAD);
            waitForLocator(internalFileUpload);
            internalFileUpload.setInputFiles(Paths.get(properties.getProperty("internalFilePath")));

            Locator attachInternalFileButton = page.locator(ATTACH_FILE_BUTTON);
            waitForLocator(attachInternalFileButton);
            attachInternalFileButton.click();

//TODO External Attachment
            Locator externalAttachmentsButton = page.locator(ATTACHMENTS);
            waitForLocator(externalAttachmentsButton);
            externalAttachmentsButton.click();

            Locator externalFileUpload = page.locator(FILE_UPLOAD);
            waitForLocator(externalFileUpload);
            externalFileUpload.setInputFiles(Paths.get(properties.getProperty("externalFilePath")));

            Locator externalRadioButton = page.locator(EXTERNAL_RADIO_BUTTON);
            waitForLocator(externalRadioButton);
            externalRadioButton.click();

            Locator attachExternalFileButton = page.locator(ATTACH_FILE_BUTTON);
            waitForLocator(attachExternalFileButton);
            attachExternalFileButton.click();

            Locator continueButton = page.locator(CONTINUE_BUTTON);
            waitForLocator(continueButton);
            continueButton.click();

        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void prCreate() {
        try {
            Locator createDraftButtonLocator = page.locator(CREATE_DRAFT_BUTTON);
            waitForLocator(createDraftButtonLocator);
            createDraftButtonLocator.click();

            Locator yesButtonLocator = page.locator(YES);
            waitForLocator(yesButtonLocator);
            yesButtonLocator.click();

            iLogout.performLogout();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }
}