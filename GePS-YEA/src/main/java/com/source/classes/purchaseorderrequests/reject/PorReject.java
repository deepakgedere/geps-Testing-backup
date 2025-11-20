package com.source.classes.purchaseorderrequests.reject;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.source.interfaces.purchaseorderrequests.IPorSendForApproval;
import com.microsoft.playwright.Page;
import com.source.interfaces.purchaseorderrequests.IPorReject;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.purchaseorderrequests.IPorEdit;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.purchaseorderrequests.LPorApprove.ADD_APPROVERS;
import static com.constants.purchaseorderrequests.LPorApprove.MY_APPROVALS;
import static com.constants.purchaseorderrequests.LPorReject.*;
import static com.utils.GetTitleUtil.getTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class PorReject implements IPorReject {

    Logger logger;
    ILogin iLogin;
    ILogout iLogout;
    JsonNode jsonNode;
    Page page;
    IPorEdit iPorEdit;
    IPorSendForApproval iPorSendForApproval;
    String appUrl;


    private PorReject(){
    }

//TODO Constructor
    public PorReject(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, IPorEdit iPorEdit, IPorSendForApproval iPorSendForApproval){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.iPorEdit = iPorEdit;
        this.iPorSendForApproval = iPorSendForApproval;
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
        this.logger = LoggerUtil.getLogger(PorReject.class);
    }

    public int porReject(String type, String purchaseType) {
        int status = 0;
        try {
            String approver = jsonNode.get("purchaseOrderRequests").get("approvers").asText();

            iLogin.performLogin(approver);

            Locator porNavigationBar = page.locator(MY_APPROVALS);
            locatorVisibleHandler(porNavigationBar);
            porNavigationBar.click();

            String title = getTransactionTitle(type, purchaseType);
            Locator titleLocator = page.locator(getTitle(title));
            locatorVisibleHandler(titleLocator);
            titleLocator.first().click();

            Locator addApproversLocator = page.locator(ADD_APPROVERS);

            if(addApproversLocator.count()>0 && addApproversLocator.isVisible()) {
                locatorVisibleHandler(addApproversLocator);
                addApproversLocator.click();

                Locator projectManagerDropDownLocator = page.locator(PROJECT_MANAGER_DROP_DOWN);
                locatorVisibleHandler(projectManagerDropDownLocator);
                Locator departmentManagerDropDown = page.locator(DEPARTMENT_MANAGER_DROP_DOWN);
                locatorVisibleHandler(departmentManagerDropDown);
                Locator divisionManagerDropDown = page.locator(DIVISION_MANAGER);
                locatorVisibleHandler(divisionManagerDropDown);
                Locator approvalPopupLocator = page.locator(APPROVAL_POP_UP);
                locatorVisibleHandler(approvalPopupLocator);


                try {
                    approvalPopupLocator.last().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));
                    if (approvalPopupLocator.last().isEnabled()) {
                        if (projectManagerDropDownLocator.count() > 0 && projectManagerDropDownLocator.isEnabled() && projectManagerDropDownLocator.isVisible()) {
                            projectManagerDropDownLocator.click();
                            String groupB = jsonNode.get("mailIds").get("prApproverGroupBEmail").asText();
                            Locator searchFieldLocator = page.locator(SEARCH_FIELD);
                            locatorVisibleHandler(searchFieldLocator);
                            searchFieldLocator.fill(groupB);
                            Locator groupBLocator = page.locator(getGroupB(groupB));
                            locatorVisibleHandler(groupBLocator);
                            groupBLocator.first().click();
                        }
                        if (departmentManagerDropDown.count() > 0 && departmentManagerDropDown.isEnabled() && departmentManagerDropDown.isVisible()) {
                            departmentManagerDropDown.click();
                            String groupC = jsonNode.get("mailIds").get("prApproverGroupCEmail").asText();
                            Locator searchFieldLocator = page.locator(SEARCH_FIELD);
                            locatorVisibleHandler(searchFieldLocator);
                            searchFieldLocator.fill(groupC);
                            Locator groupCLocator = page.locator(getGroupC(groupC));
                            locatorVisibleHandler(groupCLocator);
                            groupCLocator.first().click();
                        }
                        if (divisionManagerDropDown.count() > 0 && divisionManagerDropDown.isEnabled() && divisionManagerDropDown.isVisible()) {
                            divisionManagerDropDown.click();
                            String groupD = jsonNode.get("mailIds").get("prApproverGroupDEmail").asText();
                            Locator searchFieldLocator = page.locator(SEARCH_FIELD);
                            locatorVisibleHandler(searchFieldLocator);
                            searchFieldLocator.fill(groupD);
                            Locator groupDLocator = page.locator(getGroupD(groupD));
                            locatorVisibleHandler(groupDLocator);
                            groupDLocator.first().click();
                        }
                        Locator saveUsersLocator = page.locator(SAVE_APPROVAL_USERS);
                        locatorVisibleHandler(saveUsersLocator);
                        saveUsersLocator.click();
                    }
                } catch (Exception exception) {
                    System.out.println("Approval Popup not found");
                }
            }
            Locator rejectButtonLocator = page.locator(REJECT_BUTTON);
            locatorVisibleHandler(rejectButtonLocator);
            rejectButtonLocator.click();

            Locator remarksInputLocator = page.locator(REMARKS_INPUT);
            locatorVisibleHandler(remarksInputLocator);
            remarksInputLocator.fill("Rejected");

            Locator acceptLocator = page.locator(YES);
            locatorVisibleHandler(acceptLocator);

            String porType;
            if(type.equalsIgnoreCase("sales")){
                porType = "/api/PurchaseOrderRequestsSales/";
            } else if(type.equalsIgnoreCase("POC")){
                porType = "/api/PurchaseOrderRequests/";
            } else {
                porType = "/api/PurchaseOrderRequestsNonPOC/";
            }

            Response rejectResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + porType) && response.status() == 200,
                    acceptLocator::click
            );
            status = rejectResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Purchase Order Request Reject", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in POR Reject function: {}", exception.getMessage());
        }
        return status;
    }
}