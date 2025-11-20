Feature: Vendor Management Functional Flows

#  Scenario: Cluster Head can access Vendor Master
#    Given Cluster Head is logged in
#    When Cluster Head navigates to Vendor Master
#    And Cluster Head should have option to search for Vendor and click on details button
#    And Cluster Head should have option to Invite Vendor
#    And Cluster Head should have option add all the mandatory details
#    Then Cluster Head should be able to invite Vendor successfully

#  Scenario: Vendor can submit EDD Registration
#    Given Super Admin is logged in
#    When Super Admin navigates to Vendor Master
#    And Super Admin send email notification to vendor to complete the EDD registration
#    And Vendor will complete EDD registration and submits the form
#    Then Vendor EDD registration form is submitted successfully
#
#  Scenario: Vendor EDD Approval Flow
#    Given Cluster Head is logged in
#    When Cluster Head navigates to Vendor Details Page
#    And Approvers Are Present in Details Page
#    And Approvers login and approve EDD
#    Then EDD is approved successfully
#
#  Scenario: Vendor can submit Registration Form
#    Given Super Admin is logged in
#    When Super Admin navigates to Vendor Master
#    And Super Admin send email notification to vendor to complete the registration
#    And Vendor will receive an email with a link to complete vendor registration and submits the form
#    And Vendor will complete the vendor registration form with all mandatory details
#    Then Vendor successfully submits the vendor registration form

  Scenario: Vendor Registration Approval Flow
    Given Cluster Head is logged in
    When Cluster Head navigates to Vendor Details Page
    And Approvers Are Present in Details Page
    And Approvers login and approve Vendor Registration
    Then Vendor Registration is approved successfully