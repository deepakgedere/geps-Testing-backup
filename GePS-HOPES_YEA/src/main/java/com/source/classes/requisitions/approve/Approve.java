package com.source.classes.requisitions.approve;
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
import com.source.interfaces.requisitions.IPrApprove;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.requisitions.LPrApprove.*;
import static com.utils.GetTitleUtil.getTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class Approve implements IPrApprove {

    private Logger logger;
    private ObjectMapper objectMapper;
    private ILogin iLogin;
    private ILogout iLogout;
    private JsonNode jsonNode;
    PlaywrightFactory playwrightFactory;
    private Page page;
    private String appUrl;

    private Approve(){
    }

//TODO Constructor
    public Approve(ObjectMapper objectMapper, ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, PlaywrightFactory playwrightFactory){
        this.objectMapper = objectMapper;
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.playwrightFactory = playwrightFactory;
        this.logger = LoggerUtil.getLogger(Approve.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
    }

    public int approve(String type, String purchaseType) {
        int status = 0;
        int approved = 0;

        try {
            String requisitionStatus = "";
            String[] approvers = jsonNode.get("requisition").get("requisitionApprovers").asText().split(",");
            String remarks = jsonNode.get("commonRemarks").get("approveRemarks").asText();

            if(approvers[0].equalsIgnoreCase("")) {
                logger.error("No approvers found for the requisition");
                approved = 1;
                return approved;
            }

            for(String approver : approvers) {
                iLogin.performLogin(approver);

                String title = getTransactionTitle(type, purchaseType);
                Locator transaction = page.locator(getTitle(title));
                locatorVisibleHandler(transaction);
                transaction.first().click();

                Locator approveButton = page.locator(APPROVE);
                locatorVisibleHandler(approveButton);
                approveButton.click();

                Locator approveRemarksLocator = page.locator(APPROVE_REMARKS);
                locatorVisibleHandler(approveRemarksLocator);
                approveRemarksLocator.fill(remarks + " " + "by" + " " + approver);

                Locator submitButtonLocator = page.locator(SUBMIT_BUTTON);
                locatorVisibleHandler(submitButtonLocator);

                String reqType;
                if(type.equalsIgnoreCase("sales")){
                    reqType = "/api/RequisitionsSales/";
                } else if(type.equalsIgnoreCase("ps")){
                    reqType = "/api/Requisitions/";
                } else {
                    reqType = "/api/RequisitionsNonPoc/";
                }

                Response statusResponse = page.waitForResponse(
                        response -> response.url().startsWith(appUrl + reqType) && response.status() == 200,
                        submitButtonLocator::click
                );

                status = statusResponse.status();
                JsonNode responseJson = objectMapper.readTree(statusResponse.body());

                if(responseJson.has("status")) {
                    requisitionStatus = responseJson.get("status").asText();
                }

                String url = page.url();
                String[] urlArray = url.split("=");
                String getUid = urlArray[1];
                playwrightFactory.savePropertiesIntoJsonFile("requisition", "requisitionUid", getUid);

                if (type.equalsIgnoreCase("sales"))
                {
                    APIResponse apiResponse = page.request().fetch(appUrl + "/api/RequisitionsSales/" + getUid, RequestOptions.create());
                    JsonNode jsonNode1 = objectMapper.readTree(apiResponse.body());
                    String requisitionId = jsonNode1.get("requisitionId").asText();
                    String transactionId = jsonNode1.get("transactionId").asText();
                    playwrightFactory.savePropertiesIntoJsonFile("requisition", "requisitionId", requisitionId);
                    playwrightFactory.savePropertiesIntoJsonFile("requisition", "salesTransactionNumber", transactionId);
                } else if(type.equalsIgnoreCase("ps")) {
                    APIResponse apiResponse = page.request().fetch(appUrl + "/api/Requisitions/" + getUid, RequestOptions.create());
                    JsonNode jsonNode1 = objectMapper.readTree(apiResponse.body());
                    String requisitionId = jsonNode1.get("requisitionId").asText();
                    String transactionId = jsonNode1.get("transactionId").asText();
                    playwrightFactory.savePropertiesIntoJsonFile("requisition", "requisitionId", requisitionId);
                    playwrightFactory.savePropertiesIntoJsonFile("requisition", "psTransactionNumber", transactionId);
                } else {
                    APIResponse apiResponse = page.request().fetch(appUrl + "/api/RequisitionsNonPoc/" + getUid, RequestOptions.create());
                    JsonNode jsonNode1 = objectMapper.readTree(apiResponse.body());
                    String requisitionId = jsonNode1.get("requisitionId").asText();
                    String transactionId = jsonNode1.get("transactionId").asText();
                    playwrightFactory.savePropertiesIntoJsonFile("requisition", "requisitionId", requisitionId);
                    playwrightFactory.savePropertiesIntoJsonFile("requisition", "sdTransactionNumber", transactionId);
                }

                PlaywrightFactory.attachScreenshotWithName("Requisition Approve", page);

                iLogout.performLogout();

                if(requisitionStatus.equals("Approved")) {
                    break;
                }
            }
        } catch (Exception exception) {
            logger.error("Exception in Requisition Approve Function: {}", exception.getMessage());
        }
        return status;
    }
}