package com.source.vendors;

import com.base.BaseMain;
import com.base.BaseTest;

import com.utils.VendorDetails;
import io.cucumber.java.en.*;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class VendorRegistrationTest extends BaseMain {

    List<String> approvers;
    String nextApprover;

//TODO Scenario 1

    @Given("Cluster Head is logged in")
    public void clusterHeadIsLoggedIn() {
        try {
            iVendorInvite.clusterHeadLogin();
        } catch (Exception exception) {
            logger.error("Exception in Cluster Head Login Function: {}", exception.getMessage());
            Assert.fail();
        }
    }

    @When("Cluster Head navigates to Vendor Master")
    public void clusterHeadNavigatesToVendorMaster() {
        try {
            iVendorInvite.clickVendorsModule();
        } catch (Exception exception) {
            logger.error("Exception in navigating to Vendor Master: {}", exception.getMessage());
            Assert.fail();
        }
    }

    @And("Cluster Head should have option to search for Vendor and click on details button")
    public void searchAndViewVendorDetails() {
        try {
            VendorDetails vendorDetails = iVendorInvite.searchAndClickVendor();
            playwrightFactory.savePropertiesIntoJsonFile("vendors", "vendorName", vendorDetails.getVendorName());
            playwrightFactory.savePropertiesIntoJsonFile("vendors", "vendorEmail", vendorDetails.getEmailId());
        } catch (Exception exception) {
            logger.error("Exception in searching and viewing Vendor details: {}", exception.getMessage());
            Assert.fail();
        }
    }

    @And("Cluster Head should have option to Invite Vendor")
    public void clusterHeadShouldHaveOptionToInviteVendor() {
        try {
            iVendorInvite.inviteVendor();
        } catch (Exception exception) {
            logger.error("Exception in inviting Vendor: {}", exception.getMessage());
            Assert.fail();
        }
    }

    @And("Cluster Head should have option add all the mandatory details")
    public void clusterHeadShouldHaveOptionAddAllMandatoryDetails() {
        try {
            iVendorInvite.selectVendorDocumentType();
            iVendorInvite.selectSkillTechnicalExpertise();
            iVendorInvite.selectRegion();
//            iVendorInvite.fillFirstProjectName();
            iVendorInvite.referredBy();
            iVendorInvite.vendorType();
        } catch (Exception exception) {
            logger.error("Exception in adding mandatory details: {}", exception.getMessage());
            Assert.fail();
        }
    }

    @Then("Cluster Head should be able to invite Vendor successfully")
    public void clusterHeadShouldBeAbleToInviteVendorSuccessfully() {
        try {
            iVendorInvite.clickSubmit();
            iVendorInvite.clusterHeadLogout();
            iLogout.closeBrowser(page);

        } catch (Exception exception) {
            logger.error("Exception in inviting Vendor successfully: {}", exception.getMessage());
            Assert.fail();
        }
    }

//TODO Scenario  2

    @Given("Super Admin is logged in")
    public void superAdminIsLoggedIn() {
        try {
            iVendorEDDRegistration.superAdminLogin();
        } catch (Exception exception) {
            logger.error("Exception in Super Admin Login: {}", exception.getMessage());
            Assert.fail();
        }
    }

    @When("Super Admin navigates to Vendor Master")
    public void superAdminNavigatesToVendorMaster() {
        try {
            iVendorInvite.clickVendorsModule();
        } catch (Exception exception) {
            logger.error("Exception in navigating to Vendor Master: {}", exception.getMessage());
            Assert.fail();
        }
    }

    @And("Super Admin send email notification to vendor to complete the EDD registration")
    public void superAdminSendEmailNotificationToVendor() {
        try {
            iVendorEDDRegistration.clicksInviteVendorEmailButton();
        } catch (Exception exception) {
            logger.error("Exception in sending EDD registration email: {}", exception.getMessage());
            Assert.fail();
        }
    }

    @And("Vendor will complete EDD registration and submits the form")
    public void vendorReceivesEmailAndSubmitsEDDForm() {
        try {
            iVendorEDDRegistration.vendorSubmitsEDDRegistrationForm();
        } catch (Exception exception) {
            logger.error("Exception in vendor EDD registration form submission: {}", exception.getMessage());
            Assert.fail();
        }
    }

    @Then("Vendor EDD registration form is submitted successfully")
    public void vendorEDDRegistrationFormIsSubmittedSuccessfully() {
        try {
            iVendorEDDRegistration.verifyEDDRegistrationFormSubmission();
            iLogout.closeBrowser(page);
        } catch (Exception exception) {
            logger.error("Exception in verifying EDD registration form submission: {}", exception.getMessage());
            Assert.fail();
        }
    }

    //TODO  Scenario 3
    @When("Cluster Head navigates to Vendor Details Page")
    public void navigateToVendorDetailsPage() {
        try {
            iVendorEDDRegistration.navigateToVendorDetails();
        } catch (Exception exception) {
            logger.error("Exception in sending EDD registration form for approval: {}", exception.getMessage());
            Assert.fail();
        }
    }

    @And("Approvers Are Present in Details Page")
    public void selectPendingApprover(){
        try {
            nextApprover = iVendorEDDApproval.savePendingApprover();
            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in verifying Approvers in Details Page: {}", exception.getMessage());
            Assert.fail();
        }
    }

    @And("Approvers login and approve EDD")
    public void eddFullApprove() {
        try {
            iVendorEDDApproval.eddFullApprove(nextApprover);
        } catch (Exception exception) {
            logger.error("Exception in approvers approving EDD: {}", exception.getMessage());
            Assert.fail();
        }
    }

    @Then("EDD is approved successfully")
    public void eddIsApprovedSuccessfully() {
        try {
            iVendorEDDApproval.verifyEddIsApproved();
            iLogout.closeBrowser(page);
        } catch (Exception exception) {
            logger.error("Exception in verifying EDD approval: {}", exception.getMessage());
            Assert.fail();
        }
    }

//TODO Scenario 4

    @And("Super Admin send email notification to vendor to complete the registration")
    public void superAdminSendEmailNotification() {
        try {
            iVendorRegistration.clicksInviteVendorEmailButton();
        } catch (Exception exception) {
            logger.error("Exception in superAdminSendEmailNotification: {}", exception.getMessage());
            Assert.fail();
        }
    }

    @And("Vendor will receive an email with a link to complete vendor registration and submits the form")
    public void vendorReceivesEmailAndSubmitsRegistrationForm() {
        try {
            iVendorRegistration.vendorDetails();
            iVendorRegistration.vendorDetailsRadioButton();
            iVendorRegistration.vendorLocationDetails();
            iVendorRegistration.vendorBankDetails();
            iVendorRegistration.taxDetails();
            iVendorRegistration.serviceEscalationMatrix();
//            iVendorRegistration.formFields();
            iVendorRegistration.vendorLoginDetails();
        } catch (Exception exception) {
            logger.error("Exception in vendorReceivesEmailAndSubmitsForm: {}", exception.getMessage());
            Assert.fail();
        }
    }

    @Then("Vendor successfully submits the vendor registration form")
    public void vendorSuccessfullySubmitsRegistrationForm() {
        try {
            iVendorRegistration.verifyVendorRegistrationFormSubmission();
            iLogout.closeBrowser(page);
        } catch (Exception exception) {
            logger.error("Exception in vendorSuccessfullySubmitsRegistrationForm: {}", exception.getMessage());
            Assert.fail();
        }
    }

//TODO Scenario 5

    @And("Vendor will complete the vendor registration form with all mandatory details")
    public void vendorCompletesRegistrationForm() {
        try {
            iVendorRegistration.vendorSubmitsVendorRegistrationForm();
        } catch (Exception exception) {
            logger.error("Exception in vendorCompletesRegistrationForm: {}", exception.getMessage());
            Assert.fail();
        }
    }

    @And("Approvers login and approve Vendor Registration")
    public void approversApproveVendorRegistration() {
        try {
            iVendorRegistration.registrationFullApprove(nextApprover);
        } catch (Exception exception) {
            logger.error("Exception in approvers approving EDD: {}", exception.getMessage());
            Assert.fail();
        }
    }

    @Then("Vendor Registration is approved successfully")
    public void vendorRegistrationIsApprovedSuccessfully() {
        try {
            iVendorRegistration.verifyRegistrationIsApproved();
            iLogout.closeBrowser(page);
        } catch (Exception exception) {
            logger.error("Exception in verifying EDD approval: {}", exception.getMessage());
            Assert.fail();
        }
    }
}