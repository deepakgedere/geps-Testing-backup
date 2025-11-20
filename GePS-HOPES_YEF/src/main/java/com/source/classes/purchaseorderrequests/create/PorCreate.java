package com.source.classes.purchaseorderrequests.create;
import com.constants.purchaseorderrequests.LPorCreate;
import com.constants.requestforquotations.LQuoRequote;
import com.constants.requisitions.LPrEdit;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.RequestOptions;
import com.source.interfaces.purchaseorderrequests.IPorCreate;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import com.utils.rpa.salesordersync.PR_List_Flow;
import org.apache.logging.log4j.Logger;
import static com.constants.purchaseorderrequests.LPorCreate.*;
import static com.constants.requestforquotations.LCeCreate.*;
import static com.constants.requestforquotations.LCeCreate.CREATE_BUTTON;
import static com.constants.requestforquotations.LCeCreate.SUBMIT_BUTTON;
import static com.constants.requestforquotations.LQuoRequote.*;
import static com.constants.requestforquotations.LQuoSubmit.RFQ_NAVIGATION_BAR;
import static com.constants.requisitions.LPrEdit.YES;
import static com.utils.GetTitleUtil.*;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class PorCreate implements IPorCreate {

    Logger logger;
    PlaywrightFactory playwrightFactory;
    ObjectMapper objectMapper;
    JsonNode jsonNode;
    PR_List_Flow prListFlow;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    String appUrl;
    int status = 0;

//TODO Constructor
    private PorCreate(){
    }

    public PorCreate(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, PlaywrightFactory playwrightFactory, ObjectMapper objectMapper, PR_List_Flow prListFlow){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.objectMapper = objectMapper;
        this.playwrightFactory = playwrightFactory;
        this.prListFlow = prListFlow;
        this.logger = LoggerUtil.getLogger(PorCreate.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public void porCreateButtonForCatalog(String type, String purchaseType) {
        try {
            String buyerMailId = jsonNode.get("mailIds").get("buyerEmail").asText();
            iLogin.performLogin(buyerMailId);

            String title = getTransactionTitle(type, purchaseType);
            Locator titleLocator = page.locator(LPorCreate.getTitle(title));
            locatorVisibleHandler(titleLocator);
            titleLocator.first().click();

            if(type.equalsIgnoreCase("Sales")){
                Locator sendToYQuoteButtonLocator = page.locator(SEND_TO_Y_QUOTE_BUTTON);
                locatorVisibleHandler(sendToYQuoteButtonLocator);
                sendToYQuoteButtonLocator.click();

                Locator yesButtonLocator = page.locator(YES_BUTTON);
                locatorVisibleHandler(yesButtonLocator);
                yesButtonLocator.click();

                prListFlow.prListFlow(purchaseType);

                iLogout.performLogout();

                String requesterMailId = jsonNode.get("mailIds").get("requesterEmail").asText();
                iLogin.performLogin(requesterMailId);

                titleLocator.first().click();

                Locator convertSmToOmButtonLocator = page.locator(CONVERT_SM_TO_OM_BUTTON);
                locatorVisibleHandler(convertSmToOmButtonLocator);
                convertSmToOmButtonLocator.click();

                Locator departmentPicDropDownLocator = page.locator(DEPARTMENT_PIC_DROP_DOWN);
                locatorVisibleHandler(departmentPicDropDownLocator);
                departmentPicDropDownLocator.click();

                Locator departmentPicMailIdLocator = page.locator(getDepartmentPic(requesterMailId));
                locatorVisibleHandler(departmentPicMailIdLocator);
                departmentPicMailIdLocator.first().click();

                Locator updateButtonLocator = page.locator(LPorCreate.UPDATE_BUTTON);
                locatorVisibleHandler(updateButtonLocator);
                updateButtonLocator.click();

                Locator quantityMismatchPopup = page.locator(QUANTITY_MISMATCH_POPUP);
                locatorVisibleHandler(quantityMismatchPopup);

                if(quantityMismatchPopup.isEnabled()) {
                    yesButtonLocator.click();

                    iLogout.performLogout();

                    iLogin.performLogin(requesterMailId);

                    titleLocator.first().click();

                    Locator editButtonLocator = page.locator(LPrEdit.EDIT_BUTTON);
                    locatorVisibleHandler(editButtonLocator);
                    editButtonLocator.click();

                    Locator prUpdateButtonLocator = page.locator(LPrEdit.UPDATE_BUTTON);
                    locatorVisibleHandler(prUpdateButtonLocator);
                    prUpdateButtonLocator.click();

                    Locator submitButtonLocator = page.locator(LPorCreate.SUBMIT_BUTTON);
                    locatorVisibleHandler(submitButtonLocator);
                    submitButtonLocator.click();

                    iLogout.performLogout();

                    iLogin.performLogin(buyerMailId);

                    titleLocator.first().click();

                    Locator porCreateButtonLocator = page.locator(CATALOG_POR_CREATE_BUTTON);
                    locatorVisibleHandler(porCreateButtonLocator);
                    porCreateButtonLocator.first().click();
                } else {
                    iLogout.performLogout();

                    iLogin.performLogin(buyerMailId);

                    titleLocator.first().click();

                    Locator porCreateButtonLocator = page.locator(CATALOG_POR_CREATE_BUTTON);
                    locatorVisibleHandler(porCreateButtonLocator);
                    porCreateButtonLocator.first().click();
                }
            } else {
                Locator porCreateButtonLocator = page.locator(CATALOG_POR_CREATE_BUTTON);
                locatorVisibleHandler(porCreateButtonLocator);
                porCreateButtonLocator.first().click();
            }

            boolean advancePaymentFlag = jsonNode.get("purchaseOrderRequests").get("advancePaymentFlag").asBoolean();
            boolean milestonePaymentFlag = jsonNode.get("purchaseOrderRequests").get("milestonePaymentFlag").asBoolean();

            if(advancePaymentFlag || milestonePaymentFlag){
                advanceAndMilestonePayments(advancePaymentFlag, milestonePaymentFlag, type, purchaseType);
            }
        } catch (Exception exception) {
            logger.error("Exception in POR Create For Catalog Type Function: {}", exception.getMessage());
        }
    }

    public void porCreateButtonForNonCatalog(String type, String purchaseType) {
        try {
            String buyerMailId = jsonNode.get("mailIds").get("buyerEmail").asText();
            iLogin.performLogin(buyerMailId);

            Locator rfqNavigationBarLocator = page.locator(RFQ_NAVIGATION_BAR);
            locatorVisibleHandler(rfqNavigationBarLocator);
            rfqNavigationBarLocator.click();

            String title = getRFQTransactionTitle(type);
            Locator titleLocator = page.locator(LPorCreate.getTitle(title));
            locatorVisibleHandler(titleLocator);
            titleLocator.first().click();

            if(type.equalsIgnoreCase("Sales")){
                Locator sendToYQuoteButtonLocator = page.locator(SEND_TO_Y_QUOTE_BUTTON);
                locatorVisibleHandler(sendToYQuoteButtonLocator);
                sendToYQuoteButtonLocator.click();

                Locator yesButtonLocator = page.locator(YES_BUTTON);
                locatorVisibleHandler(yesButtonLocator);
                yesButtonLocator.click();

                prListFlow.prListFlow(purchaseType);

                iLogout.performLogout();

                String requesterMailId = jsonNode.get("mailIds").get("requesterEmail").asText();
                iLogin.performLogin(requesterMailId);

                rfqNavigationBarLocator.click();

                titleLocator.first().click();

                Locator convertSmToOmButtonLocator = page.locator(NON_CATALOG_CONVERT_SM_TO_OM_BUTTON);
                locatorVisibleHandler(convertSmToOmButtonLocator);
                convertSmToOmButtonLocator.click();

                Locator departmentPicDropDownLocator = page.locator(DEPARTMENT_PIC_DROP_DOWN);
                locatorVisibleHandler(departmentPicDropDownLocator);
                departmentPicDropDownLocator.click();

                Locator departmentPicMailIdLocator = page.locator(getDepartmentPic(requesterMailId));
                locatorVisibleHandler(departmentPicDropDownLocator);
                departmentPicMailIdLocator.first().click();

                Locator updateButtonLocator = page.locator(LPorCreate.RFQ_UPDATE_BUTTON);
                locatorVisibleHandler(updateButtonLocator);
                updateButtonLocator.click();

                Locator quantityMismatchPopup = page.locator(QUANTITY_MISMATCH_POPUP);
                locatorVisibleHandler(quantityMismatchPopup);

                if(quantityMismatchPopup.isEnabled()) {
                    yesButtonLocator.click();

                    iLogout.performLogout();

                    iLogin.performLogin(buyerMailId);

                    rfqNavigationBarLocator.click();

                    titleLocator.first().click();

                    Locator requoteButtonLocator = page.locator(LQuoRequote.REQUOTE_BUTTON);
                    locatorVisibleHandler(requoteButtonLocator);
                    requoteButtonLocator.click();

                    Locator submitButtonLocator = page.locator(LQuoRequote.ACCEPT_REMARKS_POP_UP);
                    locatorVisibleHandler(submitButtonLocator);
                    submitButtonLocator.click();

                    Locator emailSubmitButtonLocator = page.locator(EMAIL_POP_UP);
                    locatorVisibleHandler(emailSubmitButtonLocator);
                    emailSubmitButtonLocator.click();

                    iLogout.performLogout();

                    String vendorMailId = jsonNode.get("mailIds").get("vendorEmail").asText();
                    iLogin.performLogin(vendorMailId);

                    titleLocator.first().click();

                    Locator requoteEditButtonLocator = page.locator(REQUOTE_EDIT_BUTTON);
                    locatorVisibleHandler(requoteEditButtonLocator);
                    requoteEditButtonLocator.click();

                    Locator updateButtonLocator1 = page.locator(LQuoRequote.UPDATE_BUTTON);
                    locatorVisibleHandler(updateButtonLocator1);
                    updateButtonLocator1.click();

                    Locator acceptLocator = page.locator(ACCEPT_REMARKS_POP_UP);
                    locatorVisibleHandler(acceptLocator);
                    acceptLocator.click();

                    iLogout.performLogout();

                    iLogin.performLogin(buyerMailId);

                    rfqNavigationBarLocator.click();

                    titleLocator.first().click();

                    Locator createButtonLocator = page.locator(CE_CREATE_BUTTON);
                    locatorVisibleHandler(createButtonLocator);
                    createButtonLocator.click();

                    Locator selectionStatusLocator = page.locator(SELECTION_CRITERIA);
                    locatorVisibleHandler(selectionStatusLocator);
                    selectionStatusLocator.click();
                    selectionStatusLocator.selectOption(OPTION);

                    Locator submitButtonLocator1 = page.locator(SUBMIT_BUTTON);
                    locatorVisibleHandler(submitButtonLocator1);
                    submitButtonLocator1.click();

                    Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
                    locatorVisibleHandler(acceptButtonLocator);
                    acceptButtonLocator.click();

                    Locator porCreateButtonLocator = page.locator(NON_CATALOG_POR_CREATE_BUTTON);
                    locatorVisibleHandler(porCreateButtonLocator);
                    porCreateButtonLocator.first().click();
                } else {
                    iLogout.performLogout();

                    iLogin.performLogin(buyerMailId);

                    rfqNavigationBarLocator.click();

                    titleLocator.first().click();

                    Locator porCreateButtonLocator = page.locator(NON_CATALOG_POR_CREATE_BUTTON);
                    locatorVisibleHandler(porCreateButtonLocator);
                    porCreateButtonLocator.first().click();
                }
            } else {
                Locator porCreateButtonLocator = page.locator(NON_CATALOG_POR_CREATE_BUTTON);
                locatorVisibleHandler(porCreateButtonLocator);
                porCreateButtonLocator.first().click();
            }

            boolean advancePaymentFlag = jsonNode.get("purchaseOrderRequests").get("advancePaymentFlag").asBoolean();
            boolean milestonePaymentFlag = jsonNode.get("purchaseOrderRequests").get("milestonePaymentFlag").asBoolean();

            if(advancePaymentFlag || milestonePaymentFlag){
                advanceAndMilestonePayments(advancePaymentFlag, milestonePaymentFlag, type, purchaseType);
            }

        } catch (Exception exception) {
            logger.error("Exception in POR Create For Non-Catalog Type Function: {}", exception.getMessage());
        }
    }

    public void advanceAndMilestonePayments(boolean advancePaymentFlag, boolean milestonePaymentFlag, String type, String purchaseType) {
        try {
            String advancePaymentPercentage = jsonNode.get("purchaseOrderRequests").get("advancePaymentPercentage").asText();
            String creditPeriodInDays = jsonNode.get("purchaseOrderRequests").get("creditPeriodInDays").asText();

            if(advancePaymentFlag){
                Locator advancePaymentButtonLocator = page.locator(ADVANCE_PAYMENT_BUTTON);
                locatorVisibleHandler(advancePaymentButtonLocator);
                advancePaymentButtonLocator.click();

                Locator advancePaymentNameLocator = page.locator(ADVANCE_PAYMENT_NAME);
                locatorVisibleHandler(advancePaymentNameLocator);
                advancePaymentNameLocator.fill("Advance Payment - 1");

                Locator advancePaymentPercentageLocator = page.locator(ADVANCE_PAYMENT_PERCENTAGE);
                locatorVisibleHandler(advancePaymentPercentageLocator);
                advancePaymentPercentageLocator.fill(advancePaymentPercentage);

                Locator advancePaymentCreditPeriodInDaysLocator = page.locator(ADVANCE_PAYMENT_CREDIT_PERIOD_IN_DAYS);
                locatorVisibleHandler(advancePaymentCreditPeriodInDaysLocator);
                advancePaymentCreditPeriodInDaysLocator.clear();
                advancePaymentCreditPeriodInDaysLocator.fill(creditPeriodInDays);

                Locator submitButtonLocator = page.locator(SUBMIT_ADVANCE_PAYMENT_BUTTON);
                locatorVisibleHandler(submitButtonLocator);
                submitButtonLocator.click();

                //TODO Milestone Payment
                int milestoneCount = jsonNode.get("purchaseOrderRequests").get("milestonePaymentCount").asInt();
                int reminder = 100 % milestoneCount;
                int percentage = 100 / milestoneCount;

                for(int i = 1; i <= milestoneCount; i++){
                    Locator milestoneButtonLocator = page.locator(MILESTONE_PAYMENT_BUTTON);
                    locatorVisibleHandler(milestoneButtonLocator);
                    milestoneButtonLocator.click();

                    Locator milestonePaymentNameLocator;
                    if (type.equalsIgnoreCase("PS") && purchaseType.equalsIgnoreCase("Catalog BOP2/BOP5")){
                        milestonePaymentNameLocator = page.locator(SD_NON_CATALOG_MILESTONE_PAYMENT_PERCENTAGE);
                    }else if (type.equalsIgnoreCase("PS") && purchaseType.equalsIgnoreCase("NonCatalog")) {
                        milestonePaymentNameLocator = page.locator(SD_NON_CATALOG_MILESTONE_PAYMENT_PERCENTAGE);
                    }
                    else if(purchaseType.equalsIgnoreCase("Catalog") || purchaseType.equalsIgnoreCase("Punchout")) {
                        milestonePaymentNameLocator = page.locator(CATALOG_MILESTONE_PAYMENT_NAME);
                    } else if (type.equalsIgnoreCase("SD") && purchaseType.equalsIgnoreCase("NonCatalog")) {
                        milestonePaymentNameLocator = page.locator(SD_NON_CATALOG_MILESTONE_PAYMENT_PERCENTAGE);
                    } else if (type.equalsIgnoreCase("SD") && purchaseType.equalsIgnoreCase("Catalog BOP2/BOP5")) {
                        milestonePaymentNameLocator = page.locator(SD_NON_CATALOG_MILESTONE_PAYMENT_PERCENTAGE);
                    }
                    else {
                        milestonePaymentNameLocator = page.locator(NON_CATALOG_MILESTONE_PAYMENT_NAME);
                    }

                    locatorVisibleHandler(milestonePaymentNameLocator);
                    milestonePaymentNameLocator.fill("Milestone - " + i);

                    Locator milestonePaymentPercentageLocator = page.locator(MILESTONE_PAYMENT_PERCENTAGE);
                    locatorVisibleHandler(milestonePaymentPercentageLocator);
                    if(i == milestoneCount){
                        milestonePaymentPercentageLocator.fill(String.valueOf(percentage + reminder));
                    } else {
                        milestonePaymentPercentageLocator.fill(String.valueOf(percentage));
                    }
                    Locator submitButtonLocator1 = page.locator(SUBMIT_MILESTONE_PAYMENT_BUTTON);
                    locatorVisibleHandler(submitButtonLocator1);
                    submitButtonLocator1.click();
                }
            } else if (milestonePaymentFlag) {
                int milestoneCount = jsonNode.get("purchaseOrderRequests").get("milestonePaymentCount").asInt();
                int reminder = 100 % milestoneCount;
                int percentage = 100 / milestoneCount;

                for(int i = 1; i <= milestoneCount; i++){
                    Locator milestoneButtonLocator = page.locator(MILESTONE_PAYMENT_BUTTON);
                    locatorVisibleHandler(milestoneButtonLocator);
                    milestoneButtonLocator.click();

                    Locator milestonePaymentNameLocator;
                    if(purchaseType.equalsIgnoreCase("Catalog")){
                        milestonePaymentNameLocator = page.locator(CATALOG_MILESTONE_PAYMENT_NAME);
                    } else {
                        milestonePaymentNameLocator = page.locator(NON_CATALOG_MILESTONE_PAYMENT_NAME);
                    }

                    locatorVisibleHandler(milestoneButtonLocator);
                    milestonePaymentNameLocator.fill("Milestone - " + i);

                    Locator milestonePaymentPercentageLocator = page.locator(MILESTONE_PAYMENT_PERCENTAGE);
                    locatorVisibleHandler(milestonePaymentPercentageLocator);
                    if(i == milestoneCount){
                        milestonePaymentPercentageLocator.fill(String.valueOf(percentage + reminder));
                    } else {
                        milestonePaymentPercentageLocator.fill(String.valueOf(percentage));
                    }
                    Locator submitButtonLocator1 = page.locator(SUBMIT_MILESTONE_PAYMENT_BUTTON);
                    locatorVisibleHandler(submitButtonLocator1);
                    submitButtonLocator1.click();
                }
            }
        } catch (Exception exception) {
            logger.error("Exception in Advance and Milestone Payments Function: {}", exception.getMessage());
        }
    }

    public void justification(){
        try {
            Locator soleSupplier = page.locator(SOLE_SUPPLIER_NO);
            locatorVisibleHandler(soleSupplier);
            soleSupplier.click();
            Locator below5lLocator = page.locator(BELOW_5L);
            locatorVisibleHandler(below5lLocator);
            below5lLocator.click();
        } catch (Exception exception) {
            logger.error("Exception in Choose Justification Function: {}", exception.getMessage());
        }
    }

    public void taxCode(){
        try {
            Locator selectTaxCodeLocator = page.getByText(TAX_CODE);
            locatorVisibleHandler(selectTaxCodeLocator);
            selectTaxCodeLocator.last().click();

            String taxCode = jsonNode.get("purchaseOrderRequests").get("taxCode").asText();
            Locator taxCodeLocator = page.locator(getTaxCode(taxCode));
            locatorVisibleHandler(taxCodeLocator);
            taxCodeLocator.click();
        } catch (Exception exception) {
            logger.error("Exception in POR Create Tax Code Function: {}", exception.getMessage());
        }
    }

    public void porNotes() {
        try {
            String notes = jsonNode.get("purchaseOrderRequests").get("purchaseOrderNotes").asText();
            Locator porNotesLocator = page.locator(POR_NOTES);
            locatorVisibleHandler(porNotesLocator);
            porNotesLocator.fill(notes);
        } catch (Exception exception) {
            logger.error("Exception in POR Notes Function: {}", exception.getMessage());
        }
    }

    public int createButton(String type){
        int status = 0;
        try {
            Locator createButtonLocator = page.locator(POR_CREATE_BUTTON);
            locatorVisibleHandler(createButtonLocator);
            createButtonLocator.click();

            Locator yesButtonLocator = page.locator(YES);
            locatorVisibleHandler(yesButtonLocator);

            String porType;
            if(type.equalsIgnoreCase("sales")){
                porType = "/api/PurchaseOrderRequestsSales/";
            } else if(type.equalsIgnoreCase("ps")){
                porType = "/api/PurchaseOrderRequests/";
            } else {
                porType = "/api/PurchaseOrderRequestsNonPOC/";
            }

            Response createResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + porType) && response.status() == 200,
                    yesButtonLocator::click
            );
            status = createResponse.status();

            String url = page.url();
            String[] urlArray = url.split("=");
            String getUid = urlArray[1];
            playwrightFactory.savePropertiesIntoJsonFile("purchaseOrderRequests", "purchaseOrderRequestUid", getUid);

            APIResponse apiResponse = page.request().fetch(appUrl + porType + getUid, RequestOptions.create());
            JsonNode jsonNode = objectMapper.readTree(apiResponse.body());
            String purchaseOrderRequestId = jsonNode.get("id").asText();
            String porReferenceNumber = jsonNode.get("referenceId").asText();
            playwrightFactory.savePropertiesIntoJsonFile("purchaseOrderRequests", "purchaseOrderRequestId", purchaseOrderRequestId);
            playwrightFactory.savePropertiesIntoJsonFile("purchaseOrderRequests", "porReferenceNumber", porReferenceNumber);

            status = apiResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Purchase Order Request Create", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in POR Create Button Function: {}", exception.getMessage());
        }
        return status;
    }

    public int porCreate(String type, String purchaseType) {
        int status = 0;
        try {
            if (purchaseType.equalsIgnoreCase("NonCatalog")) {
                porCreateButtonForNonCatalog(type, purchaseType);
                justification();
            } else  {
                porCreateButtonForCatalog(type, purchaseType);
            }
            porNotes();
            status = createButton(type);
        }catch (Exception exception) {
            logger.error("Exception in POR Create Function: {}", exception.getMessage());
        }
        return status;
    }
}