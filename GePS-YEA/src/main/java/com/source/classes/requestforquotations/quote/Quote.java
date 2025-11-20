package com.source.classes.requestforquotations.quote;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.requestforquotations.IQuoSubmit;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.nio.file.Paths;
import java.util.List;
import static com.constants.requestforquotations.LQuoSubmit.*;
import static com.constants.requisitions.LPrEdit.getTitle;
import static com.utils.GetTitleUtil.getRFQTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class Quote implements IQuoSubmit {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    private String appUrl;

    private Quote(){
    }

//TODO Constructor
    public Quote(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout){
        this.logger = LoggerUtil.getLogger(Quote.class);
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public void inviteRegisteredVendor(String type){
        try {
            String buyerMailId = jsonNode.get("mailIds").get("buyerEmail").asText();
            iLogin.performLogin(buyerMailId);

            Locator rfqNavigationBar = page.locator(RFQ_NAVIGATION_BAR);
            locatorVisibleHandler(rfqNavigationBar);
            rfqNavigationBar.click();

            String title = getRFQTransactionTitle(type);
            Locator titleLocator = page.locator(getTitle(title));
            locatorVisibleHandler(titleLocator);
            titleLocator.first().click();

            Locator inviteVendorButton = page.locator(INVITE_VENDORS);
            locatorVisibleHandler(inviteVendorButton);
            inviteVendorButton.click();

            Locator vendorSearchFieldLocator = page.locator(VENDOR_SEARCH_FIELD);
            locatorVisibleHandler(vendorSearchFieldLocator);
            vendorSearchFieldLocator.click();

            String vendorId = jsonNode.get("requisition").get("vendorName").asText();
            Locator vendorSearchLocator = page.locator(VENDOR_SEARCH);
            locatorVisibleHandler(vendorSearchLocator);
            vendorSearchLocator.fill(vendorId);

            Locator getVendorLocator = page.locator(getVendor(vendorId));
            locatorVisibleHandler(getVendorLocator);
            getVendorLocator.first().click();

            Locator inviteVendorButtonLocator= page.locator(INVITE_BUTTON);
            locatorVisibleHandler(inviteVendorButtonLocator);
            inviteVendorButtonLocator.click();

            Locator vendorEmailPopUpLocator = page.locator(VENDOR_EMAIL_POP_UP);
            locatorVisibleHandler(vendorEmailPopUpLocator);
            vendorEmailPopUpLocator.click();

        iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Invite Registered Vendor Function: {}", exception.getMessage());
        }
    }

    public void vendorLogin(String type) {
        try {
            String vendorEmailId = jsonNode.get("mailIds").get("vendorEmail").asText();
            iLogin.performLogin(vendorEmailId);

            String title = getRFQTransactionTitle(type);
            Locator titleLocator = page.locator(getTitle(title));
            locatorVisibleHandler(titleLocator);
            titleLocator.first().click();

            Locator sendQuoteButtonLocator = page.locator(SEND_QUOTE_BUTTON);
            locatorVisibleHandler(sendQuoteButtonLocator);
            sendQuoteButtonLocator.click();

            String incoterm = jsonNode.get("requestForQuotation").get("incotermLocation").asText();
            Locator incotermLocationLocator = page.locator(INCOTERM_LOCATION);
            locatorVisibleHandler(incotermLocationLocator);
            incotermLocationLocator.fill(incoterm);

            String quotationReferenceNumber = jsonNode.get("requestForQuotation").get("quotationReferenceNumber").asText();
            Locator quotationRefNumLocator = page.locator(QUOTATION_REFERENCE_NUMBER);
            locatorVisibleHandler(quotationRefNumLocator);
            quotationRefNumLocator.fill(quotationReferenceNumber);

            Locator validityDateLocator = page.locator(VALIDITY_DATE);
            locatorVisibleHandler(validityDateLocator);
            validityDateLocator.click();

            Locator todayLocator = page.locator(TODAY);
            locatorVisibleHandler(todayLocator);

            int todayDayNumber = Integer.parseInt(todayLocator.textContent());
            int tomorrowDayNumber = todayDayNumber + 1;
            int nextDayAfterThirty = 31;

            if (todayDayNumber == 30) {
                Locator dayLocator = page.locator(getThirtyOne(nextDayAfterThirty));
                locatorVisibleHandler(dayLocator);
                if (dayLocator.isVisible() || !dayLocator.isHidden()) {
                    dayLocator.click();
                } else {
                    Locator nextMonthFirstDayLocator = page.locator(FIRST_DAY_OF_NEXT_MONTH);
                    locatorVisibleHandler(nextMonthFirstDayLocator);
                    nextMonthFirstDayLocator.first().click();
                }
            }
            if (todayDayNumber == 31) {
                Locator nextMonthFirstDayLocator = page.locator(FIRST_DAY_OF_NEXT_MONTH);
                locatorVisibleHandler(nextMonthFirstDayLocator);
                nextMonthFirstDayLocator.first().click();
            } else {
                Locator tomorrow = page.locator(getNextDay(tomorrowDayNumber));
                locatorVisibleHandler(tomorrow);
                tomorrow.last().click();
            }
        } catch (Exception exception) {
            logger.error("Exception in Vendor Login Function: {}", exception.getMessage());
        }
    }

    public void liquidatedDamages(){
        try {
            Locator liquidatedDamagesLocator = page.locator(LIQUIDATED_DAMAGES);
            locatorVisibleHandler(liquidatedDamagesLocator);
            liquidatedDamagesLocator.click();
        } catch (Exception exception) {
            logger.error("Exception in Liquidated Damages Function: {}", exception.getMessage());
        }
    }

    public void rohsCompliance(){
        try {
            Locator rohsCompliance = page.locator(ROHS_COMPLIANCE);
            locatorVisibleHandler(rohsCompliance);
            rohsCompliance.click();
        } catch (Exception exception) {
            logger.error("Exception in RoHS Compliance Function: {}", exception.getMessage());
        }
    }

    public void warrantyRequirements(){
        try {
            Locator warrantyRequirementsLocator = page.locator(WARRANTY_REQUIREMENTS);
            locatorVisibleHandler(warrantyRequirementsLocator);
            warrantyRequirementsLocator.click();
        } catch (Exception exception) {
            logger.error("Exception in Warranty Requirements Function: {}", exception.getMessage());
        }
    }

    public void quotationItems(){
        try {
        String hsCode = jsonNode.get("requestForQuotation").get("hsCode").asText();
        String make = jsonNode.get("requestForQuotation").get("makeDescription").asText();
        String model = jsonNode.get("requestForQuotation").get("modelDescription").asText();
        String partNumber = jsonNode.get("requestForQuotation").get("partNumber").asText();
        String countryOfOrigin = jsonNode.get("requestForQuotation").get("countryOfOrigin").asText();
        String rate = jsonNode.get("requestForQuotation").get("rate").asText();
        String discount = jsonNode.get("requestForQuotation").get("discount").asText();
        String leadTime = jsonNode.get("requestForQuotation").get("leadTimeDays").asText();
        String notes = jsonNode.get("requestForQuotation").get("quotationNotes").asText();

        List<String> itemSerialNumbers = page.locator(RFQ_ITEM_LIST).allTextContents();
        for(int i = 1; i <= itemSerialNumbers.size(); i++){

            Locator hsCodeLocator = page.locator(HS_CODE + i);
            locatorVisibleHandler(hsCodeLocator);
            hsCodeLocator.fill(hsCode);

            Locator makeLocator = page.locator(MAKE + i);
            locatorVisibleHandler(makeLocator);
            makeLocator.fill(make + i);

            Locator modelLocator = page.locator(MODEL + i);
            locatorVisibleHandler(modelLocator);
            modelLocator.fill(model + i);

            Locator partNumberLocator = page.locator(PART_NUMBER + i);
            locatorVisibleHandler(partNumberLocator);
            partNumberLocator.fill(partNumber + i);

            Locator countryOfOriginLocator = page.locator(COUNTRY_OF_ORIGIN + i);
            locatorVisibleHandler(countryOfOriginLocator);
            countryOfOriginLocator.fill(countryOfOrigin + i);

            Locator rateLocator = page.locator(RATE + i);
            locatorVisibleHandler(rateLocator);
            rateLocator.clear();
            rateLocator.fill(rate);

            Locator dicountLocator = page.locator(DISCOUNT + i);
            locatorVisibleHandler(dicountLocator);
            dicountLocator.clear();
            dicountLocator.fill(discount);

            Locator leatTimeLocator = page.locator(LEAD_TIME + i);
            locatorVisibleHandler(leatTimeLocator);
            leatTimeLocator.fill(leadTime);

            Locator quotationNotesLocator = page.locator(QUOTATION_NOTES + i);
            locatorVisibleHandler(quotationNotesLocator);
            quotationNotesLocator.fill(notes + i);
        }
        } catch (Exception exception) {
            logger.error("Exception in Quotation Items Function: {}", exception.getMessage());
        }
    }

    public void gst(){
        try {
            String gst = jsonNode.get("requestForQuotation").get("gstPercentage").asText();
            Locator gstLocator = page.locator(GST);
            locatorVisibleHandler(gstLocator);
            gstLocator.fill(gst);
        } catch (Exception error) {
            logger.error("Exception in GST Function: {}", error.getMessage());
        }
    }

    public void quotationAttachments() {
        try {
            String technicalAttachmentFilePath = jsonNode.get("configSettings").get("technicalAttachmentFilePath").asText();
            String commercialAttachmentFilePath = jsonNode.get("configSettings").get("commercialAttachmentFilePath").asText();

            uploadAttachments(technicalAttachmentFilePath, "Technical");
            uploadAttachments(commercialAttachmentFilePath, "Commercial");
        } catch (Exception exception) {
            logger.error("Exception in Quotation Attachments Function: {}", exception.getMessage());
        }
    }

    private void uploadAttachments(String filePath, String attachmentType) {
        try {
            Locator attachFileLocator = page.locator(ATTACH_FILE);
            locatorVisibleHandler(attachFileLocator);
            attachFileLocator.click();

            Locator fileInputLocator = page.locator(FILE_INPUT);
            locatorVisibleHandler(fileInputLocator);
            fileInputLocator.setInputFiles(Paths.get(filePath));

            Locator attachmentTypeDropDownLocator = page.locator(ATTACHMENT_TYPE_DROPDOWN);
            locatorVisibleHandler(attachmentTypeDropDownLocator);
            attachmentTypeDropDownLocator.click();

            Locator attachmentTypeLocator = page.locator(getAttatmentType(attachmentType));
            locatorVisibleHandler(attachmentTypeLocator);
            attachmentTypeLocator.click();

            Locator saveAttachemnt = page.locator(SAVE_ATTACHMENTS);
            locatorVisibleHandler(saveAttachemnt);
            saveAttachemnt.click();
        } catch (Exception exception) {
            logger.error("Exception in Upload Attachments Function: {}", exception.getMessage());
        }
    }

    public int quotationSubmitButton(String type){
        int status = 0;
        try {
            Locator createButtonLocator = page.locator(CREATE_BUTTON);
            locatorVisibleHandler(createButtonLocator);
            createButtonLocator.click();

            Locator acceptLocator = page.locator(ACCEPT_BUTTON_LOCATOR);
            locatorVisibleHandler(acceptLocator);

            String rfqType;
            if(type.equalsIgnoreCase("sales")) {
                rfqType = "/api/Vp/QuotationSales/";
            } else if (type.equalsIgnoreCase("POC")) {
                rfqType = "/api/Vp/Quotation/";
            } else {
                rfqType = "/api/Vp/QuotationNonPOC/";
            }

            Response submitResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + rfqType) && response.status() == 200,
                    acceptLocator::click
            );
            status = submitResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Quotation Submit", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Quotation Submit Button Function: {}", exception.getMessage());
        }
        return status;
    }
}