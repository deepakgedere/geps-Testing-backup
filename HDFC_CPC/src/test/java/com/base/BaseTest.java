package com.base;
import com.classes.bms.purchaseOrders.accept.BmsPOAcceptTest;
import com.classes.bms.purchaseOrders.ackreject.BmsAckRejectTest;
import com.classes.bms.purchaseOrders.amend.BmsPOAmendTest;
import com.classes.bms.purchaseOrders.approve.BmsPOApproveTest;
import com.classes.bms.purchaseOrders.create.BmsPoCreateTest;
import com.classes.bms.purchaseOrders.edit.BmsPOEditTest;
import com.classes.bms.purchaseOrders.reject.BmsPORejectTest;
import com.classes.bms.requisitions.approve.BmsApproveTest;
import com.classes.bms.requisitions.create.BmsCreateTest;
import com.classes.bms.requisitions.edit.BmsEditTest;
import com.classes.bms.requisitions.reject.BmsRejectTest;
import com.classes.bms.requisitions.selectVendor.BmsSelectVendorTest;
import com.classes.infra.billofquantity.approve.BOQApproveTest;
import com.classes.infra.billofquantity.create.BOQCreateTest;
import com.classes.infra.billofquantity.reject.BOQRejectTest;
import com.classes.infra.purchaseorder.accept.POAcceptTest;
import com.classes.infra.purchaseorder.ackreject.AckRejectTest;
import com.classes.infra.purchaseorder.amend.POAmendTest;
import com.classes.infra.purchaseorder.approve.POApproveTest;
import com.classes.infra.purchaseorder.edit.POEditTest;
import com.classes.infra.purchaseorder.reject.PORejectTest;
import com.classes.infra.requisitions.approve.ApproveTest;
import com.classes.infra.requisitions.create.CreateTest;
import com.classes.infra.requisitions.edit.EditTest;
import com.classes.infra.requisitions.reject.RejectTest;
import com.source.classes.bms.purchaseOrders.accept.BmsPOAccept;
import com.source.classes.bms.purchaseOrders.ackreject.BmsAckReject;
import com.source.classes.bms.purchaseOrders.amend.BmsPOAmend;
import com.source.classes.bms.purchaseOrders.approve.BmsPOApprove;
import com.source.classes.bms.purchaseOrders.create.BmsPoCreate;
import com.source.classes.bms.purchaseOrders.edit.BmsPOEdit;
import com.source.classes.bms.purchaseOrders.reject.BmsPOReject;
import com.source.classes.bms.requisitions.approve.BmsApprove;
import com.source.classes.bms.requisitions.create.BmsCreate;
import com.source.classes.bms.requisitions.edit.BmsEdit;
import com.source.classes.bms.requisitions.reject.BmsReject;
import com.source.classes.bms.requisitions.selectVendor.BmsSelectVendor;
import com.source.classes.infra.billofquantity.approve.BOQApprove;
import com.source.classes.infra.billofquantity.create.BOQCreate;
import com.source.classes.infra.billofquantity.reject.BOQReject;
import com.source.classes.infra.purchaseorder.accept.POAccept;
import com.source.classes.infra.purchaseorder.ackreject.AckReject;
import com.source.classes.infra.purchaseorder.amend.POAmend;
import com.source.classes.infra.purchaseorder.approve.POApprove;
import com.source.classes.infra.purchaseorder.edit.POEdit;
import com.source.classes.infra.purchaseorder.reject.POReject;
import com.source.classes.infra.requisitions.approve.Approve;
import com.source.classes.infra.requisitions.create.Create;
import com.source.classes.infra.requisitions.edit.Edit;
import com.source.classes.infra.requisitions.reject.Reject;
import com.source.classes.login.Login;
import com.classes.login.LoginTest;
import com.source.classes.logout.Logout;
import com.classes.logout.LogoutTest;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.source.interfaces.bms.purchaseOrders.accept.IBmsPoAccept;
import com.source.interfaces.bms.purchaseOrders.ackreject.IBmsAckReject;
import com.source.interfaces.bms.purchaseOrders.amend.IBmsPoAmend;
import com.source.interfaces.bms.purchaseOrders.approve.IBmsPoApprove;
import com.source.interfaces.bms.purchaseOrders.create.IBmsPoCreate;
import com.source.interfaces.bms.purchaseOrders.edit.IBmsPoEdit;
import com.source.interfaces.bms.purchaseOrders.reject.IBmsPoReject;
import com.source.interfaces.bms.requisitions.approve.IBmsPrApprove;
import com.source.interfaces.bms.requisitions.create.IBmsPrCreate;
import com.source.interfaces.bms.requisitions.edit.IBmsPrEdit;
import com.source.interfaces.bms.requisitions.reject.IBmsPrReject;
import com.source.interfaces.bms.requisitions.selectVendor.IBmsSelectVendor;
import com.source.interfaces.infra.billofquantity.approve.IBoqApprove;
import com.source.interfaces.infra.billofquantity.create.IBoqCreate;
import com.source.interfaces.infra.billofquantity.reject.IBoqReject;
import com.source.interfaces.infra.purchaseorder.accept.IPoAccept;
import com.source.interfaces.infra.purchaseorder.ackreject.IAckReject;
import com.source.interfaces.infra.purchaseorder.amend.IPoAmend;
import com.source.interfaces.infra.purchaseorder.approve.IPoApprove;
import com.source.interfaces.infra.purchaseorder.edit.IPoEdit;
import com.source.interfaces.infra.purchaseorder.reject.IPoReject;
import com.source.interfaces.infra.requisitions.approve.IPrApprove;
import com.source.interfaces.infra.requisitions.create.IPrCreate;
import com.source.interfaces.infra.requisitions.edit.IPrEdit;
import com.source.interfaces.infra.requisitions.reject.IPrReject;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.utils.*;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {

    protected static Logger logger;
    protected static SaveToJsonUtil saveToJsonUtil;
    protected static ObjectMapper objectMapper;
    protected static JsonNode jsonNode;
    protected static PlaywrightFactory playwrightFactory;
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;
    protected static Page page;
    protected String traceFileName;
    protected ILogin iLogin;
    protected LoginTest loginTest;
    protected ILogout iLogout;
    protected LogoutTest logoutTest;
    protected IPrCreate iPrCreate;
    protected IPrEdit iPrEdit;
    protected IPrReject iPrReject;
    protected IPrApprove iPrApprove;
    protected CreateTest createTest;
    protected EditTest editTest;
    protected RejectTest rejectTest;
    protected ApproveTest approveTest;
    protected IBoqCreate iBoqCreate;
    protected IBoqReject iBoqReject;
    protected IBoqApprove iBoqApprove;
    protected BOQCreateTest boqCreateTest;
    protected BOQRejectTest boqRejectTest;
    protected BOQApproveTest boqApproveTest;
    protected IPoEdit iPoEdit;
    protected POEditTest poEditTest;
    protected IPoReject iPoReject;
    protected PORejectTest poRejectTest;
    protected IPoApprove iPoApprove;
    protected POApproveTest poApproveTest;
    protected IPoAmend iPoAmmend;
    protected POAmendTest poAmmendTest;
    protected POAcceptTest poAcceptTest;
    protected IPoAccept iPoAccept;
    protected AckRejectTest ackRejectTest;
    protected IAckReject iAckReject;

    protected IBmsPrCreate iBmsPrCreate;
    protected BmsCreateTest bmsCreateTest;
    protected IBmsPrReject iBmsPrReject;
    protected BmsRejectTest bmsRejectTest;
    protected BmsEditTest bmsEditTest;
    protected IBmsPrEdit iBmsPrEdit;
    protected BmsSelectVendorTest bmsSelectVendorTest;
    protected IBmsSelectVendor iBmsSelectVendor;
    protected BmsApproveTest bmsApproveTest;
    protected IBmsPrApprove iBmsPrApprove;
    protected IBmsPoCreate iBmsPoCreate;
    protected BmsPoCreateTest bmsPoCreateTest;
    protected BmsPORejectTest bmsPORejectTest;
    protected IBmsPoReject iBmsPoReject;
    protected BmsPOEditTest bmsPOEditTest;
    protected IBmsPoEdit iBmsPoEdit;
    protected BmsPOApproveTest bmsPOApproveTest;
    protected IBmsPoApprove iBmsPoApprove;
    protected IBmsAckReject iBmsAckReject;
    protected BmsAckRejectTest bmsAckRejectTest;
    protected IBmsPoAmend iBmsPoAmmend;
    protected BmsPOAmendTest bmsPOAmendTest;
    protected BmsPOAcceptTest bmsPOAcceptTest;
    protected IBmsPoAccept iBmsPoAccept;

//TODO Constructor
    public BaseTest() {
    }

    @BeforeSuite
    public void globalSetup(){
        try {
            logger = LoggerUtil.getLogger(BaseTest.class);
            objectMapper = new ObjectMapper();
            jsonNode = objectMapper.readTree(new File("src/test/resources/config/test-data.json"));
            saveToJsonUtil = new SaveToJsonUtil(jsonNode);

            String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
            traceFileName = "HDFC_CPC-Trace_Suite_" + timestamp + ".zip";

            playwrightFactory = new PlaywrightFactory(objectMapper, jsonNode);
            playwrightFactory.setPlaywright();
            playwright = playwrightFactory.getPlaywright();
            playwrightFactory.initializeBrowser(jsonNode);
            browser = playwrightFactory.getBrowser();
            playwrightFactory.initializeBrowserContext();
            browserContext = playwrightFactory.getBrowserContext();
            page = playwrightFactory.initializePage(jsonNode);
            playwrightFactory.startTracing(browserContext, traceFileName);
        } catch (Exception exception) {
            logger.error("Error Initializing Global Setup Function: {}", exception.getMessage());
        }
    }

    @BeforeClass
    public void setUp(){
        try {
            iLogin = new Login(jsonNode, page);
            loginTest = new LoginTest();
            iLogout = new Logout(page);
            logoutTest = new LogoutTest();

//TODO Purchase Requisition
            createTest = new CreateTest();
            iPrCreate = new Create(jsonNode, page, iLogin, iLogout, objectMapper);
            bmsCreateTest = new BmsCreateTest();
            iBmsPrCreate = new BmsCreate(jsonNode, page, iLogin, iLogout, objectMapper);
            bmsRejectTest = new BmsRejectTest();
            iBmsPrReject = new BmsReject(jsonNode, page, iLogin, iLogout);
            editTest = new EditTest();
            iPrEdit = new Edit(jsonNode, page, iLogin, iLogout, iPrCreate);
            bmsEditTest = new BmsEditTest();
            iBmsPrEdit = new BmsEdit(jsonNode, page, iLogin, iLogout, iBmsPrCreate);
            rejectTest = new RejectTest();
            iPrReject = new Reject(jsonNode, page, iLogin, iLogout);
            approveTest = new ApproveTest();
            iPrApprove = new Approve(jsonNode, page, iLogin, iLogout);
            iBmsPrApprove = new BmsApprove(jsonNode, page, iLogin, iLogout);
            bmsApproveTest = new BmsApproveTest();

//TODO Bill Of Quantity
            boqCreateTest = new BOQCreateTest();
            iBoqCreate = new BOQCreate(jsonNode, page, iLogin, iLogout);
            boqRejectTest = new BOQRejectTest();
            iBoqReject = new BOQReject(jsonNode, page, iLogin, iLogout, iPrReject, iBoqCreate);
            boqApproveTest = new BOQApproveTest();
            iBoqApprove = new BOQApprove(jsonNode, page, iLogin, iLogout);
            bmsSelectVendorTest = new BmsSelectVendorTest();
            iBmsSelectVendor = new BmsSelectVendor(jsonNode, page, iLogin, iLogout);

//TODO Purchase Orders
            bmsPoCreateTest = new BmsPoCreateTest();
            iBmsPoCreate = new BmsPoCreate(jsonNode, page, iLogin, iLogout);
            bmsPORejectTest = new BmsPORejectTest();
            iBmsPoReject = new BmsPOReject(jsonNode, page, iLogin, iLogout);
            bmsPOEditTest = new BmsPOEditTest();
            iBmsPoEdit = new BmsPOEdit(jsonNode, page, iLogin, iLogout);
            poEditTest = new POEditTest();
            iPoEdit = new POEdit(jsonNode, page, iLogin, iLogout);
            poRejectTest = new PORejectTest();
            iPoReject = new POReject(jsonNode, page, iLogin, iLogout);
            poApproveTest = new POApproveTest();
            iPoApprove = new POApprove(jsonNode, page, iLogin, iLogout);
            bmsPOApproveTest = new BmsPOApproveTest();
            iBmsPoApprove = new BmsPOApprove(jsonNode, page, iLogin, iLogout);
            poAmmendTest = new POAmendTest();
            iPoAmmend = new POAmend(jsonNode, page, iLogin, iLogout, iPrEdit, iPrApprove, iBoqCreate, iBoqApprove, iPoApprove);
            bmsPOAmendTest = new BmsPOAmendTest();
            iBmsPoAmmend = new BmsPOAmend(jsonNode, page, iLogin, iLogout, iBmsPrEdit, iBmsSelectVendor, iBmsPrApprove, iBmsPoApprove);
            poAcceptTest = new POAcceptTest();
            iPoAccept = new POAccept(jsonNode, page, iLogin, iLogout);
            bmsPOAcceptTest = new BmsPOAcceptTest();
            iBmsPoAccept = new BmsPOAccept(jsonNode, page, iLogin, iLogout);
            ackRejectTest = new AckRejectTest();
            iAckReject = new AckReject(jsonNode, page, iLogin, iLogout);
            bmsAckRejectTest = new BmsAckRejectTest();
            iBmsAckReject = new BmsAckReject(jsonNode, page, iLogin, iLogout);
        } catch (Exception exception) {
            logger.error("Exception in BaseMain Constructor: {}", exception.getMessage());
        }
    }

    @AfterSuite
    public void globalTearDown() {
        try {
            playwrightFactory.stopTracing(browserContext, traceFileName);
            browserContext.browser().close();
        } catch (Exception exception) {
            System.out.println("Error Initializing Global Tear Down Function: " + exception.getMessage());
        }
    }
}