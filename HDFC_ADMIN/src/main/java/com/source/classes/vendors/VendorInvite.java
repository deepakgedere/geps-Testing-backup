package com.source.classes.vendors;

import com.enums.vendors.LVendorInvite;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.vendors.IVendorInvite;
import com.utils.VendorDetails;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.nio.channels.Selector;

public class VendorInvite implements IVendorInvite {

    protected Logger logger = LoggerUtil.getLogger(VendorInvite.class);
    protected Page page;
    protected JsonNode jsonNode;
    protected ILogin iLogin;
    protected ILogout iLogout;

    private VendorInvite() {
    }

    //TODO Constructor
    public VendorInvite(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
    }

    public void clusterHeadLogin() {
        String email = jsonNode.get("mailIds").get("clusterHead").asText();
        iLogin.performLogin(email);
    }

    public void clickVendorsModule() {
        page.waitForSelector(LVendorInvite.VENDORS_MODULE.getSelector()).click();
        page.waitForTimeout(2000);
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.waitForSelector("//button[@aria-label='Button for details']", new Page.WaitForSelectorOptions()
                .setTimeout(30000));
    }

    public void clickFilterByStatus() {
        page.waitForSelector(LVendorInvite.FILTER_BY_STATUS.getSelector()).click();
    }

    public void clickOracleVendor() {
        page.waitForSelector(LVendorInvite.ORACLE_VENDOR.getSelector()).click();
        page.waitForTimeout(1000); // Wait for 1 second to ensure the filter is applied
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.waitForSelector(LVendorInvite.FILTER_ORACLE_VENDOR_VALIDATION.getSelector());

        Locator vendorTypeCells = page.locator(LVendorInvite.ORACLE_VENDOR_LIST_VENDOR_TYPE.getSelector());
        int count = vendorTypeCells.count();

        for (int i = 0; i < count; i++) {
            String text = vendorTypeCells.nth(i).innerText().trim();
            PlaywrightAssertions.assertThat(vendorTypeCells.nth(i)).hasText("NA");
        }
    }

    public VendorDetails searchAndClickVendor(){
        String vendorName = jsonNode.get("vendors").get("vendorName").asText();
        page.waitForSelector("//span[contains(text(),'Show All Vendors')]").click();
        page.waitForSelector(LVendorInvite.VENDOR_SEARCH_BAR.getSelector()).fill(vendorName);
        page.waitForSelector(LVendorInvite.VENDOR_SEARCH_BUTTON.getSelector()).click();
        String x = "(//tr[.//td[@data-label='Name' and contains(text(),'" + vendorName + "')]])[1]";
        page.waitForSelector(x, new Page.WaitForSelectorOptions()
                .setTimeout(60000));
        page.waitForSelector(x+"//button[@mattooltip='View Details']").click();
        String vendorNameDetails = page.locator(LVendorInvite.VENDOR_NAME.getSelector()).innerText().trim();
        String vendorEmail = page.locator(LVendorInvite.VENDOR_EMAIL.getSelector()).innerText().trim();
        return new VendorDetails(vendorNameDetails, vendorEmail);
    }

    public VendorDetails clickDetailsButton() {
        page.waitForSelector(LVendorInvite.FIRST_BUTTON_FOR_DETAILS.getSelector()).click();
        page.waitForSelector(LVendorInvite.VENDOR_NAME.getSelector());
        String vendorName = page.locator(LVendorInvite.VENDOR_NAME.getSelector()).innerText().trim();
        String vendorEmail = page.locator(LVendorInvite.VENDOR_EMAIL.getSelector()).innerText().trim();
        return new VendorDetails(vendorName, vendorEmail);
    }

    public void inviteVendor() {
        page.waitForSelector(LVendorInvite.INVITE_VENDOR.getSelector()).click();
        String originalVendorName = jsonNode.get("vendors").get("vendorName").asText();
        String originalVendorEmail = jsonNode.get("vendors").get("vendorEmail").asText();
        page.waitForSelector(LVendorInvite.VENDOR_NAME_INPUT.getSelector(), new Page.WaitForSelectorOptions().setTimeout(30000));
        PlaywrightAssertions.assertThat(page.locator(LVendorInvite.VENDOR_NAME_INPUT.getSelector())).hasValue(originalVendorName);
        PlaywrightAssertions.assertThat(page.locator(LVendorInvite.VENDOR_EMAIL_INPUT.getSelector())).hasValue(originalVendorEmail);
    }

