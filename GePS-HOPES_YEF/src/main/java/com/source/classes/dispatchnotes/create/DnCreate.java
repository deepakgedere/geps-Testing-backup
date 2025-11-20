package com.source.classes.dispatchnotes.create;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.dispatchnotes.IDnCreate;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.util.Properties;
import static com.constants.dispatchnotes.LDnCreate.*;
import static com.constants.orderschedules.LOsEdit.getTitle;
import static com.utils.GetTitleUtil.getTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;
import static com.utils.SaveToTestDataJsonUtil.saveReferenceIdFromResponse;

public class DnCreate implements IDnCreate {

    Logger logger;
    JsonNode jsonNode;
    Properties properties;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    String appUrl;

    private DnCreate() {
    }

//TODO Constructor
    public DnCreate(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.iLogin = iLogin;
        this.logger = LoggerUtil.getLogger(DnCreate.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int create(String type, String purchaseType) {
        int status =0;
        try {
            String vendorMailId;
            if (purchaseType.equalsIgnoreCase("punchout")) {
                vendorMailId = "punchoutVendorEmail";
            } else {
                vendorMailId = jsonNode.get("mailIds").get("vendorEmail").asText();
            }
            iLogin.performLogin(vendorMailId);

            page.waitForLoadState(LoadState.NETWORKIDLE);

            Locator vendorSelection = page.locator("#vendor-container");
            if(vendorSelection.isVisible()){
                if(purchaseType.equalsIgnoreCase("punchout"))
                    page.locator("//tr[.//td[contains(text(),'" + jsonNode.get("requisition").get("punchoutVendorName").asText() + "')]]//input[@type='radio']").click();
                else
                    page.locator("//tr[.//td[contains(text(),'" + jsonNode.get("requisition").get("vendorName").asText() + "')]]//input[@type='radio']").click();
                page.locator("#glbBtnChangeVendor").click();
            }
            Locator osNavigationBarLocator = page.locator(OS_NAVIGATION_BAR);
            locatorVisibleHandler(osNavigationBarLocator);
            osNavigationBarLocator.click();

            String osRefId = jsonNode.get("orderSchedules").get("orderScheduleReferenceId").asText();
            Locator osTitle = page.locator(getTitle(osRefId));
            locatorVisibleHandler(osTitle);
            osTitle.click();

            Locator dnCreateButtonLocator = page.locator(DN_CREATE_BUTTON);
            locatorVisibleHandler(dnCreateButtonLocator);
            dnCreateButtonLocator.click();

            Locator date = page.locator(DN_DATE_SELECTION);
            locatorVisibleHandler(date);
            date.click();

            Locator today = page.locator(TODAY);
            locatorVisibleHandler(today);
            today.click();

            Locator collectionAddress = page.locator(COLLECTION_ADDRESS);
            locatorVisibleHandler(collectionAddress);
            collectionAddress.fill(jsonNode.get("dispatchNotes").get("collectionAddress").asText());

            Locator consignor = page.locator(CONSIGNOR);
            locatorVisibleHandler(consignor);
            consignor.fill(jsonNode.get("dispatchNotes").get("consignor").asText());

            Locator addDnPackagesButtonLocator = page.locator(ADD_DISPATCH_NOTE_PACKAGES_BUTTON);
            locatorVisibleHandler(addDnPackagesButtonLocator);
            addDnPackagesButtonLocator.click();

            Locator packageTypeLocator = page.locator(PACKAGE_TYPE);
            locatorVisibleHandler(packageTypeLocator);
            packageTypeLocator.click();

            String packageType = jsonNode.get("dispatchNotes").get("packageType").asText();
            Locator searchFieldLocator = page.locator(SEARCH_FIELD);
            locatorVisibleHandler(searchFieldLocator);
            searchFieldLocator.fill(packageType);

            Locator getPackageLocator = page.locator(getPackageType(packageType));
            locatorVisibleHandler(getPackageLocator);
            getPackageLocator.click();

            String grossWeight = jsonNode.get("dispatchNotes").get("grossWeightKg").asText();
            Locator grossWeightLocator = page.locator(GROSS_WEIGHT);
            locatorVisibleHandler(grossWeightLocator);
            grossWeightLocator.fill(grossWeight);

            String netWeight = jsonNode.get("dispatchNotes").get("netWeightKg").asText();
            Locator netWeightLocator = page.locator(NET_WEIGHT);
            locatorVisibleHandler(netWeightLocator);
            netWeightLocator.fill(netWeight);

            String volume = jsonNode.get("dispatchNotes").get("volumeCm3").asText();
            Locator volumeLocator = page.locator(VOLUME);
            locatorVisibleHandler(volumeLocator);
            volumeLocator.fill(volume);

            String quantity = jsonNode.get("dispatchNotes").get("totalChargeableWeightKg").asText();
            Locator quantityLocator = page.locator(QUANTITY);
            locatorVisibleHandler(quantityLocator);
            quantityLocator.fill(quantity);

            Locator saveDnPackagesButtonLocator = page.locator(SAVE_DISPATCH_NOTE_PACKAGES_BUTTON);
            locatorVisibleHandler(saveDnPackagesButtonLocator);
            saveDnPackagesButtonLocator.click();

            Locator createButtonLocator = page.locator(CREATE_BUTTON);
            locatorVisibleHandler(createButtonLocator);
            createButtonLocator.click();

            Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptButtonLocator);

            Response dnListResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + "/api/VP/DispatchNotes/Listing") && response.status() == 200,
                    acceptButtonLocator.first()::click
            );

            String poReferenceId = jsonNode.get("purchaseOrders").get("poReferenceId").asText();
            //TODO Locate the row containing the dynamic poReferenceId and click the <a> tag
            Locator rows = page.locator(ROWS);
            int rowCount = rows.count();
            for (int i = 0; i < rowCount; i++) {
                Locator row = rows.nth(i);
                String referenceText = row.locator(THIRD_CHILD_ELEMENT).innerText();
                if (referenceText.contains(poReferenceId)) {
                    Response dnResponse = page.waitForResponse(
                            response -> response.url().startsWith(appUrl + "/api/VP/DispatchNotes/") && response.status() == 200,
                            row.locator("a").first()::click
                    );
                    page.waitForLoadState(LoadState.NETWORKIDLE);
                    saveReferenceIdFromResponse(dnResponse, "dispatchNotes", "dispatchNoteReferenceId");
                    status = dnResponse.status();
                    break;
                }
            }

            PlaywrightFactory.attachScreenshotWithName("Dispatch Notes Create", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Dispatch Notes Create function: {}", exception.getMessage());
        }
        return status;
    }
}