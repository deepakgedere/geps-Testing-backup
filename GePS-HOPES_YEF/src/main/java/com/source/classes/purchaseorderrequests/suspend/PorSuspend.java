package com.source.classes.purchaseorderrequests.suspend;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.purchaseorderrequests.IPorCreate;
import com.source.interfaces.purchaseorderrequests.IPorEdit;
import com.source.interfaces.purchaseorderrequests.IPorSuspend;
import com.source.interfaces.requestforquotations.ICeCreate;
import com.source.interfaces.requisitions.IPrApprove;
import com.source.interfaces.requisitions.IPrAssign;
import com.source.interfaces.requisitions.IPrEdit;
import com.source.interfaces.requisitions.IPrSendForApproval;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import static com.constants.purchaseorderrequests.LPorSuspend.*;
import static com.utils.GetTitleUtil.getTransactionTitle;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class PorSuspend implements IPorSuspend {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;
    IPrEdit iPrEdit;
    IPrSendForApproval iPrSendForApproval;
    IPrApprove iPrApprove;
    IPrAssign iPrAssign;
    IPorCreate iPorCreate;
    IPorEdit iPorEdit;
    ICeCreate iCeCreate;
    String appUrl;


    private PorSuspend(){
    }

//TODO Constructor
    public PorSuspend(ILogin iLogin, JsonNode jsonNode, Page page, ILogout iLogout, IPrEdit iPrEdit, IPrSendForApproval iPrSendForApproval, IPrApprove iPrApprove, IPrAssign iPrAssign, IPorCreate iPorCreate, IPorEdit iPorEdit, ICeCreate iCeCreate){
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogout = iLogout;
        this.iPrEdit = iPrEdit;
        this.iPrSendForApproval = iPrSendForApproval;
        this.iPrApprove = iPrApprove;
        this.iPrAssign = iPrAssign;
        this.iPorCreate = iPorCreate;
        this.iPorEdit = iPorEdit;
        this.iCeCreate = iCeCreate;
        this.logger = LoggerUtil.getLogger(PorSuspend.class);
        this.appUrl = jsonNode.get("configSettings").get("appUrl").asText();

    }

    public int suspendPorEdit(String type, String purchaseType){
        int status = 0;
        try {
            status = suspend(type, purchaseType);
            iPorEdit.porEdit(type, purchaseType);
        } catch (Exception exception) {
            logger.error("Exception in POR Suspend Edit function: {}", exception.getMessage());
        }
        return status;
    }

    public int suspendRfqOrPrEdit(String type, String purchaseType){
        int status = 0;
        try {
            status = suspend(type, purchaseType);
            if(purchaseType.equalsIgnoreCase("NonCatalog")) {
                iCeCreate.commercialEvaluationButton(type);
                iPorCreate.porCreateButtonForNonCatalog(type, purchaseType);
                iPorCreate.justification();
            } else {
                iPrEdit.edit(type, purchaseType);
                iPrSendForApproval.sendForApproval(type, purchaseType);
                iPrApprove.approve(type, purchaseType);
                iPrAssign.buyerManagerAssign(type, purchaseType);
                iPorCreate.porCreateButtonForCatalog(type, purchaseType);
            }
//            iPorCreate.taxCode();
            iPorCreate.porNotes();
            iPorCreate.createButton(type);
        } catch (Exception exception) {
            logger.error("Exception in POR Suspend RFQ or PR Edit function: {}", exception.getMessage());
        }
        return status;
    }

    public int suspend(String type, String purchaseType) {
        int status = 0;
        try {
            String buyerMailId = jsonNode.get("mailIds").get("buyerEmail").asText();

            iLogin.performLogin(buyerMailId);

            Locator porNavigationBarLocator = page.locator(POR_NAVIGATION_BAR);
            locatorVisibleHandler(porNavigationBarLocator);
            porNavigationBarLocator.click();

            String title = getTransactionTitle(type, purchaseType);
            Locator titleLocator = page.locator(getTitle(title));
            locatorVisibleHandler(titleLocator);
            titleLocator.first().click();

            Locator suspendButtonLocator = page.locator(SUSPEND_BUTTON);
            locatorVisibleHandler(suspendButtonLocator);
            suspendButtonLocator.click();

            Locator remarksLocator = page.locator(REMARKS_INPUT_LOCATOR);
            locatorVisibleHandler(remarksLocator);
            remarksLocator.fill("Suspended");

            Locator acceptLocator = page.locator(YES);
            locatorVisibleHandler(acceptLocator);

            String porType;
            if(type.equalsIgnoreCase("sales")){
                porType = "/api/PurchaseOrderRequestsSales/";
            } else if(type.equalsIgnoreCase("ps")){
                porType = "/api/PurchaseOrderRequests/";
            } else {
                porType = "/api/PurchaseOrderRequestsNonPOC/";
            }

            Response rejectResponse = page.waitForResponse(
                    response -> response.url().startsWith(appUrl + porType) && response.status() == 200,
                    acceptLocator::click
            );
            status = rejectResponse.status();

            PlaywrightFactory.attachScreenshotWithName("Purchase Order Request Suspend", page);

            iLogout.performLogout();
        } catch (Exception exception) {
            logger.error("Exception in POR Suspend function: {}", exception.getMessage());
        }
        return status;
    }
}
