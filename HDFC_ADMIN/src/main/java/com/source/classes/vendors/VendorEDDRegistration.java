package com.source.classes.vendors;

import com.enums.vendors.LVendorEDDRegistration;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.vendors.IVendorEDDRegistration;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class VendorEDDRegistration implements IVendorEDDRegistration {

    protected Logger logger = LoggerUtil.getLogger(VendorEDDRegistration.class);
    protected Page page;
    protected JsonNode jsonNode;
    protected ILogin iLogin;
    protected ILogout iLogout;
    protected String link;
    protected List<String> approvers = new ArrayList<>();

    private VendorEDDRegistration() {
    }

    //TODO Constructor
    public VendorEDDRegistration(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
    }

    public void superAdminLogin() {
        String email = jsonNode.get("mailIds").get("superAdmin").asText();
        iLogin.performLogin(email);
    }

    public void clickVendorsModule() {
        page.waitForSelector(LVendorEDDRegistration.VENDORS_MODULE.getSelector()).click();
    }

    public void navigateToVendorDetails() {
        clickVendorsModule();
        String vendorName = jsonNode.get("vendors").get("vendorName").asText();
        searchListPage(vendorName);

        page.waitForSelector(LVendorEDDRegistration.detailsButtonForVendorName(vendorName)).click();
    }

    public void clicksInviteVendorEmailButton() {
        String vendorName = jsonNode.get("vendors").get("vendorName").asText();

        searchListPage(vendorName);

        page.waitForSelector(LVendorEDDRegistration.notifyButtonForVendorName(vendorName)).click();
        link = page.waitForSelector(LVendorEDDRegistration.EMAIL_LINK.getSelector()).textContent();
        link = link.replace("\"", "");
        page.waitForSelector(LVendorEDDRegistration.SUBMIT_BUTTON.getSelector()).click();
        page.waitForSelector(LVendorEDDRegistration.EMAIL_SENT_VALIDATION.getSelector());
    }

    public void vendorSubmitsEDDRegistrationForm() {
        page.navigate(link);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("EMD Deposit")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("EMD Deposit")).fill("NA");

        List<Locator> rows = page.locator(LVendorEDDRegistration.ROW_COUNT.getSelector()).all();
        int rowCount = rows.size();

        for (int i = 1; i <= rowCount; i++) {
            Locator startDate = page.locator(LVendorEDDRegistration.getStartDate(i));
            Locator endDate = page.locator(LVendorEDDRegistration.getEndDate(i));
            Locator today = page.locator(LVendorEDDRegistration.TODAY.getSelector());
            page.locator(LVendorEDDRegistration.getAttachFileRowCount(i)).setInputFiles(Paths.get("documents/Vendor_EDD_Documents.xls"));

            if (startDate.isVisible() && endDate.isVisible()) {
                // Scroll the start date element into view before clicking
//                startDate.scrollIntoViewIfNeeded();
                startDate.click();

                today.scrollIntoViewIfNeeded();
                today.click();

                // Scroll the end date element into view before clicking
//                endDate.scrollIntoViewIfNeeded();
                endDate.click();

                today.scrollIntoViewIfNeeded();
                today.click();
            }
            page.waitForSelector(LVendorEDDRegistration.getRemarks(i)).fill("Remarks - " + i);
        }

        page.waitForSelector(LVendorEDDRegistration.SUBMIT.getSelector()).click();
        page.waitForSelector(LVendorEDDRegistration.YES.getSelector()).click();


    }

    public void verifyEDDRegistrationFormSubmission() {
        page.waitForSelector(LVendorEDDRegistration.SUCCESS_MESSAGE.getSelector());
    }

    public void searchListPage(String vendorName) {
        page.waitForSelector("//input").fill(vendorName);
        page.waitForSelector("//input").press("Enter");
        page.waitForTimeout(1000);
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

}