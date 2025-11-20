package com.source.classes.purchaseorderrequests.revision;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.RequestOptions;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.purchaseorderrequests.IPorApprove;
import com.source.interfaces.purchaseorderrequests.IPorRevision;
import com.source.interfaces.purchaseorderrequests.IPorSendForApproval;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.purchaseorderrequests.LPorRevision.*;
import static com.utils.GetTitleUtil.getTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class PorRevision implements IPorRevision {

    Logger logger;
    JsonNode jsonNode;
    PlaywrightFactory playwrightFactory;
    ObjectMapper objectMapper;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    IPorSendForApproval iPorSendForApproval;
    IPorApprove iPorApprove;
    String appUrl;

    private PorRevision(){
    }

//TODO Constructor
    public PorRevision(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, IPorSendForApproval iPorSendForApproval, IPorApprove iPorApprove, PlaywrightFactory playwrightFactory, ObjectMapper objectMapper) {
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.iPorSendForApproval = iPorSendForApproval;
        this.iPorApprove = iPorApprove;
        this.playwrightFactory = playwrightFactory;
        this.objectMapper = objectMapper;
        this.logger = LoggerUtil.getLogger(PorRevision.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int porRevision(String type, String purchaseType) {
        int status = 0;
        try {
            String buyerMailId = jsonNode.get("mailIds").get("buyerEmail").asText();
            String appUrl = jsonNode.get("configSettings").get("appUrl").asText();
            String porType;
            if(type.equalsIgnoreCase("sales")){
                porType = "/api/PurchaseOrderRequestsSales/";
            } else if(type.equalsIgnoreCase("ps")){
                porType = "/api/PurchaseOrderRequests/";
            } else {
                porType = "/api/PurchaseOrderRequestsNonPOC/";
            }
            iLogin.performLogin(buyerMailId);

            Locator porNavigationBarLocator = page.locator(POR_NAVIGATION_BAR);
            locatorVisibleHandler(porNavigationBarLocator);
            porNavigationBarLocator.click();

            String title = getTransactionTitle(type, purchaseType);
            Locator titleLocator = page.locator(getTitle(title));
            locatorVisibleHandler(titleLocator);
            titleLocator.first().click();

            String url = page.url();
            String[] urlArray = url.split("=");
            String getUid = urlArray[1];

            APIResponse apiResponse = page.request().fetch(appUrl + porType + getUid, RequestOptions.create());
            JsonNode jsonNode1 = objectMapper.readTree(apiResponse.body());
            JsonNode purchaseOrders = jsonNode1.get("purchaseOrders");
            boolean poProcessed;
            if (purchaseOrders != null && purchaseOrders.isArray() && purchaseOrders.size() > 0) {
                poProcessed = true;
            } else {
                poProcessed = false;
            }

            playwrightFactory.savePropertiesIntoJsonFile("purchaseOrderRequests", "poProcessed", String.valueOf(poProcessed));

            boolean poProcessedFlag = jsonNode.get("purchaseOrderRequests").get("poProcessed").asBoolean();
            if (!poProcessedFlag) {
                throw new Exception("POR Status is not PO Created");
            } else {
                Locator revisionRequestButton = page.locator(REQUEST_REVISION_BUTTON);
                locatorVisibleHandler(revisionRequestButton);
                revisionRequestButton.click();

                Locator justificationRequiredTextBoxLocator = page.locator(JUSTIFICATION_REQUIRED_TEXT_BOX);
                locatorVisibleHandler(justificationRequiredTextBoxLocator);
                justificationRequiredTextBoxLocator.fill("POR Revision");

                Locator submitButtonLocator = page.locator(ACCEPT_BUTTON);
                locatorVisibleHandler(submitButtonLocator);
                submitButtonLocator.click();

                Locator editButtonLocator = page.locator(EDIT_BUTTON);
                locatorVisibleHandler(editButtonLocator);
                editButtonLocator.click();

                Locator addAdditionalItemsLocator = page.locator(ADD_ADDITIONAL_ITEM);
                locatorVisibleHandler(addAdditionalItemsLocator);
                addAdditionalItemsLocator.click();

                String itemName = jsonNode.get("purchaseOrderRequests").get("additionalItemName").asText();
                String rate = jsonNode.get("purchaseOrderRequests").get("rate").asText();
                String hsCode = jsonNode.get("purchaseOrderRequests").get("hsCode").asText();
                String make = jsonNode.get("purchaseOrderRequests").get("make").asText();
                String model = jsonNode.get("purchaseOrderRequests").get("model").asText();
                String partNumber = jsonNode.get("purchaseOrderRequests").get("partNumber").asText();
                String countryOfOrigin = jsonNode.get("purchaseOrderRequests").get("countryOfOrigin").asText();
                String leadTime = jsonNode.get("purchaseOrderRequests").get("leadTime").asText();
                String shippingPoint = jsonNode.get("purchaseOrderRequests").get("shippingPoint").asText();

                Locator additionalItemNameDropDownLocator = page.locator(ITEM_NAME_DROPDOWN);
                locatorVisibleHandler(additionalItemNameDropDownLocator);
                additionalItemNameDropDownLocator.click();

                Locator additionalItemNameSearchLocator = page.locator(ITEM_NAME_SEARCH);
                locatorVisibleHandler(additionalItemNameSearchLocator);
                additionalItemNameSearchLocator.fill(itemName);

                String additionalItemNameSelect = getItemName(itemName);
                Locator additionalItemNameSelectLocator = page.locator(additionalItemNameSelect);
                locatorVisibleHandler(additionalItemNameSelectLocator);
                additionalItemNameSelectLocator.click();

                Locator rateLocator = page.locator(RATE);
                locatorVisibleHandler(rateLocator);
                rateLocator.fill(rate);

                Locator hsCodeLocator = page.locator(HS_CODE);
                locatorVisibleHandler(hsCodeLocator);
                hsCodeLocator.fill(hsCode);

                Locator makeLocator = page.locator(MAKE);
                locatorVisibleHandler(makeLocator);
                makeLocator.fill(make);

                Locator modelLocator = page.locator(MODEL);
                locatorVisibleHandler(modelLocator);
                modelLocator.fill(model);

                Locator partNumberLocator = page.locator(PART_NUMBER);
                locatorVisibleHandler(partNumberLocator);
                partNumberLocator.fill(partNumber);

                Locator countryOfOriginLocator = page.locator(COUNTRY_OF_ORIGIN);
                locatorVisibleHandler(countryOfOriginLocator);
                countryOfOriginLocator.fill(countryOfOrigin);

                Locator leadTimeLocator = page.locator(LEAD_TIME);
                locatorVisibleHandler(leadTimeLocator);
                leadTimeLocator.fill(leadTime);

                Locator shippingPointDropDownLocator = page.locator(SHIPPING_POINT_DROPDOWN);
                locatorVisibleHandler(shippingPointDropDownLocator);
                shippingPointDropDownLocator.click();

                Locator shippingPointSearchLocator = page.locator(SHIPPING_POINT_SEARCH);
                locatorVisibleHandler(shippingPointSearchLocator);
                shippingPointSearchLocator.fill(shippingPoint);

                String shippingPointSelect = getShippingPoint(shippingPoint);
                Locator shippingPointSelectSelectLocator = page.locator(shippingPointSelect);
                locatorVisibleHandler(shippingPointSelectSelectLocator);
                shippingPointSelectSelectLocator.click();

                Locator submitButtonLocator1 = page.locator(SUBMIT_BUTTON);
                locatorVisibleHandler(submitButtonLocator1);
                submitButtonLocator1.click();

                Locator updateButtonLocator = page.locator(UPDATE_BUTTON);
                locatorVisibleHandler(updateButtonLocator);
                updateButtonLocator.click();

                Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
                locatorVisibleHandler(acceptButtonLocator);

                Response porRevisionResponse = page.waitForResponse(
                        response -> response.url().startsWith(appUrl + porType) && response.status() == 200,
                        acceptButtonLocator::click
                );
                status = porRevisionResponse.status();

                PlaywrightFactory.attachScreenshotWithName("Purchase Order Request Revision", page);

                iLogout.performLogout();

                iPorApprove.approve(type, purchaseType);
            }
        } catch (Exception exception) {
            logger.error("Exception in POR Revision function: {}", exception.getMessage());
        }
        return status;
    }
}