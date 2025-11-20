package com.utils;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Response;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.utils.GetTitleUtil.jsonNode;

public class SaveToTestDataJsonUtil {

    static PlaywrightFactory playwrightFactory;
    static Logger logger;
    static JsonNode jsonNode;
    static ObjectMapper objectMapper;

//TODO Constructor
    private SaveToTestDataJsonUtil(){
    }

    public SaveToTestDataJsonUtil(PlaywrightFactory playwrightFactory, ObjectMapper objectMapper, JsonNode jsonNode) {
        this.playwrightFactory = playwrightFactory;
        this.objectMapper = objectMapper;
        this.jsonNode = jsonNode;
        this.logger = LoggerUtil.getLogger(SaveToTestDataJsonUtil.class);
    }

    public static void saveReferenceIdFromResponse(Response response, String parentKey, String attributeKey) {
        try {
            String responseBody = response.text();
            JsonNode responseJson = objectMapper.readTree(responseBody);
            String referenceId = responseJson.get("referenceId").asText();
            playwrightFactory.savePropertiesIntoJsonFile(parentKey, attributeKey, referenceId);
        } catch(Exception exception) {
            logger.error("Exception in Save Reference Id from Response function: {}", exception.getMessage());
        }
    }

    public static void saveReferenceIdFromResponse(Response response, String node, String parentKey, String attributeKey) {
        try {
            String responseBody = response.text();
            JsonNode responseJson = objectMapper.readTree(responseBody);
            String referenceId = responseJson.get(node).get(0).get("referenceId").asText();
            playwrightFactory.savePropertiesIntoJsonFile(parentKey, attributeKey, referenceId);
        } catch(Exception exception) {
            logger.error("Exception in Save Reference Id from Response function (First Index Node): {}", exception.getMessage());
        }
    }

    public static void saveReferenceIdFromApiResponse(APIResponse response, String parentKey, String attributeKey) {
        try {
            String responseBody = response.text();
            JsonNode responseJson = objectMapper.readTree(responseBody);
            String referenceId = responseJson.get("referenceId").asText();
            playwrightFactory.savePropertiesIntoJsonFile(parentKey,attributeKey,referenceId);
        } catch (Exception exception) {
            logger.error("Exception in Save Reference Id from API Response function: {}", exception.getMessage());
        }
    }

    public static void saveVerifierEmail(Response response, String parentKey, String attributeKey) {
        try {
            String responseBody = response.text();
            JsonNode responseJson = objectMapper.readTree(responseBody);
            String verifierRole = responseJson.get("verifierRole").asText();
            String verifierEmail = "";
            switch (verifierRole){
                case "Finance Checker":
                    verifierEmail = jsonNode.get("mailIds").get("financeCheckerEmail").asText();
                    break;
                case "Buyer":
                    verifierEmail = jsonNode.get("mailIds").get("buyerEmail").asText();
                    break;
                default :
                    break;
            }
            playwrightFactory.savePropertiesIntoJsonFile(parentKey, attributeKey, verifierEmail);
        } catch(Exception exception) {
            logger.error("Exception in Save Reference Id from Response function: {}", exception.getMessage());
        }
    }

    public static String saveAndReturNextApprover(Response response, String invoiceType) {
        String nextApproverEmail = "";
        try {
            JsonNode responseJson = objectMapper.readTree(response.body());
            for(int i = 0; i < responseJson.get("approvers").size(); i++){
                String approverStatus = responseJson.get("approvers").get(i).get("approverStatus").asText();
                if(approverStatus.equalsIgnoreCase("Pending")) {
                    nextApproverEmail = responseJson.get("approvers").get(i).get("email").asText();
                    break;
                }
            }

            if(invoiceType.equalsIgnoreCase("AdvancePaymentInvoice")) {
                playwrightFactory.savePropertiesIntoJsonFile("invoices", "advancePaymentInvoiceApprovers", nextApproverEmail);
            } else if (invoiceType.equalsIgnoreCase("MilestonePaymentInvoice")) {
                playwrightFactory.savePropertiesIntoJsonFile("invoices", "milestonePaymentInvoiceApprovers", nextApproverEmail);
            } else if (invoiceType.equalsIgnoreCase("NormalInvoice")){
                playwrightFactory.savePropertiesIntoJsonFile("invoices", "normalInvoiceApprovers", nextApproverEmail);
            }
        } catch(Exception exception) {
            logger.error("Exception in Save Reference Id from Response function (First Index Node): {}", exception.getMessage());
        }
        return nextApproverEmail;
    }

    public static String saveAndReturNextApprover(Response response) {
        String nextApproverEmail = "";
        try {
            JsonNode responseJson = objectMapper.readTree(response.body());
            for(int i = 0; i < responseJson.get("approvers").size(); i++){
                String approverStatus = responseJson.get("approvers").get(i).get("approverStatus").asText();
                if(approverStatus.equalsIgnoreCase("Pending")) {
                    nextApproverEmail = responseJson.get("approvers").get(i).get("email").asText();
                    break;
                }
            }

            playwrightFactory.savePropertiesIntoJsonFile("invoices", "woInvoiceApprovers", nextApproverEmail);
        } catch(Exception exception) {
            logger.error("Exception in Save Reference Id from Response function (First Index Node): {}", exception.getMessage());
        }
        return nextApproverEmail;
    }
}