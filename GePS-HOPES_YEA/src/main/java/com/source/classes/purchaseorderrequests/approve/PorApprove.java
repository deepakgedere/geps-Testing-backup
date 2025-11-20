package com.source.classes.purchaseorderrequests.approve;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.RequestOptions;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.source.interfaces.purchaseorderrequests.IPorApprove;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.purchaseorderrequests.IPorSendForApproval;
import com.utils.LoggerUtil;
import com.utils.rpa.purchaseorderrequest.MSA_Flow;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import static com.constants.purchaseorderrequests.LPorApprove.*;
import static com.utils.GetTitleUtil.getTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class PorApprove implements IPorApprove {

    Logger logger;
    ObjectMapper objectMapper;
    PlaywrightFactory playwrightFactory;
    MSA_Flow msaFlow;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    IPorSendForApproval iPorSendForApproval;
    String appUrl;

    private PorApprove() {
    }

//TODO Constructor
    public PorApprove(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, PlaywrightFactory playwrightFactory, ObjectMapper objectMapper, IPorSendForApproval iPorSendForApproval, MSA_Flow msaFlow) {
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.playwrightFactory = playwrightFactory;
        this.objectMapper = objectMapper;
        this.iPorSendForApproval = iPorSendForApproval;
        this.msaFlow = msaFlow;
        this.logger = LoggerUtil.getLogger(PorApprove.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public void savePorAprovers(String type, String purchaseType) {
        List<String> porApprovers = new ArrayList<>();
        try {
            iPorSendForApproval.sendForApproval(type, purchaseType);

            String firstApprover = jsonNode.get("purchaseOrderRequests").get("approvers").asText();
            String appUrl = jsonNode.get("configSettings").get("appUrl").asText();
            int id = jsonNode.get("purchaseOrderRequests").get("purchaseOrderRequestId").asInt();

            iLogin.performLogin(firstApprover);

            Locator myApprovalsButtonLocator = page.locator(MY_APPROVALS);
            locatorVisibleHandler(myApprovalsButtonLocator);
            myApprovalsButtonLocator.click();

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
                    if (approvalPopupLocator.last().isEnabled() && approvalPopupLocator.last().isVisible()) {
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
            Locator approveButtonLocator = page.locator(APPROVE_BUTTON);
            locatorVisibleHandler(approveButtonLocator);
            approveButtonLocator.click();

            Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
            locatorVisibleHandler(acceptButtonLocator);
            acceptButtonLocator.click();

            APIResponse apiResponse = page.request().fetch(appUrl + "/api/Approvals?entityId=" + id + "&approvalTypeEnum=PurchaseOrderRequest");
            JsonNode responseNode = objectMapper.readTree(apiResponse.body());
            JsonNode approvers = responseNode.get("approvers");

            for (JsonNode approver : approvers) {
                String porApprover = approver.get("email").asText();
                porApprovers.add(porApprover);
            }

            playwrightFactory.savePorApproversIntoJsonFile("purchaseOrderRequests", "approvers", porApprovers);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in POR Save Approvers function: {}", exception.getMessage());
        }
    }

    public int approve(String type, String purchaseType){
        int status = 0;
        try {
            savePorAprovers(type, purchaseType);
            JsonNode approvers = jsonNode.get("purchaseOrderRequests").get("approvers");

            if(approvers.size() == 1){
                return 200; //TODO Skip approval if only one approver is present
            }

            for(int i = 0; i < approvers.size(); i++) {
                if (i == 0) {
                    continue; //TODO to skip first approval
                }

                String approver = approvers.get(i).asText();
                iLogin.performLogin(approver);

                Locator myApprovalsButtonLocator = page.locator(MY_APPROVALS);
                locatorVisibleHandler(myApprovalsButtonLocator);
                myApprovalsButtonLocator.click();

                String title = getTransactionTitle(type, purchaseType);
                Locator titleLocator = page.locator(getTitle(title));
                locatorVisibleHandler(titleLocator);
                titleLocator.first().click();

                Locator approveButtonLocator = page.locator(APPROVE_BUTTON);
                locatorVisibleHandler(approveButtonLocator);
                approveButtonLocator.click();

                Locator acceptButtonLocator = page.locator(ACCEPT_BUTTON);
                locatorVisibleHandler(acceptButtonLocator);

                String porType;
                if(type.equalsIgnoreCase("sales")){
                    porType = "/api/PurchaseOrderRequestsSales/";
                } else if(type.equalsIgnoreCase("ps")){
                    porType = "/api/PurchaseOrderRequests/";
                } else {
                    porType = "/api/PurchaseOrderRequestsNonPOC/";
                }

                Response approveResponse = page.waitForResponse(
                        response -> response.url().startsWith(appUrl + porType) && response.status() == 200,
                        acceptButtonLocator::click
                );
                status = approveResponse.status();

                if(i == approvers.size() - 1) {
                    String appUrl = jsonNode.get("configSettings").get("appUrl").asText();

                    String url = page.url();
                    String[] urlArray = url.split("=");
                    String getUid = urlArray[1];
                    playwrightFactory.savePropertiesIntoJsonFile("purchaseOrderRequests", "purchaseOrderRequestUid", getUid);

                    APIResponse apiResponse = page.request().fetch(appUrl + porType + getUid, RequestOptions.create());
                    JsonNode jsonNode = objectMapper.readTree(apiResponse.body());
                    String purchaseOrderRequestId = jsonNode.get("id").asText();
                    JsonNode purchaseOrders = jsonNode.get("purchaseOrders");
                    boolean poProcessed;
                    if (purchaseOrders != null && purchaseOrders.isArray() && purchaseOrders.size() > 0) {
                        poProcessed = true;
                    } else {
                        poProcessed = false;
                    }
                    String porStatus = jsonNode.get("status").asText();
                    String porReferenceNumber = jsonNode.get("referenceId").asText();
                    playwrightFactory.savePropertiesIntoJsonFile("purchaseOrderRequests", "purchaseOrderRequestId", purchaseOrderRequestId);
                    playwrightFactory.savePropertiesIntoJsonFile("purchaseOrderRequests", "porReferenceNumber", porReferenceNumber);
                    playwrightFactory.savePropertiesIntoJsonFile("purchaseOrderRequests", "porStatus", porStatus);
                    playwrightFactory.savePropertiesIntoJsonFile("purchaseOrderRequests", "poProcessed", String.valueOf(poProcessed));

                    if(porStatus.equalsIgnoreCase("ProcessingPO")) {
                        msaFlow.msaFlow();
                    } else {
                        throw new RuntimeException("POR is not in Processing PO status");
                    }
                }

                PlaywrightFactory.attachScreenshotWithName("Purchase Order Request Approve", page);

                iLogout.performLogout();
            }
        } catch (Exception exception) {
            logger.error("Exception in POR Approve function: {}", exception.getMessage());
        }
        return status;
    }
}