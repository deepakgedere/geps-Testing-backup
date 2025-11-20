package com.source.classes.infra.purchaseorder.approve;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.source.interfaces.infra.purchaseorder.approve.IPoApprove;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.io.FileWriter;

import static com.enums.infra.purchaseorders.approve.LPoApprove.*;
import static com.factory.PlaywrightFactory.attachScreenshotWithName;
import static com.utils.GetRowUtil.getPoRow;

public class POApprove implements IPoApprove {
    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    ObjectMapper objectMapper = new ObjectMapper();

    private POApprove() {
    }

    //TODO Constructor
    public POApprove(JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(POApprove.class);
    }

    public void poApproverLogin() {
        try {
            String poApproverMailId = jsonNode.get("mailIds").get("POApproveLogin").asText();
            iLogin.performLogin(poApproverMailId);
        } catch (Exception exception) {
            logger.error("Exception in PO Approver Login function: {}", exception.getMessage());
        }
    }

    public void poDetails(String trnId) {
        try {
            try {
               page.waitForSelector(PO_LIST_PAGE.getSelector()).click();
            } catch (Exception e) {
                page.waitForSelector(ASN_LIST_PAGE.getSelector()).click();
                page.waitForSelector(PO_LIST_PAGE.getSelector()).click();
            }
            Locator row = getPoRow(page, trnId);
            Locator detailsButton = row.locator(DETAILS_PAGE.getSelector());
            detailsButton.click();
        } catch (Exception exception) {
            logger.error("Exception in Details PR function: {}", exception.getMessage());
        }
    }

    public void approve(){
        try {
            String remarks = jsonNode.get("commonRemarks").get("approveRemarks").asText();

            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Approve")).click();

            page.getByRole(AriaRole.TEXTBOX).click();

            page.getByRole(AriaRole.TEXTBOX).fill(remarks);

            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();

            page.waitForSelector(APPROVE_MESSAGE.getSelector()).scrollIntoViewIfNeeded();
            attachScreenshotWithName("PO Approved" , page);

            String vendorEmail = page.locator(VENDOR_EMAIL.getSelector()).innerText().trim();

            //Add vendor Emails, so that new emails are saved when PO is approved
            try {
                ObjectNode parentNode = (ObjectNode) jsonNode.get("mailIds");
                com.fasterxml.jackson.databind.node.ArrayNode arrayNode;
                if (parentNode.has("VendorEmails") && parentNode.get("VendorEmails").isArray()) {
                    arrayNode = (com.fasterxml.jackson.databind.node.ArrayNode) parentNode.get("VendorEmails");
                } else {
                    arrayNode = objectMapper.createArrayNode();
                    parentNode.set("VendorEmails", arrayNode);
                }
                    arrayNode.add(vendorEmail);

                try (FileWriter fileWriter = new FileWriter("./src/test/resources/config/test-data.json")) {
                    objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, jsonNode);
                }
            } catch (Exception exception) {
                logger.error("Error in Save Properties Into Json File Function: {}", exception.getMessage());
            }

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in Details PR function: {}", exception.getMessage());
        }
    }
}