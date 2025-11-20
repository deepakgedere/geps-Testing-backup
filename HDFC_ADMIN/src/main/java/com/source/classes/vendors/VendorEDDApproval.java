package com.source.classes.vendors;

import com.enums.vendors.LVendorEDDRegistration;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.vendors.IVendorEDDApproval;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class VendorEDDApproval implements IVendorEDDApproval {

    protected Logger logger = LoggerUtil.getLogger(VendorEDDRegistration.class);
    protected Page page;
    protected JsonNode jsonNode;
    protected ILogin iLogin;
    protected ILogout iLogout;
    protected List<String> approvers = new ArrayList<>();
    protected VendorEDDRegistration vendorEDDRegistration;

    private VendorEDDApproval() {
    }

    //TODO Constructor
    public VendorEDDApproval(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.vendorEDDRegistration = new VendorEDDRegistration(jsonNode, page, iLogin, iLogout);
    }

    public List<String> clusterHeadAssignApprovers() {
        String stateHead = jsonNode.get("mailIds").get("stateHead").asText();
        String regionHead = jsonNode.get("mailIds").get("regionalHead").asText();

        page.getByRole(AriaRole.ROW, new Page.GetByRoleOptions().setName("Checker Select Approvers")).getByRole(AriaRole.BUTTON).click();
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Number")).fill(stateHead);
        page.getByText(stateHead, new Page.GetByTextOptions().setExact(true)).click();
        approvers.add(stateHead);

        page.getByRole(AriaRole.ROW, new Page.GetByRoleOptions().setName("Approver Select Approvers")).getByRole(AriaRole.BUTTON).click();
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Number")).fill(regionHead);
        page.getByText(regionHead, new Page.GetByTextOptions().setExact(true)).click();
        approvers.add(regionHead);

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Apply Approvers")).click();

        page.waitForSelector(LVendorEDDRegistration.APPROVAL_PENDING_STATUS.getSelector()).scrollIntoViewIfNeeded();

        iLogout.performLogout();
        return approvers;
    }

    public String savePendingApprover() {
        page.waitForSelector("(//tr[.//span[contains(text(),'Pending')]]//span[contains(@class,'mail')])[1]").hover();
        String pendingApproverEmail = page.waitForSelector("(//tr[.//span[contains(text(),'Pending')]]//span[contains(@class,'mail')])[1]//span").textContent();
        return pendingApproverEmail;
    }

    public void eddFullApprove(String approver){
        iLogin.performLogin(approver);

        page.waitForSelector("//h4[contains(text(),'Vendor Due Diligence and Empanelment')]").click();
        String vendorName = jsonNode.get("vendors").get("vendorName").asText();
        page.waitForSelector(LVendorEDDRegistration.myAppprovalsDetailsButtonForVendorName(vendorName)).click();
        page.waitForSelector(LVendorEDDRegistration.APPROVE_BUTTON.getSelector(), new Page.WaitForSelectorOptions().setTimeout(60000)).scrollIntoViewIfNeeded();

        String nextApproverEmail = "";
        try {
            page.waitForSelector("(//tr[.//span[contains(text(),'Waiting')]]//span[contains(@class,'mail')])[1]", new Page.WaitForSelectorOptions().setTimeout(3000)).hover();
            nextApproverEmail = page.waitForSelector("(//tr[.//span[contains(text(),'Waiting')]]//span[contains(@class,'mail')])[1]//span").textContent();
        }
        catch(Exception e){
        }
        page.waitForSelector(LVendorEDDRegistration.APPROVE_BUTTON.getSelector(), new Page.WaitForSelectorOptions().setTimeout(60000)).scrollIntoViewIfNeeded();
        page.waitForSelector(LVendorEDDRegistration.APPROVE_BUTTON.getSelector()).click();
        page.waitForSelector("//textarea").fill("Approved by " + approver.split("@")[0]);

        page.waitForSelector(LVendorEDDRegistration.SUBMIT_BUTTON_FOR_REMARKS.getSelector()).scrollIntoViewIfNeeded();
        page.waitForSelector(LVendorEDDRegistration.SUBMIT_BUTTON_FOR_REMARKS.getSelector()).click();

        page.waitForSelector("//h4[contains(text(),'My Approvals')]");
        page.waitForTimeout(2000);

        iLogout.performLogout();

        if(!nextApproverEmail.isEmpty()) {
            eddFullApprove(nextApproverEmail);
        }
    }

    public void approversApproveEDD(List<String> approvers) {
        for (String approver : approvers) {
            iLogin.performLogin(approver);

            page.waitForSelector("//h4[contains(text(),'Vendor Due Diligence and Empanelment')]").click();
            String vendorName = jsonNode.get("vendors").get("vendorName").asText();
            page.waitForSelector(LVendorEDDRegistration.myAppprovalsDetailsButtonForVendorName(vendorName)).click();
            page.waitForSelector(LVendorEDDRegistration.APPROVE_BUTTON.getSelector()).scrollIntoViewIfNeeded();
            page.waitForSelector(LVendorEDDRegistration.APPROVE_BUTTON.getSelector()).click();

            page.waitForSelector("//textarea").fill("Approved by " + approver.split("@")[0]);

            page.waitForSelector(LVendorEDDRegistration.SUBMIT_BUTTON_FOR_REMARKS.getSelector()).scrollIntoViewIfNeeded();
            page.waitForSelector(LVendorEDDRegistration.SUBMIT_BUTTON_FOR_REMARKS.getSelector()).click();

            page.waitForTimeout(2000);
            page.waitForLoadState(LoadState.NETWORKIDLE);

            page.waitForSelector("//h4[text()=' My Approvals ']");

            iLogout.performLogout();
        }
    }

    public void verifyEddIsApproved() {
        vendorEDDRegistration.superAdminLogin();
        vendorEDDRegistration.clickVendorsModule();
        String vendorName = jsonNode.get("vendors").get("vendorName").asText();
        searchListPage(vendorName);

        page.waitForSelector("//tr[./td[contains(text(),'"+vendorName+"')]]//td[@data-label='Status']").scrollIntoViewIfNeeded();
        PlaywrightAssertions.assertThat(page.locator("//tr[./td[contains(text(),'"+vendorName+"')]]//td[@data-label='Status']//span")).containsText("EDDApproved", new LocatorAssertions.ContainsTextOptions().setIgnoreCase(true));

        iLogout.performLogout();
    }

    public void searchListPage(String vendorName) {
        page.waitForSelector("//input").fill(vendorName);
        page.waitForSelector("//input").press("Enter");
        page.waitForTimeout(1000);
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }
}
