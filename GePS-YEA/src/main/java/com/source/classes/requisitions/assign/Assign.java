package com.source.classes.requisitions.assign;
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
import com.source.interfaces.requisitions.IPrAssign;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.constants.requisitions.LPrAssign.*;
import static com.utils.GetTitleUtil.getTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class Assign implements IPrAssign {

    private PlaywrightFactory playwrightFactory;
    private JsonNode jsonNode;
    private Page page;
    private ILogin iLogin;
    private ILogout iLogout;
    private Logger logger;
    private String appUrl;
    private ObjectMapper objectMapper;

//TODO Constructor    
    private Assign(){
    }

//TODO Constructor
    public Assign(PlaywrightFactory playwrightFactory,ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, ObjectMapper objectMapper){
        this.playwrightFactory = playwrightFactory;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(Assign.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();
        this.objectMapper = objectMapper;
    }

    public int buyerManagerAssign(String type, String purchaseType) {
        int assignStatus = 0;
        try {
            String buyerMailId = jsonNode.get("mailIds").get("buyerEmail").asText();
            String buyerManagerMailId = jsonNode.get("mailIds").get("buyerManagerEmail").asText();
            String uid = jsonNode.get("requisition").get("requisitionUid").asText();

            iLogin.performLogin(buyerManagerMailId);

            String getTitle = getTransactionTitle(type, purchaseType);
            String getBuyerMailId = getBuyerMailId(buyerMailId);

            Locator titleLocator = page.locator(getTitle(getTitle));
            locatorVisibleHandler(titleLocator);
            titleLocator.first().click();

            Locator assignUser = page.locator(ASSIGN_USER);
            locatorVisibleHandler(assignUser);
            assignUser.click();

            Locator selectAssignUser = page.locator(SELECT_ASSIGN_USER);
            locatorVisibleHandler(selectAssignUser);
            selectAssignUser.click();

            Locator searchBox = page.locator(SEARCHBOX);
            locatorVisibleHandler(searchBox);
            searchBox.fill(buyerMailId);

            Locator buyerManager = page.locator(getBuyerMailId);
            locatorVisibleHandler(buyerManager);
            buyerManager.first().click();

            String assign = "";
            if(type.equalsIgnoreCase("sales")){
                assign = SAVE_USER_SALES;
            } else {
                assign = SAVE_USER;
            }

            Locator saveUser = page.locator(assign);
            locatorVisibleHandler(saveUser);

            String reqType;
            if(type.equalsIgnoreCase("sales")){
                reqType = "/api/RequisitionsSales/";
            } else if(type.equalsIgnoreCase("POC")){
                reqType = "/api/Requisitions/";
            } else {
                reqType = "/api/RequisitionsNonPoc/";
            }

            Response assignResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + reqType) && response.status() == 200,
                    saveUser::click
            );

            JsonNode requisitionJson = objectMapper.readTree(assignResponse.body());

            int itemsCount = 0;
            if(type.equalsIgnoreCase("sales")) {
                if (requisitionJson.has("requisitionItems")) {
                    JsonNode itemsArray = requisitionJson.get("requisitionItems");
                    for (int i = 1; i < itemsArray.size(); i++) {
                        itemsCount++;
                    }
                }
                playwrightFactory.savePropertiesIntoJsonFile("requisition", "requisitionItemCount", String.valueOf(itemsCount));
            }

            assignStatus = assignResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Requisition Assign", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Error in Requisition Buyer Manager Assign Function: {}", exception.getMessage());
        }
        return assignStatus;
    }
}