    public void selectVendorDocumentType() {
        String vendorDocumentType = jsonNode.get("vendors").get("vendorDocumentType").asText();

        page.getByLabel(LVendorInvite.VENDOR_DOCUMENT_TYPE.getSelector()).click();
        page.waitForSelector(LVendorInvite.getVendorDocumentType(vendorDocumentType)).click();
    }

    public void selectSkillTechnicalExpertise() {
        JsonNode skills = jsonNode.get("vendors").get("skillsOrTechnicalExpertise");

        page.waitForSelector(LVendorInvite.SKILL_TECHNICAL_EXPERTISE.getSelector()).click();

        for (int i = 0; i < skills.size(); i++) {
            String skill = skills.get(i).asText();
            Locator option = page.locator(LVendorInvite.getSkillTechnicalExpertise(skill));
            if (option.isVisible() || option.isEnabled()) {
                option.click();
            } else {
                logger.warn("Skill option '{}' is not visible or enabled, skipping selection.", skill);
            }
            if (i == skills.size() - 1) {
                page.locator(LVendorInvite.CLICK.getSelector()).click();
            }
        }
    }

    public void selectRegion() {
        String region = jsonNode.get("vendors").get("region").asText();

        page.getByLabel(LVendorInvite.SELECT_REGIONS.getSelector()).click();
        page.waitForSelector(LVendorInvite.getRegion(region)).click();

        page.mouse().click(1, 1);
    }

    public void fillFirstProjectName() {
        String firstProjectName = jsonNode.get("vendors").get("firstProjectName").asText();

        page.getByLabel(LVendorInvite.FIRST_PROJECT_NAME.getSelector()).fill(firstProjectName);
    }

    public void referredBy() {
        String referredBy = jsonNode.get("vendors").get("referredBy").asText();

        page.waitForSelector(LVendorInvite.REFERRED_BY.getSelector()).fill(referredBy);
        page.waitForSelector(LVendorInvite.getReferredBy(referredBy)).click();

        PlaywrightAssertions.assertThat(page.locator(LVendorInvite.REFERRED_NAME.getSelector())).hasValue(referredBy);
    }

    public void vendorType() {
        boolean oneTimeVendor = jsonNode.get("vendors").get("oneTimeVendor").asBoolean();

        if (!oneTimeVendor) {
            page.waitForSelector(LVendorInvite.VENDOR_TYPE_REGULAR.getSelector()).click();
        } else {
            page.waitForSelector(LVendorInvite.VENDOR_TYPE_ONE_TIME.getSelector()).click();

            page.waitForSelector(LVendorInvite.START_DATE_LOCATOR.getSelector()).click();
            page.waitForSelector(LVendorInvite.CURRENT_DATE.getSelector()).click();

            page.waitForSelector(LVendorInvite.END_DATE_LOCATOR.getSelector()).click();
            page.waitForSelector(LVendorInvite.NEXT_MONTH.getSelector()).click();
            page.waitForSelector(LVendorInvite.NEXT_MONTH_27.getSelector()).click();
//            page.waitForSelector(LVendorInvite.DECEMBER_MONTH.getSelector()).click();
//            page.waitForSelector(LVendorInvite.DATE_1.getSelector()).click();
        }
    }

    public void clickSubmit() {
        try {
            page.waitForSelector(LVendorInvite.SUBMIT.getSelector()).click();
            page.waitForSelector(LVendorInvite.YES.getSelector()).click();
        } catch (Exception exception) {
            logger.error("Exception in Click Submit Function: {}", exception.getMessage());
        }
    }

    public void clusterHeadLogout() {
        String vendorName = jsonNode.get("vendors").get("vendorName").asText();
        page.waitForSelector(LVendorInvite.getStatusLocatorOfVendor(vendorName), new Page.WaitForSelectorOptions().setTimeout(60000));
        PlaywrightAssertions.assertThat(page.locator(LVendorInvite.getStatusLocatorOfVendor(vendorName))).containsText("Invited");
        iLogout.performLogout();
    }

}