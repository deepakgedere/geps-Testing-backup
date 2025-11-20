package com.source.classes.requisitions.sendforapproval;
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
import com.source.interfaces.requisitions.IPrSendForApproval;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import static com.constants.requisitions.LPrSendForApproval.*;
import static com.utils.GetTitleUtil.getTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class SendForApproval implements IPrSendForApproval {

    Logger logger;
    private PlaywrightFactory playwrightFactory;
    private ObjectMapper objectMapper;
    private Page page;
    private JsonNode jsonNode;
    private ILogin iLogin;
    private ILogout iLogout;
    private String appUrl;

    private SendForApproval() {
    }

//TODO Constructor
    public SendForApproval(PlaywrightFactory playwrightFactory, ObjectMapper objectMapper, ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout) {
        this.playwrightFactory = playwrightFactory;
        this.objectMapper = objectMapper;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(SendForApproval.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int sendForApproval(String type, String purchaseType) {
        int approvalStatus = 0;
        List<String> approvers = new ArrayList<>();

        try {
            String requesterMailId = jsonNode.get("mailIds").get("requesterEmail").asText();

            iLogin.performLogin(requesterMailId);

            String title = getTransactionTitle(type, purchaseType);
            Locator titleLocator = page.locator(getTitle(title));
            titleLocator.first().click();

            String url = page.url();
            String[] urlArray = url.split("=");
            String getUid = urlArray[1];

            playwrightFactory.savePropertiesIntoJsonFile("requisition", "requisitionUid", getUid);

            Locator sendForApprovalButtonLocator = page.locator(SEND_FOR_APPROVAL_BUTTON);
            locatorVisibleHandler(sendForApprovalButtonLocator);
            sendForApprovalButtonLocator.click();

            Locator yesButtonLocator = page.locator(YES);
            locatorVisibleHandler(yesButtonLocator);

            String reqType;
            if (type.equalsIgnoreCase("sales")) {
                reqType = "/api/RequisitionsSales/";
            } else if (type.equalsIgnoreCase("ps")) {
                reqType = "/api/Requisitions/";
            } else {
                reqType = "/api/RequisitionsNonPoc/";
            }

            Response sendResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + reqType) && response.status() == 200,
                    yesButtonLocator::click
            );
            approvalStatus = sendResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Requisition Send For Approval", page);

            APIResponse requisitionResponse = page.request().fetch(appUrl + reqType + getUid, RequestOptions.create());
            JsonNode requisitionJson = objectMapper.readTree(requisitionResponse.body());
            int requisitionId = requisitionJson.get("requisitionId").asInt();

            APIResponse approversResponse = page.request().fetch(appUrl + "/api/Approvals?entityId=" + requisitionId + "&approvalTypeEnum=Requisition", RequestOptions.create());
            JsonNode approversJson = objectMapper.readTree(approversResponse.body());

            if (approversJson.has("approvers")) {
                JsonNode approversArray = approversJson.get("approvers");
                for (JsonNode approver : approversArray) {
                    if (approver.has("email")) {
                        String finalApprover = approver.get("email").asText();
                        approvers.add(finalApprover);
                    }
                }
            }

            if (!approvers.isEmpty()) {
                for (String approver : approvers) {
                    playwrightFactory.savePropertiesIntoJsonFile("requisition", "requisitionApprovers", approver);
                }
            } else {
                playwrightFactory.savePropertiesIntoJsonFile("requisition", "requisitionApprovers", "");
            }

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Requisition Send For Approval Function: {}", exception.getMessage());
        }
        return approvalStatus;
    }
}