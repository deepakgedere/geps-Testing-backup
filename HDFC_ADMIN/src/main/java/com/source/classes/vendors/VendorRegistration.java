package com.source.classes.vendors;

import com.enums.vendors.LVendorEDDRegistration;
import com.enums.vendors.LVendorRegistration;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.vendors.IVendorRegistration;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class VendorRegistration implements IVendorRegistration {

    protected Logger logger = LoggerUtil.getLogger(LVendorRegistration.class);
    protected Page page;
    protected JsonNode jsonNode;
    protected ILogin iLogin;
    protected ILogout iLogout;
    protected String link;
    protected List<String> approvers;

    private VendorRegistration() {
    }

    //TODO Constructor
    public VendorRegistration(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
    }

    public void superAdminLogin() {
        try {
            String email = jsonNode.get("mailIds").get("superAdmin").asText();
            iLogin.performLogin(email);
        } catch (Exception exception) {
            logger.error("Exception in Super Admin Login Function: {}", exception.getMessage());
        }
    }

    public void clickVendorsModule() {
        try {
            page.waitForSelector(LVendorRegistration.VENDORS_MODULE.getSelector()).click();
            page.waitForSelector("//h4[text()='Vendors']").isVisible();
        } catch (Exception exception) {
            logger.error("Exception in Click Vendors Module Function: {}", exception.getMessage());
        }
    }

    public void clicksInviteVendorEmailButton() {
        try {
            String vendorName = jsonNode.get("vendors").get("vendorName").asText();

            searchListPage(vendorName);

            page.waitForSelector(LVendorRegistration.notifyButtonForVendorName(vendorName)).click();
            link = page.waitForSelector(LVendorEDDRegistration.EMAIL_LINK.getSelector()).textContent();
            link = link.split("LINK:")[1];
            link = link.replace("\"", "");
            page.waitForSelector(LVendorEDDRegistration.SUBMIT_BUTTON.getSelector()).click();
            page.waitForSelector(LVendorEDDRegistration.EMAIL_SENT_VALIDATION.getSelector());
        } catch (Exception exception) {
            logger.error("Exception in Click Invite Vendor Email Button Function: {}", exception.getMessage());
        }
    }

    public void vendorDetails() {
        try {
            String initial = jsonNode.get("vendorRegistration").get("vendorDetails").get("initial").asText();
            String contactPerson = jsonNode.get("vendorRegistration").get("vendorDetails").get("contactPerson").asText();
            String landlineNimber = jsonNode.get("vendorRegistration").get("vendorDetails").get("landlineNumber").asText();
            String natureOfTransaction = jsonNode.get("vendorRegistration").get("vendorDetails").get("natureOfTransaction").asText();
            String oem = jsonNode.get("vendorRegistration").get("vendorDetails").get("oem").asText();
            String productAndService = jsonNode.get("vendorRegistration").get("vendorDetails").get("productAndService").asText();

            page.navigate(link);

            page.waitForSelector(LVendorRegistration.SELECT_INITIAL.getSelector()).click();
            page.waitForSelector(LVendorRegistration.getInitial(initial)).click();
            page.waitForSelector(LVendorRegistration.CONTACT_PERSON.getSelector()).fill(contactPerson);
            page.waitForSelector(LVendorRegistration.LANDLINE_NUMBER.getSelector()).fill(landlineNimber);
            page.waitForSelector(LVendorRegistration.NATURE_OF_TRANSACTION.getSelector()).click();
            page.waitForSelector(LVendorRegistration.getNatureOfTransaction(natureOfTransaction)).click();
            page.waitForSelector(LVendorRegistration.OEM.getSelector()).fill(oem);
            page.waitForSelector(LVendorRegistration.PRODUCT_OR_SERVICE.getSelector()).fill(productAndService);
        } catch (Exception exception) {
            logger.error("Exception in Vendor Details Function: {}", exception.getMessage());
        }
    }

    public void vendorDetailsRadioButton() {
        try {
            boolean anyRelativeWorking = jsonNode.get("vendorRegistration").get("vendorDetailsRadioButton").get("anyOfYourRelativeWorkingInHDFCBank?").asBoolean();
            String employeeName = jsonNode.get("vendorRegistration").get("vendorDetailsRadioButton").get("employeeName").asText();
            String employeePhoneNumber = jsonNode.get("vendorRegistration").get("vendorDetailsRadioButton").get("employeePhoneNumber").asText();
            String employeeCode = jsonNode.get("vendorRegistration").get("vendorDetailsRadioButton").get("employeeCode").asText();
            String employeeEmail = jsonNode.get("vendorRegistration").get("vendorDetailsRadioButton").get("employeeEmail").asText();
            String relationWithEmployee = jsonNode.get("vendorRegistration").get("vendorDetailsRadioButton").get("relationWithEmployee").asText();

            if (anyRelativeWorking) {
                page.waitForSelector(LVendorRegistration.ANY_OF_YOUR_RELATIVE_WORKING_IN_HDFC_BANK_YES.getSelector()).click();
                page.waitForSelector(LVendorRegistration.EMPLOYEE_NAME.getSelector()).fill(employeeName);
                page.waitForSelector(LVendorRegistration.EMPLOYEE_PHONE_NUMBER.getSelector()).fill(employeePhoneNumber);
                page.locator(LVendorRegistration.VENDOR_DETAILS_UPLOAD_ATTACHMENT.getSelector()).setInputFiles(Paths.get("documents/Vendor Details Upload Attachment.pdf"));
                page.waitForSelector(LVendorRegistration.EMPLOYEE_CODE_OR_ID.getSelector()).fill(employeeCode);
                page.waitForSelector(LVendorRegistration.EMPLOYEE_EMAIL.getSelector()).fill(employeeEmail);
                page.waitForSelector(LVendorRegistration.RELATION_WITH_EMPLOYEE.getSelector()).fill(relationWithEmployee);
                page.waitForSelector(LVendorRegistration.ADD_RELATIVE_BUTTON.getSelector()).click();

            }
        } catch (Exception exception) {
            logger.error("Exception in Vendor Details Radio Button Function: {}", exception.getMessage());
        }
    }

    public void vendorLocationDetails() {
        try {
            Locator rows = page.locator("//table//tr[./td[contains(@class,'cdk-column-TaxCode')]]");
            int count = rows.count();
            for (int i = 1; i <= count; i++) {
                String x = page.locator("(//table//tr//td[contains(@class,'cdk-column-TaxCode')])["+i+"]").textContent();
                if(!x.equalsIgnoreCase("")) {
                    Locator row = rows.nth(i-1);
                    row.locator("//button[@mattooltip='edit Location']").click();
                    page.locator(LVendorRegistration.ATTACH_GST_CERTIFICATE.getSelector()).setInputFiles(Paths.get("documents/Vendor_GST_Documents.pdf"));
                    page.waitForSelector(LVendorRegistration.GST_OPEN_CALENDAR.getSelector()).click();

                    int today = Integer.parseInt(page.locator(LVendorRegistration.TODAY_DATE.getSelector()).textContent().trim());
                    int yesterday = today - 1;
                    page.waitForSelector(LVendorRegistration.getYesterdayDate(yesterday)).scrollIntoViewIfNeeded();
                    page.waitForSelector(LVendorRegistration.getYesterdayDate(yesterday)).click();

                    page.waitForSelector(LVendorRegistration.ADD_LOCATIONS_BUTTON.getSelector()).click();
                }
            }
        } catch (Exception exception) {
            logger.error("Exception in Vendor Location Details Function: {}", exception.getMessage());
        }
    }

    public void vendorBankDetails() {
        try {
            String accountType = jsonNode.get("vendorRegistration").get("vendorBankDetails").get("accountType").asText();
            boolean isHDFCBankAccount = jsonNode.get("vendorRegistration").get("vendorBankDetails").get("isHDFCBankAccount").asBoolean();
            String bankName = jsonNode.get("vendorRegistration").get("vendorBankDetails").get("bankName").asText();
            String ifscCode = jsonNode.get("vendorRegistration").get("vendorBankDetails").get("ifscCode").asText();
            String reasonForNotHavingHDFCBankAccount = jsonNode.get("vendorRegistration").get("vendorBankDetails").get("reasonForNotHavingHDFCBankAccount").asText();

            if (isHDFCBankAccount) {
                page.waitForSelector(LVendorRegistration.HDFC_BANK_ACCOUNT_TYPE_RADIO.getSelector()).click();
                String accountNumber = page.waitForSelector(LVendorRegistration.HDFC_BANK_ACCOUNT_NUMBER.getSelector()).inputValue().trim();
                page.waitForSelector(LVendorRegistration.HDFC_BANK_CONFIRM_ACCOUNT_NUMBER.getSelector()).fill(accountNumber);
                page.waitForSelector(LVendorRegistration.HDFC_BANK_ACCOUNT_TYPE.getSelector()).click();
                page.waitForSelector(LVendorRegistration.getAccountType(accountType)).click();
                page.locator(LVendorRegistration.HDFC_BANK_ATTACHMENT.getSelector()).setInputFiles(Paths.get("documents/Upload Finware-UBS Account Credit Authority Letter .pdf"));
            } else {
                page.waitForSelector(LVendorRegistration.OTHERS_BANK_NAME.getSelector()).fill(bankName);
                page.waitForSelector(LVendorRegistration.OTHERS_IFSC_CODE.getSelector()).fill(ifscCode);
                String accountNumber = page.waitForSelector(LVendorRegistration.HDFC_BANK_ACCOUNT_NUMBER.getSelector()).inputValue().trim();
                page.waitForSelector(LVendorRegistration.HDFC_BANK_CONFIRM_ACCOUNT_NUMBER.getSelector()).fill(accountNumber);
                page.waitForSelector(LVendorRegistration.HDFC_BANK_ACCOUNT_TYPE.getSelector()).click();
                page.waitForSelector(LVendorRegistration.getAccountType(accountType)).click();
                page.waitForSelector(LVendorRegistration.REASON.getSelector()).fill(reasonForNotHavingHDFCBankAccount);
                page.locator(LVendorRegistration.OTHERS_BANK_NEFT_ATTACHMENT.getSelector()).setInputFiles(Paths.get("documents/Upload NEFT_Format .pdf"));
                page.locator(LVendorRegistration.OTHERS_BANK_CANCEL_CHEQUE_ATTACHMENT.getSelector()).setInputFiles(Paths.get("documents/Upload Cancelled Cheque .pdf"));
            }
        } catch (Exception exception) {
            logger.error("Exception in Vendor Bank Details Function: {}", exception.getMessage());
        }
    }

    public void taxDetails() {
        try {
//            String panHolderName = jsonNode.get("vendors").get("vendorName").asText();
            String panHolderName = "PanHolderName";

            String companyType = jsonNode.get("vendorRegistration").get("taxDetails").get("companyType").asText();
            boolean areYouACompositeTaxableDealer = jsonNode.get("vendorRegistration").get("taxDetails").get("areYouACompositeTaxableDealer").asBoolean();
            boolean confirmRegistrationUnderMSMEACT2006 = jsonNode.get("vendorRegistration").get("taxDetails").get("confirmRegistrationUnderMSMEACT2006").asBoolean();
            String enterpriseUnderMSME = jsonNode.get("vendorRegistration").get("taxDetails").get("enterpriseUnderMSME").asText();
            String msmeRegistrationNumber = jsonNode.get("vendorRegistration").get("taxDetails").get("msmeRegistrationNumber").asText();

            page.waitForSelector(LVendorRegistration.PAN_HOLDER_NAME.getSelector()).fill(panHolderName);
            page.waitForSelector(LVendorRegistration.COMPANY_TYPE.getSelector()).scrollIntoViewIfNeeded();
            page.waitForSelector(LVendorRegistration.COMPANY_TYPE.getSelector()).click();
            page.waitForSelector(LVendorRegistration.getCompanyType(companyType)).click();
            page.locator(LVendorRegistration.ATTACH_PAN_CARD_DOCUMENT.getSelector()).setInputFiles(Paths.get("documents/Attach PAN Card Document .pdf"));

            //TODO Question Number 2
            if (areYouACompositeTaxableDealer) {
                page.waitForSelector(LVendorRegistration.QUESTION_NUMBER_TWO_YES.getSelector()).click();
                page.locator(LVendorRegistration.ATTACH_COMPOSITE_TAX_DOCUMENT.getSelector()).setInputFiles(Paths.get("documents/Attach Composite Tax Document .pdf"));
            }

            //TODO Question 3
            if (confirmRegistrationUnderMSMEACT2006) {
                page.waitForSelector(LVendorRegistration.MSME_REGISTRATION_YES.getSelector()).click();
                page.waitForSelector(LVendorRegistration.ENTERPRISE_UNDER_MSME.getSelector()).scrollIntoViewIfNeeded();
                page.waitForSelector(LVendorRegistration.ENTERPRISE_UNDER_MSME.getSelector()).click();
                page.waitForSelector(LVendorRegistration.getEnterpriseUnderMSME(enterpriseUnderMSME)).scrollIntoViewIfNeeded();
                page.waitForSelector(LVendorRegistration.getEnterpriseUnderMSME(enterpriseUnderMSME)).click();
                page.waitForSelector(LVendorRegistration.MSME_REGISTRATION_NUMBER.getSelector()).fill(msmeRegistrationNumber);
                page.waitForSelector(LVendorRegistration.MSME_DATE_OPEN_CALENDAR.getSelector()).click();
                int today = Integer.parseInt(page.locator(LVendorRegistration.TODAY_DATE.getSelector()).textContent().trim());
                int yesterday = today - 1;
                page.waitForSelector(LVendorRegistration.getYesterdayDate(yesterday)).click();
                page.locator(LVendorRegistration.ATTACH_MSME_CERTIFICATE.getSelector()).setInputFiles(Paths.get("documents/Attach MSME Certificate .pdf"));
            }
        } catch (Exception exception) {
            logger.error("Exception in Vendor Tax Details Function: {}", exception.getMessage());
        }
    }

    public void serviceEscalationMatrix() {
        try {
            page.locator(LVendorRegistration.ATTACH_SERVICE_DOCUMENT.getSelector()).setInputFiles(Paths.get("documents/Attach Service Document .pdf"));
        } catch (Exception exception) {
            logger.error("Exception in Vendor Service Escalation Matrix Function: {}", exception.getMessage());
        }
    }

    public void formFields() {
        try {
            String specification = jsonNode.get("vendorRegistration").get("formFields").get("specification").asText();
            page.waitForSelector(LVendorRegistration.SPECIFICATION.getSelector()).fill(specification);
        } catch (Exception exception) {
            logger.error("Exception in Vendor Form Fields Function: {}", exception.getMessage());
        }
    }

    public void vendorLoginDetails() {
        try {
//            String firstName = jsonNode.get("vendors").get("vendorName").asText();
            String firstName = "First";
            String lastName = jsonNode.get("vendorRegistration").get("vendorLoginDetails").get("lastName").asText();
            String designation = jsonNode.get("vendorRegistration").get("vendorLoginDetails").get("designation").asText();
            String phoneNumber = jsonNode.get("vendorRegistration").get("vendorLoginDetails").get("phoneNumber").asText();
            String password = jsonNode.get("vendorRegistration").get("vendorLoginDetails").get("password").asText();
            String confirmPassword = jsonNode.get("vendorRegistration").get("vendorLoginDetails").get("confirmPassword").asText();

            page.waitForSelector(LVendorRegistration.FIRST_NAME.getSelector()).fill(firstName);
            page.waitForSelector(LVendorRegistration.LAST_NAME.getSelector()).fill(lastName);
            page.waitForSelector(LVendorRegistration.DESIGNATION.getSelector()).fill(designation);
            page.waitForSelector(LVendorRegistration.PHONE_NUMBER.getSelector()).fill(phoneNumber);
            page.waitForSelector(LVendorRegistration.PASSWORD.getSelector()).fill(password);
            page.waitForSelector(LVendorRegistration.CONFIRM_PASSWORD.getSelector()).fill(confirmPassword);
        } catch (Exception exception) {
            logger.error("Exception in Vendor Login Details Function: {}", exception.getMessage());
        }
    }

    public void vendorSubmitsVendorRegistrationForm() {
        try {
            page.waitForSelector(LVendorRegistration.SUBMIT_BUTTON.getSelector()).click();
            page.waitForSelector(LVendorRegistration.YES_BUTTON.getSelector()).click();
        } catch (Exception exception) {
            logger.error("Exception in Vendor Submits Vendor Registration Form Function: {}", exception.getMessage());
        }
    }

    public void verifyVendorRegistrationFormSubmission() {
        try {
            PlaywrightAssertions.assertThat(page.locator("//span[text()='Enterprise P2P System ']")).isVisible();
            superAdminLogin();
            clickVendorsModule();
            String vendorName = jsonNode.get("vendors").get("vendorName").asText();
            searchListPage(vendorName);
            Locator x = page.locator("(//tr[.//td[@data-label='Name' and contains(text(),'" + vendorName + "')]])[1]");
            PlaywrightAssertions.assertThat(x.locator("//td[@data-label='Status']//span")).containsText("Vendor Registration Pending", new LocatorAssertions.ContainsTextOptions().setIgnoreCase(true));
            page.waitForSelector(LVendorRegistration.detailsButtonForVendorName(vendorName)).click();
            page.waitForSelector("//span[contains(text(),'Supplier has completed registration')]").scrollIntoViewIfNeeded();
        } catch (Exception exception) {
            logger.error("Exception in Verify Vendor Registration Form Submission Function: {}", exception.getMessage());
        }
    }

    public List<String> clusterHeadAssignApprovers() {
        approvers = new ArrayList<>();
        String stateHead = jsonNode.get("mailIds").get("stateHead").asText();
        String regionHead = jsonNode.get("mailIds").get("regionalHead").asText();

        page.getByRole(AriaRole.ROW, new Page.GetByRoleOptions().setName("Select Approvers")).getByRole(AriaRole.BUTTON).first().click();
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Number")).fill(stateHead);
        page.getByText(stateHead, new Page.GetByTextOptions().setExact(true)).click();
        approvers.add(stateHead);

        page.getByRole(AriaRole.ROW, new Page.GetByRoleOptions().setName("Select Approvers")).getByRole(AriaRole.BUTTON).last().click();
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Number")).fill(regionHead);
        page.getByText(regionHead, new Page.GetByTextOptions().setExact(true)).click();
        approvers.add(regionHead);

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Apply Approvers")).click();

        page.waitForSelector(LVendorRegistration.APPROVAL_PENDING_STATUS.getSelector()).scrollIntoViewIfNeeded();

        iLogout.performLogout();

        return approvers;
    }

    public void approversApproveVendorRegistration(List<String> approvers) {
        for (String approver : approvers) {
            iLogin.performLogin(approver);

            page.waitForSelector("//h4[text()='Vendor Registration']").click();
            String vendorName = jsonNode.get("vendors").get("vendorName").asText();
            page.waitForSelector(LVendorEDDRegistration.myAppprovalsDetailsButtonForVendorName(vendorName)).click();
            page.waitForSelector(LVendorEDDRegistration.APPROVE_BUTTON.getSelector()).scrollIntoViewIfNeeded();
            page.waitForSelector(LVendorEDDRegistration.APPROVE_BUTTON.getSelector()).click();

            page.waitForSelector("//textarea").fill("Approved by " + approver.split("@")[0]);

            page.waitForSelector(LVendorEDDRegistration.SUBMIT_BUTTON_FOR_REMARKS.getSelector()).scrollIntoViewIfNeeded();
            page.waitForSelector(LVendorEDDRegistration.SUBMIT_BUTTON_FOR_REMARKS.getSelector()).click();

            page.waitForSelector("//h4[text()=' My Approvals ']");

            iLogout.performLogout();
        }
    }

    public void verifyRegistrationIsApproved() {
        superAdminLogin();
        clickVendorsModule();
        String vendorName = jsonNode.get("vendors").get("vendorName").asText();
        searchListPage(vendorName);

        page.waitForSelector("//tr[./td[contains(text(),'"+vendorName+"')]]//td[@data-label='Status']").scrollIntoViewIfNeeded();
        PlaywrightAssertions.assertThat(page.locator("//tr[./td[contains(text(),'"+vendorName+"')]]//td[@data-label='Status']//span")).containsText("Approved", new LocatorAssertions.ContainsTextOptions().setIgnoreCase(true));

        iLogout.performLogout();
    }

    public void searchListPage(String vendorName) {
        page.waitForSelector("//input").fill(vendorName);
        page.waitForSelector("//input").press("Enter");
        String x = "(//tr[.//td[@data-label='Name' and contains(text(),'" + vendorName + "')]])[1]";
        page.waitForSelector(x, new Page.WaitForSelectorOptions()
                .setTimeout(60000));
    }

    public void registrationFullApprove(String approver){
        iLogin.performLogin(approver);

        page.waitForSelector("//h4[contains(text(),'Vendor Registration')]").click();
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
            registrationFullApprove(nextApproverEmail);
        }
    }

}