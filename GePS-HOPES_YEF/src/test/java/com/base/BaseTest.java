package com.base;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;
import com.source.classes.currencyexchangerate.CurrencyExchangeRate;
import com.source.classes.dispatchnotes.DnMarkAsComplete.DnMarkAsCompleteTest;
import com.source.classes.dispatchnotes.assign.DnAssign;
import com.source.classes.dispatchnotes.assign.DnAssignTest;
import com.source.classes.dispatchnotes.cancel.DnCancel;
import com.source.classes.dispatchnotes.cancel.DnCancelTest;
import com.source.classes.dispatchnotes.create.DnCreate;
import com.source.classes.dispatchnotes.create.DnCreateTest;
import com.source.classes.dispatchnotes.dnreturn.DnReturn;
import com.source.classes.dispatchnotes.dnreturn.DnReturnTest;
import com.source.classes.dispatchnotes.edit.DnEdit;
import com.source.classes.dispatchnotes.edit.DnEditTest;
import com.source.classes.dispatchnotes.markAsComplete.DnMarkAsComplete;
import com.source.classes.freightforwarderrequests.invite.FfrInvite;
import com.source.classes.freightforwarderrequests.invite.FfrInviteTest;
import com.source.classes.freightforwarderrequests.quote.FfrQuote;
import com.source.classes.freightforwarderrequests.quote.FfrQuoteTest;
import com.source.classes.freightforwarderrequests.requote.FfrRequote;
import com.source.classes.freightforwarderrequests.requote.FfrRequoteTest;
import com.source.classes.inspections.assign.InsAssign;
import com.source.classes.inspections.assign.InsAssignTest;
import com.source.classes.inspections.create.InsCreate;
import com.source.classes.inspections.create.InsCreateTest;
import com.source.classes.inspections.fail.InsFail;
import com.source.classes.inspections.fail.InsFailTest;
import com.source.classes.inspections.readyforinspection.InsReadyForInspection;
import com.source.classes.inspections.readyforinspection.InsReadyForInspectionTest;
import com.source.classes.invoices.poinvoice.approve.InvApproval;
import com.source.classes.invoices.poinvoice.approve.PoInvApprovalTest;
import com.source.classes.invoices.poinvoice.cancel.InvCancel;
import com.source.classes.invoices.poinvoice.cancel.PoInvCancelTest;
import com.source.classes.invoices.poinvoice.checklist.InvChecklistAccept;
import com.source.classes.invoices.poinvoice.checklist.InvChecklistReject;
import com.source.classes.invoices.poinvoice.checklist.PoInvChecklistAcceptTest;
import com.source.classes.invoices.poinvoice.checklist.PoInvChecklistRejectTest;
import com.source.classes.invoices.poinvoice.create.InvCreate;
import com.source.classes.invoices.poinvoice.create.PoInvCreateTest;
import com.source.classes.invoices.poinvoice.edit.InvEdit;
import com.source.classes.invoices.poinvoice.edit.PoInvEditTest;
import com.source.classes.invoices.poinvoice.hold.InvHold;
import com.source.classes.invoices.poinvoice.hold.PoInvHoldTest;
import com.source.classes.invoices.poinvoice.invreturn.InvReturn;
import com.source.classes.invoices.poinvoice.invreturn.PoInvReturnTest;
import com.source.classes.invoices.poinvoice.reject.InvReject;
import com.source.classes.invoices.poinvoice.reject.PoInvRejectTest;
import com.source.classes.invoices.poinvoice.revert.InvRevert;
import com.source.classes.invoices.poinvoice.revert.PoInvRevertTest;
import com.source.classes.invoices.poinvoice.sendforapproval.InvSendForApproval;
import com.source.classes.invoices.poinvoice.sendforapproval.PoInvSendForApprovalTest;
import com.source.classes.invoices.poinvoice.verify.InvVerify;
import com.source.classes.invoices.poinvoice.verify.PoInvVerifyTest;
import com.source.classes.invoices.woinvoice.approve.WoInvApproval;
import com.source.classes.invoices.woinvoice.approve.WoInvApprovalTest;
import com.source.classes.invoices.woinvoice.cancel.WoInvCancel;
import com.source.classes.invoices.woinvoice.cancel.WoInvCancelTest;
import com.source.classes.invoices.woinvoice.checklist.WoInvChecklistAccept;
import com.source.classes.invoices.woinvoice.checklist.WoInvChecklistAcceptTest;
import com.source.classes.invoices.woinvoice.checklist.WoInvChecklistReject;
import com.source.classes.invoices.woinvoice.checklist.WoInvChecklistRejectTest;
import com.source.classes.invoices.woinvoice.create.WoInvCreate;
import com.source.classes.invoices.woinvoice.create.WoInvCreateTest;
import com.source.classes.invoices.woinvoice.edit.WoInvEdit;
import com.source.classes.invoices.woinvoice.edit.WoInvEditTest;
import com.source.classes.invoices.woinvoice.hold.WoInvHold;
import com.source.classes.invoices.woinvoice.hold.WoInvHoldTest;
import com.source.classes.invoices.woinvoice.invreturn.WoInvReturn;
import com.source.classes.invoices.woinvoice.invreturn.WoInvReturnTest;
import com.source.classes.invoices.woinvoice.reject.WoInvReject;
import com.source.classes.invoices.woinvoice.reject.WoInvRejectTest;
import com.source.classes.invoices.woinvoice.revert.WoInvRevert;
import com.source.classes.invoices.woinvoice.revert.WoInvRevertTest;
import com.source.classes.invoices.woinvoice.sendforapproval.WoInvSendForApproval;
import com.source.classes.invoices.woinvoice.sendforapproval.WoInvSendForApprovalTest;
import com.source.classes.invoices.woinvoice.verify.WoInvVerify;
import com.source.classes.invoices.woinvoice.verify.WoInvVerifyTest;
import com.source.classes.login.LoginTest;
import com.source.classes.orderschedules.approval.OsApproveTest;
import com.source.classes.orderschedules.approve.OsApprove;
import com.source.classes.orderschedules.create.OsCreate;
import com.source.classes.orderschedules.create.OsCreateTest;
import com.source.classes.orderschedules.edit.OsEdit;
import com.source.classes.orderschedules.edit.OsEditTest;
import com.source.classes.orderschedules.reject.OsReject;
import com.source.classes.orderschedules.reject.OsRejectTest;
import com.source.classes.purchaseorderrequests.approve.PorApprove;
import com.source.classes.purchaseorderrequests.approve.PorApproveTest;
import com.source.classes.purchaseorderrequests.create.PorCreate;
import com.source.classes.purchaseorderrequests.create.PorCreateTest;
import com.source.classes.purchaseorderrequests.edit.PorEdit;
import com.source.classes.purchaseorderrequests.edit.PorEditTest;
import com.source.classes.purchaseorderrequests.reject.PorReject;
import com.source.classes.purchaseorderrequests.reject.PorRejectTest;
import com.source.classes.purchaseorderrequests.revision.PorRevision;
import com.source.classes.purchaseorderrequests.revision.PorRevisionTest;
import com.source.classes.purchaseorderrequests.sendforapproval.PorSendForApproval;
import com.source.classes.purchaseorderrequests.sendforapproval.PorSendForApprovalTest;
import com.source.classes.purchaseorderrequests.suspend.PorSuspend;
import com.source.classes.purchaseorderrequests.suspend.SuspendEditTest;
import com.source.classes.purchaseorders.PoSendForVendorTest;
import com.source.classes.purchaseorders.SendForVendor;
import com.source.classes.requestforquotations.commercialevaluation.CommercialEvaluation;
import com.source.classes.requestforquotations.commercialevaluation.CommercialEvaluationTest;
import com.source.classes.requestforquotations.create.RfqCreate;
import com.source.classes.requestforquotations.create.RfqCreateTest;
import com.source.classes.requestforquotations.edit.RfqEdit;
import com.source.classes.requestforquotations.edit.RfqEditTest;
import com.source.classes.requestforquotations.quote.Quote;
import com.source.classes.requestforquotations.quote.QuoteTest;
import com.source.classes.requestforquotations.readyforevaluation.ReadyForEvaluation;
import com.source.classes.requestforquotations.readyforevaluation.ReadyForEvaluationTest;
import com.source.classes.requestforquotations.regret.QuotationRegret;
import com.source.classes.requestforquotations.regret.RegretTest;
import com.source.classes.requestforquotations.requote.Requote;
import com.source.classes.requestforquotations.requote.RequoteTest;
import com.source.classes.requestforquotations.suspend.RfqSuspend;
import com.source.classes.requestforquotations.suspend.RfqSuspendPrEditTest;
import com.source.classes.requestforquotations.suspend.RfqSuspendRfqEditTest;
import com.source.classes.requestforquotations.technicalevaluation.*;
import com.source.classes.requisitions.approve.ApproveTest;
import com.source.classes.requisitions.assign.AssignTest;
import com.source.classes.requisitions.create.CreateTest;
import com.source.classes.requisitions.edit.EditTest;
import com.source.classes.requisitions.reject.RejectTest;
import com.source.classes.requisitions.sendforapproval.SendForApprovalTest;
import com.source.classes.requisitions.suspend.BuyerManagerSuspendTest;
import com.source.classes.requisitions.suspend.BuyerSuspendTest;
import com.source.classes.requisitions.create.Create;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Page;
import com.source.classes.login.Login;
import com.source.classes.logout.Logout;
import com.source.classes.requisitions.approve.Approve;
import com.source.classes.requisitions.assign.Assign;
import com.source.classes.requisitions.edit.Edit;
import com.source.classes.requisitions.reject.Reject;
import com.source.classes.requisitions.sendforapproval.SendForApproval;
import com.source.classes.requisitions.suspend.BuyerManagerSuspend;
import com.source.classes.requisitions.suspend.BuyerSuspend;
import com.source.classes.requisitions.type.PurchaseRequisitionTypeHandler;
import com.source.classes.workorder.create.WoCreate;
import com.source.classes.workorder.edit.WoEdit;
import com.source.classes.workorder.okforinvoice.WoOkForInvoice;
import com.source.classes.workorder.trackerstatus.WoTrackerStatus;
import com.source.classes.workorders.create.WoCreateTest;
import com.source.classes.workorders.edit.WoEditTest;
import com.source.classes.workorders.okforinvoice.WoOkForInvoiceTest;
import com.source.classes.workorders.trackerstatus.WoTrackerStatusTest;
import com.source.interfaces.currencyexchangerate.ICurrencyExchangeRate;
import com.source.interfaces.dispatchnotes.*;
import com.source.interfaces.freightforwarderrequests.IFfrInvite;
import com.source.interfaces.freightforwarderrequests.IFfrQuote;
import com.source.interfaces.freightforwarderrequests.IFfrRequote;
import com.source.interfaces.inspections.IInsAssign;
import com.source.interfaces.inspections.IInsCreate;
import com.source.interfaces.inspections.IInsFail;
import com.source.interfaces.inspections.IInsReadyForInspection;
import com.source.interfaces.invoices.poinvoices.*;
import com.source.interfaces.invoices.woinvoices.*;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.orderschedules.IOsApprove;
import com.source.interfaces.orderschedules.IOsCreate;
import com.source.interfaces.orderschedules.IOsEdit;
import com.source.interfaces.orderschedules.IOsReject;
import com.source.interfaces.purchaseorderrequests.*;
import com.source.interfaces.purchaseorders.IPoSendForVendor;
import com.source.interfaces.requestforquotations.*;
import com.source.interfaces.requisitions.*;
import com.source.interfaces.workorders.IWoCreate;
import com.source.interfaces.workorders.IWoEdit;
import com.source.interfaces.workorders.IWoOkForInvoice;
import com.source.interfaces.workorders.IWoTrackerStatus;
import com.utils.*;
import com.utils.rpa.invoiceverification.IV_Flow;
import com.utils.rpa.orderacknowledgement.OA_Flow;
import com.utils.rpa.purchaseorderrequest.MSA_Flow;
import com.utils.rpa.salesordersync.PR_List_Flow;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {

    protected static Logger logger;
    protected ToastrUtil toastrUtil;
    protected static ObjectMapper objectMapper;
    protected static JsonNode jsonNode;
    protected GetTitleUtil getTitleUtil;
    protected SaveToTestDataJsonUtil saveToTestDataJsonUtil;
    protected GetInvoiceReferenceIdUtil getInvoiceReferenceIdUtil;
    protected LocatorHandlerUtil locatorHandlerUtil;
    protected static PlaywrightFactory playwrightFactory;
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;
    protected static Page page;
    protected ICurrencyExchangeRate iCurrencyExchangeRate;
    protected PR_List_Flow prListFlow;
    protected MSA_Flow msaFlow;
    protected OA_Flow oaFlow;
    protected IV_Flow ivFlow;
    protected LoginTest loginTest;
    protected ILogin iLogin;
    protected ILogout iLogout;
    protected IPrType iPrType;
    protected CreateTest createTest;
    protected IPrCreate iPrCreate;
    protected EditTest editTest;
    protected IPrEdit iPrEdit;
    protected SendForApprovalTest sendForApprovalTest;
    protected IPrSendForApproval iPrSendForApproval;
    protected RejectTest rejectTest;
    protected IPrReject iPrReject;
    protected ApproveTest approveTest;
    protected IPrApprove iPrApprove;
    protected AssignTest assignTest;
    protected IPrAssign iPrAssign;
    protected BuyerSuspendTest buyerSuspendTest;
    protected IPrBuyerSuspend iPrBuyerSuspend;
    protected BuyerManagerSuspendTest buyerManagerSuspendTest;
    protected IPrBuyerManagerSuspend iPrBuyerManagerSuspend;
    protected RfqCreateTest rfqCreateTest;
    protected IRfqCreate iRfqCreate;
    protected RfqEditTest rfqEditTest;
    protected IRfqEdit iRfqEdit;
    protected RfqSuspendPrEditTest rfqSuspendPrEditTest;
    protected RfqSuspendRfqEditTest rfqSuspendRfqEditTest;
    protected IRfqSuspend iRfqSuspend;
    protected RegretTest regretTest;
    protected IQuoRegret iQuoRegret;
    protected QuoteTest quoteTest;
    protected IQuoSubmit iQuoSubmit;
    protected RequoteTest requoteTest;
    protected IQuoRequote iQuoRequote;
    protected ReadyForEvaluationTest readyForEvaluationTest;
    protected IReadyForEvalutation iReadyForEvalutation;
    protected TechnicalEvaluationCreateTest technicalEvaluationCreateTest;
    protected ITeCreate iTeCreate;
    protected TechnicalEvaluationRejectTest technicalEvaluationRejectTest;
    protected ITeReject iTeReject;
    protected TechnicalEvaluationApproveTest technicalEvaluationApproveTest;
    protected ITeApprove iTeApprove;
    protected CommercialEvaluationTest commercialEvaluationTest;
    protected ICeCreate iCeCreate;
    protected PorCreateTest porCreateTest;
    protected IPorCreate iPorCreate;
    protected PorEditTest porEditTest;
    protected IPorEdit iPorEdit;
    protected SuspendEditTest suspendEditTest;
    protected IPorSuspend iPorSuspend;
    protected PorSendForApprovalTest porSendForApprovalTest;
    protected IPorSendForApproval iPorSendForApproval;
    protected PorRejectTest porRejectTest;
    protected IPorReject iPorReject;
    protected PorApproveTest porApproveTest;
    protected IPorApprove iPorApprove;
    protected PorRevisionTest porRevisionTest;
    protected IPorRevision iPorRevision;
    protected PoSendForVendorTest poSendForVendorTest;
    protected IPoSendForVendor iPoSendForVendor;
    protected OsCreateTest osCreateTest;
    protected IOsCreate iOsCreate;
    protected OsEditTest osEditTest;
    protected IOsEdit iOsEdit;
    protected OsRejectTest osRejectTest;
    protected IOsReject iOsReject;
    protected OsApproveTest osApproveTest;
    protected IOsApprove iOsApprove;
    protected InsReadyForInspectionTest insReadyForInspectionTest;
    protected IInsReadyForInspection iInsReadyForInspection;
    protected InsCreateTest insCreateTest;
    protected IInsCreate iInsCreate;
    protected InsAssignTest insAssignTest;
    protected IInsAssign iInsAssign;
    protected InsFailTest insFailTest;
    protected IInsFail iInsFail;
    protected DnCreateTest dnCreateTest;
    protected IDnCreate iDnCreate;
    protected DnReturnTest dnReturnTest;
    protected IDnReturn iDnReturn;
    protected DnEditTest dnEditTest;
    protected IDnEdit iDnEdit;
    protected DnAssignTest dnAssignTest;
    protected IDnAssign iDnAssign;
    protected DnCancelTest dnCancelTest;
    protected IDnCancel iDnCancel;
    protected IDnMarkAsComplete iDnMarkAsComplete;
    protected DnMarkAsCompleteTest dnMarkAsCompleteTest;
    protected FfrInviteTest ffrInviteTest;
    protected IFfrInvite iFfrInvite;
    protected FfrQuoteTest ffrQuoteTest;
    protected IFfrQuote iFfrQuote;
    protected FfrRequoteTest ffrRequoteTest;
    protected IFfrRequote iFfrRequote;
    protected WoCreateTest woCreateTest;
    protected IWoCreate iWoCreate;
    protected WoEditTest woEditTest;
    protected IWoEdit iWoEdit;
    protected WoTrackerStatusTest woTrackerStatusTest;
    protected IWoTrackerStatus iWoTrackerStatus;
    protected WoOkForInvoiceTest woOkForInvoiceTest;
    protected IWoOkForInvoice iWoOkForInvoice;
    protected PoInvCreateTest poInvCreateTest;
    protected IInvCreate iInvCreate;
    protected PoInvHoldTest poInvHoldTest;
    protected IInvHold iInvHold;
    protected PoInvRevertTest poInvRevertTest;
    protected IInvRevert iInvRevert;
    protected PoInvCancelTest poInvCancelTest;
    protected IInvCancel iInvCancel;
    protected PoInvSendForApprovalTest poInvSendForApprovalTest;
    protected IInvSendForApproval iInvSendForApproval;
    protected PoInvChecklistRejectTest poInvChecklistRejectTest;
    protected IInvChecklistReject iInvChecklistReject;
    protected PoInvChecklistAcceptTest poInvChecklistAcceptTest;
    protected IInvChecklistAccept iInvChecklistAccept;
    protected PoInvEditTest poInvEditTest;
    protected IInvEdit iInvEdit;
    protected PoInvReturnTest poInvReturnTest;
    protected IInvReturn iInvReturn;
    protected PoInvVerifyTest poInvVerifyTest;
    protected IInvVerify iInvVerify;
    protected PoInvRejectTest poInvRejectTest;
    protected IInvReject iInvReject;
    protected PoInvApprovalTest poInvApprovalTest;
    protected IInvApproval iInvApproval;
    protected WoInvCreateTest woInvCreateTest;
    protected IWoInvCreate iWoInvCreate;
    protected WoInvHoldTest woInvHoldTest;
    protected IWoInvHold iWoInvHold;
    protected WoInvRevertTest woInvRevertTest;
    protected IWoInvRevert iWoInvRevert;
    protected WoInvCancelTest woInvCancelTest;
    protected IWoInvCancel iWoInvCancel;
    protected WoInvSendForApprovalTest woInvSendForApprovalTest;
    protected IWoInvSendForApproval iWoInvSendForApproval;
    protected WoInvChecklistAcceptTest woInvChecklistAcceptTest;
    protected IWoInvChecklistAccept iWoInvChecklistAccept;
    protected WoInvChecklistRejectTest woInvChecklistRejectTest;
    protected IWoInvChecklistReject iWoInvChecklistReject;
    protected WoInvEditTest woInvEditTest;
    protected IWoInvEdit iWoInvEdit;
    protected WoInvReturnTest woInvReturnTest;
    protected IWoInvReturn iWoInvReturn;
    protected WoInvVerifyTest woInvVerifyTest;
    protected IWoInvVerify iWoInvVerify;
    protected WoInvRejectTest woInvRejectTest;
    protected IWoInvReject iWoInvReject;
    protected WoInvApprovalTest woInvApprovalTest;
    protected IWoInvApproval iWoInvApproval;
    protected String traceFileName;

//TODO Constructor
    public BaseTest() {
    }

    @BeforeSuite
    public void globalSetup(){
        try {
            logger = LoggerUtil.getLogger(BaseTest.class);
            objectMapper = new ObjectMapper();
            jsonNode = objectMapper.readTree(new File("src/test/resources/config/test-data.json"));

            String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
            traceFileName = "GePS-HOPES_YEF-Trace_Suite_" + timestamp + ".zip";

            playwrightFactory = new PlaywrightFactory(objectMapper, jsonNode);
            playwrightFactory.setPlaywright();
            playwright = playwrightFactory.getPlaywright();
            playwrightFactory.initializeBrowser(jsonNode);
            browser = playwrightFactory.getBrowser();
            playwrightFactory.initializeBrowserContext();
            browserContext = playwrightFactory.getBrowserContext();
            page = playwrightFactory.initializePage(jsonNode);
            playwrightFactory.startTracing(browserContext, traceFileName);
            getTitleUtil = new GetTitleUtil(jsonNode, logger);
            saveToTestDataJsonUtil = new SaveToTestDataJsonUtil(playwrightFactory, objectMapper, jsonNode);
            locatorHandlerUtil = new LocatorHandlerUtil(logger);
        } catch (Exception exception) {
            logger.error("Error Initializing Global Setup Function: {}", exception.getMessage());
        }
    }

    @BeforeClass
    public void setUp(){
        try {
            prListFlow = new PR_List_Flow(page);
            msaFlow = new MSA_Flow(page);
            oaFlow = new OA_Flow(page);
            ivFlow = new IV_Flow(page);

//TODO Requisition
            iLogin = new Login(jsonNode, page);
            iLogout = new Logout(page);
            loginTest = new LoginTest();
            iPrCreate = new Create(playwrightFactory, objectMapper, iLogin, jsonNode, page, iLogout);
            iPrType = new PurchaseRequisitionTypeHandler(iPrCreate, prListFlow);
            createTest = new CreateTest();
            iPrSendForApproval = new SendForApproval(playwrightFactory, objectMapper, iLogin, jsonNode, page, iLogout);
            sendForApprovalTest = new SendForApprovalTest();
            iPrApprove = new Approve(objectMapper, iLogin, jsonNode, page, iLogout, playwrightFactory);
            approveTest = new ApproveTest();
            iPrAssign = new Assign(playwrightFactory, iLogin, jsonNode, page, iLogout, objectMapper);
            assignTest = new AssignTest();
            iPrEdit = new Edit(iLogin, jsonNode, page, iLogout);
            editTest = new EditTest();
            iPrReject = new Reject(iLogin, jsonNode, page, iLogout);
            rejectTest = new RejectTest();
            iPrBuyerManagerSuspend = new BuyerManagerSuspend(iLogin, jsonNode, page, iLogout, iPrEdit);
            buyerManagerSuspendTest = new BuyerManagerSuspendTest();
            iPrBuyerSuspend = new BuyerSuspend(iLogin, jsonNode, page, iLogout, objectMapper);
            buyerSuspendTest = new BuyerSuspendTest();

//TODO Request For Quotation
            iRfqCreate = new RfqCreate(iLogin, jsonNode, page, iLogout, playwrightFactory, objectMapper);
            rfqCreateTest = new RfqCreateTest();
            iRfqEdit = new RfqEdit(iLogin, jsonNode, page, iLogout);
            rfqEditTest = new RfqEditTest();
            iRfqSuspend = new RfqSuspend(iLogin, jsonNode, page, iLogout, iRfqEdit, iPrEdit, iPrSendForApproval, iPrApprove, iPrAssign, iRfqCreate);
            rfqSuspendPrEditTest = new RfqSuspendPrEditTest();
            rfqSuspendRfqEditTest = new RfqSuspendRfqEditTest();
            iQuoSubmit = new Quote(iLogin, jsonNode, page, iLogout);
            quoteTest = new QuoteTest();
            iQuoRegret = new QuotationRegret(iQuoSubmit, iLogin, jsonNode, page, iLogout);
            regretTest = new RegretTest();
            iQuoRequote = new Requote(iLogin, jsonNode, page, iLogout);
            requoteTest = new RequoteTest();
            iReadyForEvalutation = new ReadyForEvaluation(iLogin, jsonNode, page, iLogout, playwrightFactory, objectMapper);
            readyForEvaluationTest = new ReadyForEvaluationTest();
            iTeCreate = new TechnicalEvaluationCreate(iLogin, jsonNode, page, iLogout);
            technicalEvaluationCreateTest = new TechnicalEvaluationCreateTest();
            iTeReject = new TechnicalEvaluationReject(iLogin, jsonNode, page, iLogout, iTeCreate);
            technicalEvaluationRejectTest = new TechnicalEvaluationRejectTest();
            iTeApprove = new TechnicalEvaluationApprove(iLogin, jsonNode, page, iLogout, iTeCreate);
            technicalEvaluationApproveTest = new TechnicalEvaluationApproveTest();
            iCeCreate = new CommercialEvaluation(iLogin, jsonNode, page, iLogout);
            commercialEvaluationTest = new CommercialEvaluationTest();

//TODO Purchase Order Request
            iPorCreate = new PorCreate(iLogin, jsonNode, page, iLogout, playwrightFactory, objectMapper, prListFlow);
            porCreateTest = new PorCreateTest();
            iPorEdit = new PorEdit(iLogin, jsonNode, page, iLogout);
            porEditTest = new PorEditTest();
            iPorSuspend = new PorSuspend(iLogin, jsonNode, page, iLogout, iPrEdit, iPrSendForApproval, iPrApprove, iPrAssign, iPorCreate, iPorEdit, iCeCreate);
            suspendEditTest = new SuspendEditTest();
            iPorSendForApproval = new PorSendForApproval(iLogin, jsonNode, page, iLogout, objectMapper, playwrightFactory);
            porSendForApprovalTest = new PorSendForApprovalTest();
            iPorApprove = new PorApprove(iLogin, jsonNode, page, iLogout, playwrightFactory, objectMapper, iPorSendForApproval, msaFlow);
            porApproveTest = new PorApproveTest();
            iPorReject = new PorReject(iLogin, jsonNode, page, iLogout, iPorEdit, iPorSendForApproval);
            porRejectTest = new PorRejectTest();

//TODO Purchase Order
            iPoSendForVendor = new SendForVendor(iLogin, jsonNode, page, iLogout, playwrightFactory, objectMapper);
            poSendForVendorTest = new PoSendForVendorTest();

//TODO Purchase Order Request Revision
            iPorRevision = new PorRevision(iLogin, jsonNode, page, iLogout, iPorSendForApproval, iPorApprove, playwrightFactory, objectMapper);
            porRevisionTest = new PorRevisionTest();

//TODO Order Schedule
            iOsCreate = new OsCreate(iLogin, jsonNode, page, iLogout, playwrightFactory);
            osCreateTest = new OsCreateTest();
            iOsEdit = new OsEdit(iLogin, jsonNode, page, iLogout);
            osEditTest = new OsEditTest();
            iOsReject = new OsReject(iLogin, jsonNode, page, iLogout);
            osRejectTest = new OsRejectTest();
            iOsApprove = new OsApprove(iLogin, jsonNode, page, iLogout, objectMapper, oaFlow, playwrightFactory);
            osApproveTest = new OsApproveTest();

//TODO Inspection
            iInsReadyForInspection = new InsReadyForInspection(iLogin, jsonNode, page, iLogout);
            insReadyForInspectionTest = new InsReadyForInspectionTest();
            iInsCreate = new InsCreate(iLogin, jsonNode, page, iLogout);
            insCreateTest = new InsCreateTest();
            iInsAssign = new InsAssign(iLogin, jsonNode, page, iLogout);
            insAssignTest = new InsAssignTest();
            iInsFail = new InsFail(iLogin, jsonNode, page, iLogout, iInsReadyForInspection);
            insFailTest = new InsFailTest();

//TODO Dispatch Note
            iDnCreate = new DnCreate(iLogin, jsonNode, page, iLogout);
            dnCreateTest = new DnCreateTest();
            iDnEdit = new DnEdit(iLogin, jsonNode, page, iLogout);
            dnEditTest = new DnEditTest();
            iDnReturn = new DnReturn(iLogin, jsonNode, page, iLogout, iDnEdit);
            dnReturnTest = new DnReturnTest();
            iDnCancel = new DnCancel(iLogin, jsonNode, page, iLogout, iDnCreate);
            dnCancelTest = new DnCancelTest();
            iDnAssign = new DnAssign(iLogin, jsonNode, page, iLogout, playwrightFactory);
            dnAssignTest = new DnAssignTest();
            iDnMarkAsComplete = new DnMarkAsComplete(iLogin, jsonNode, page, iLogout, iDnCreate);
            dnMarkAsCompleteTest = new DnMarkAsCompleteTest();

//TODO Freight Forwarder Request
            iFfrInvite = new FfrInvite(iLogin, jsonNode, page, iLogout, iDnAssign);
            ffrInviteTest = new FfrInviteTest();
            iFfrQuote = new FfrQuote(iLogin, jsonNode, page, iLogout);
            ffrQuoteTest = new FfrQuoteTest();
            iFfrRequote = new FfrRequote(iLogin, jsonNode, iFfrQuote, iLogout, page);
            ffrRequoteTest = new FfrRequoteTest();

//TODO Work Order
            iWoCreate = new WoCreate(iLogin, jsonNode, page, iLogout);
            woCreateTest = new WoCreateTest();
            iWoEdit = new WoEdit(iLogin, jsonNode, page, iLogout);
            woEditTest = new WoEditTest();
            iWoTrackerStatus = new WoTrackerStatus(iLogin, jsonNode, page, iLogout, playwrightFactory);
            woTrackerStatusTest = new WoTrackerStatusTest();
            iWoOkForInvoice = new WoOkForInvoice(iLogin, jsonNode, page, iLogout, playwrightFactory);
            woOkForInvoiceTest = new WoOkForInvoiceTest();

//TODO Currency Exchange Rate
            iCurrencyExchangeRate = new CurrencyExchangeRate(playwrightFactory, iLogin, jsonNode, iLogout);

//TODO Purchase Order Invoice
            iInvCreate = new InvCreate(playwrightFactory, iLogin, jsonNode, page, iLogout, iCurrencyExchangeRate);
            poInvCreateTest = new PoInvCreateTest();
            iInvCancel = new InvCancel(iLogin, jsonNode, page, iLogout, iInvCreate);
            poInvCancelTest = new PoInvCancelTest();
            iInvHold = new InvHold(iLogin, jsonNode, page, iLogout);
            poInvHoldTest = new PoInvHoldTest();
            iInvRevert = new InvRevert(iLogin, jsonNode, page, iLogout);
            poInvRevertTest = new PoInvRevertTest();
            iInvChecklistAccept = new InvChecklistAccept(iLogin, jsonNode, page, iLogout);
            poInvChecklistAcceptTest = new PoInvChecklistAcceptTest();
            iInvChecklistReject = new InvChecklistReject(iLogin, jsonNode, page, iLogout);
            poInvChecklistRejectTest = new PoInvChecklistRejectTest();
            iInvSendForApproval = new InvSendForApproval(iLogin, jsonNode, page, iLogout, objectMapper);
            poInvSendForApprovalTest = new PoInvSendForApprovalTest();
            iInvReturn = new InvReturn(iLogin, jsonNode, page, iLogout);
            poInvReturnTest = new PoInvReturnTest();
            iInvVerify = new InvVerify(iLogin, jsonNode, page, iLogout);
            poInvVerifyTest = new PoInvVerifyTest();
            iInvEdit = new InvEdit(iLogin, jsonNode, page, iLogout);
            poInvEditTest = new PoInvEditTest();
            iInvReject = new InvReject(iLogin, jsonNode, page, iLogout, iInvSendForApproval, iInvVerify);
            poInvRejectTest = new PoInvRejectTest();
            iInvApproval = new InvApproval(iLogin, jsonNode, page, iLogout, objectMapper, playwrightFactory, ivFlow);
            poInvApprovalTest = new PoInvApprovalTest();

//TODO Work Order Invoice
            iWoInvCreate = new WoInvCreate(playwrightFactory, iLogin, jsonNode, page, iLogout, iCurrencyExchangeRate);
            woInvCreateTest = new WoInvCreateTest();
            iWoInvCancel = new WoInvCancel(iLogin, jsonNode, page, iLogout, iWoInvCreate);
            woInvCancelTest = new WoInvCancelTest();
            iWoInvHold = new WoInvHold(iLogin, jsonNode, page, iLogout);
            woInvHoldTest = new WoInvHoldTest();
            iWoInvRevert = new WoInvRevert(iLogin, jsonNode, page, iLogout);
            woInvRevertTest = new WoInvRevertTest();
            iWoInvChecklistAccept = new WoInvChecklistAccept(iLogin, jsonNode, page, iLogout);
            woInvChecklistAcceptTest = new WoInvChecklistAcceptTest();
            iWoInvChecklistReject = new WoInvChecklistReject(iLogin, jsonNode, page, iLogout);
            woInvChecklistRejectTest = new WoInvChecklistRejectTest();
            iWoInvSendForApproval = new WoInvSendForApproval(iLogin, jsonNode, page, iLogout);
            woInvSendForApprovalTest = new WoInvSendForApprovalTest();
            iWoInvReturn = new WoInvReturn(iLogin, jsonNode, page, iLogout, iWoInvSendForApproval);
            woInvReturnTest = new WoInvReturnTest();
            iWoInvVerify = new WoInvVerify(iLogin, jsonNode, page, iLogout);
            woInvVerifyTest = new WoInvVerifyTest();
            iWoInvEdit = new WoInvEdit(iLogin, jsonNode, page, iLogout);
            woInvEditTest = new WoInvEditTest();
            iWoInvReject = new WoInvReject(iLogin, jsonNode, page, iLogout, iWoInvSendForApproval, iWoInvVerify);
            woInvRejectTest = new WoInvRejectTest();
            iWoInvApproval = new WoInvApproval(iLogin, jsonNode, page, iLogout, objectMapper, playwrightFactory, ivFlow);
            woInvApprovalTest = new WoInvApprovalTest();

            getInvoiceReferenceIdUtil = new GetInvoiceReferenceIdUtil(playwright, playwrightFactory, page, iLogin, jsonNode, objectMapper, iLogout);

        } catch (Exception exception) {
            logger.error("Error Initializing SetUp Function: {}", exception.getMessage());
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