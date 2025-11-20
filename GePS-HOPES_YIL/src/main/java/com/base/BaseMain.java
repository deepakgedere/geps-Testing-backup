package com.base;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.source.classes.currencyexchangerate.CurrencyExchangeRate;
import com.source.classes.dispatchnotes.assign.DnAssign;
import com.source.classes.dispatchnotes.cancel.DnCancel;
import com.source.classes.dispatchnotes.create.DnCreate;
import com.source.classes.dispatchnotes.dnreturn.DnReturn;
import com.source.classes.dispatchnotes.edit.DnEdit;
import com.source.classes.freightforwarderrequests.invite.FfrInvite;
import com.source.classes.freightforwarderrequests.quote.FfrQuote;
import com.source.classes.freightforwarderrequests.requote.FfrRequote;
import com.source.classes.inspections.assign.InsAssign;
import com.source.classes.inspections.create.InsCreate;
import com.source.classes.inspections.fail.InsFail;
import com.source.classes.inspections.readyforinspection.InsReadyForInspection;
import com.source.classes.invoices.poinvoice.approve.InvApproval;
import com.source.classes.invoices.poinvoice.cancel.InvCancel;
import com.source.classes.invoices.poinvoice.checklist.InvChecklistAccept;
import com.source.classes.invoices.poinvoice.checklist.InvChecklistReject;
import com.source.classes.invoices.poinvoice.create.InvCreate;
import com.source.classes.invoices.poinvoice.edit.InvEdit;
import com.source.classes.invoices.poinvoice.hold.InvHold;
import com.source.classes.invoices.poinvoice.invreturn.InvReturn;
import com.source.classes.invoices.poinvoice.reject.InvReject;
import com.source.classes.invoices.poinvoice.revert.InvRevert;
import com.source.classes.invoices.poinvoice.sendforapproval.InvSendForApproval;
import com.source.classes.invoices.poinvoice.verify.InvVerify;
import com.source.classes.invoices.woinvoice.approve.WoInvApproval;
import com.source.classes.invoices.woinvoice.cancel.WoInvCancel;
import com.source.classes.invoices.woinvoice.checklist.WoInvChecklistAccept;
import com.source.classes.invoices.woinvoice.checklist.WoInvChecklistReject;
import com.source.classes.invoices.woinvoice.create.WoInvCreate;
import com.source.classes.invoices.woinvoice.edit.WoInvEdit;
import com.source.classes.invoices.woinvoice.hold.WoInvHold;
import com.source.classes.invoices.woinvoice.invreturn.WoInvReturn;
import com.source.classes.invoices.woinvoice.reject.WoInvReject;
import com.source.classes.invoices.woinvoice.revert.WoInvRevert;
import com.source.classes.invoices.woinvoice.sendforapproval.WoInvSendForApproval;
import com.source.classes.invoices.woinvoice.verify.WoInvVerify;
import com.source.classes.login.Login;
import com.source.classes.logout.Logout;
import com.source.classes.orderschedules.approve.OsApprove;
import com.source.classes.orderschedules.create.OsCreate;
import com.source.classes.orderschedules.edit.OsEdit;
import com.source.classes.orderschedules.reject.OsReject;
import com.source.classes.purchaseorderrequests.create.PorCreate;
import com.source.classes.purchaseorderrequests.edit.PorEdit;
import com.source.classes.purchaseorderrequests.reject.PorReject;
import com.source.classes.purchaseorderrequests.approve.PorApprove;
import com.source.classes.purchaseorderrequests.revision.PorRevision;
import com.source.classes.purchaseorderrequests.sendforapproval.PorSendForApproval;
import com.source.classes.purchaseorderrequests.suspend.PorSuspend;
import com.source.classes.purchaseorders.SendForVendor;
import com.source.classes.requestforquotations.commercialevaluation.CommercialEvaluation;
import com.source.classes.requestforquotations.create.RfqCreate;
import com.source.classes.requestforquotations.edit.RfqEdit;
import com.source.classes.requestforquotations.quote.Quote;
import com.source.classes.requestforquotations.readyforevaluation.ReadyForEvaluation;
import com.source.classes.requestforquotations.regret.QuotationRegret;
import com.source.classes.requestforquotations.requote.Requote;
import com.source.classes.requestforquotations.suspend.RfqSuspend;
import com.source.classes.requestforquotations.technicalevaluation.TechnicalEvaluationApprove;
import com.source.classes.requestforquotations.technicalevaluation.TechnicalEvaluationCreate;
import com.source.classes.requestforquotations.technicalevaluation.TechnicalEvaluationReject;
import com.source.classes.requisitions.approve.Approve;
import com.source.classes.requisitions.assign.Assign;
import com.source.classes.requisitions.create.Create;
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
import java.io.File;

public class BaseMain {

    protected Logger logger;
    protected ToastrUtil toastrUtil;
    protected ObjectMapper objectMapper;
    protected JsonNode jsonNode;
    protected GetTitleUtil getTitleUtil;
    protected SaveToTestDataJsonUtil saveToTestDataJsonUtil;
    protected GetInvoiceReferenceIdUtil getInvoiceReferenceIdUtil;
    protected LocatorHandlerUtil locatorHandlerUtil;
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    protected PlaywrightFactory playwrightFactory;
    protected ICurrencyExchangeRate iCurrencyExchangeRate;
    protected PR_List_Flow prListFlow;
    protected MSA_Flow msaFlow;
    protected OA_Flow oaFlow;
    protected IV_Flow ivFlow;
    protected ILogin iLogin;
    protected ILogout iLogout;
    protected IPrType iPrType;
    protected IPrCreate iPrCreate;
    protected IPrEdit iPrEdit;
    protected IPrSendForApproval iPrSendForApproval;
    protected IPrReject iPrReject;
    protected IPrApprove iPrApprove;
    protected IPrBuyerManagerSuspend iPrBuyerManagerSuspend;
    protected IPrAssign iPrAssign;
    protected IPrBuyerSuspend iPrBuyerSuspend;
    protected IRfqCreate iRfqCreate;
    protected IRfqEdit iRfqEdit;
    protected IRfqSuspend iRfqSuspend;
    protected IQuoRegret iQuoRegret;
    protected IQuoSubmit iQuoSubmit;
    protected IQuoRequote iQuoRequote;
    protected IReadyForEvalutation iReadyForEvalutation;
    protected ITeCreate iTeCreate;
    protected ITeApprove iTeApprove;
    protected ITeReject iTeReject;
    protected ICeCreate iCeCreate;
    protected IPorCreate iPorCreate;
    protected IPorEdit iPorEdit;
    protected IPorSuspend iPorSuspend;
    protected IPorSendForApproval iPorSendForApproval;
    protected IPorReject iPorReject;
    protected IPorApprove iPorApprove;
    protected IPorRevision iPorRevision;
    protected IPoSendForVendor iPoSendForVendor;
    protected IOsCreate iOsCreate;
    protected IOsEdit iOsEdit;
    protected IOsReject iOsReject;
    protected IOsApprove iOsApprove;
    protected IInsCreate iInsCreate;
    protected IInsReadyForInspection iInsReadyForInspection;
    protected IInsFail iInsFail;
    protected IInsAssign iInsAssign;
    protected IDnCreate iDnCreate;
    protected IDnEdit iDnEdit;
    protected IDnReturn iDnReturn;
    protected IDnAssign iDnAssign;
    protected IDnCancel iDnCancel;
    protected IFfrInvite iFfrInvite;
    protected IFfrQuote iFfrQuote;
    protected IFfrRequote iFfrRequote;
    protected IWoCreate iWoCreate;
    protected IWoEdit iWoEdit;
    protected IWoTrackerStatus iWoTrackerStatus;
    protected IWoOkForInvoice iWoOkForInvoice;
    protected IInvCreate iInvCreate;
    protected IInvHold iInvHold;
    protected IInvRevert iInvRevert;
    protected IInvCancel iInvCancel;
    protected IInvSendForApproval iInvSendForApproval;
    protected IInvChecklistReject iInvChecklistReject;
    protected IInvChecklistAccept iInvChecklistAccept;
    protected IInvEdit iInvEdit;
    protected IInvReturn iInvReturn;
    protected IInvVerify iInvVerify;
    protected IInvReject iInvReject;
    protected IInvApproval iInvApproval;
    protected IWoInvCreate iWoInvCreate;
    protected IWoInvHold iWoInvHold;
    protected IWoInvRevert iWoInvRevert;
    protected IWoInvCancel iWoInvCancel;
    protected IWoInvSendForApproval iWoInvSendForApproval;
    protected IWoInvChecklistAccept iWoInvChecklistAccept;
    protected IWoInvChecklistReject iWoInvChecklistReject;
    protected IWoInvEdit iWoInvEdit;
    protected IWoInvReturn iWoInvReturn;
    protected IWoInvVerify iWoInvVerify;
    protected IWoInvReject iWoInvReject;
    protected IWoInvApproval iWoInvApproval;

//TODO Constructor
    public BaseMain(){
        try {
            this.logger = LoggerUtil.getLogger(BaseMain.class);
            objectMapper = new ObjectMapper();
            jsonNode = objectMapper.readTree(new File("./src/test/resources/config/test-data.json"));
            playwrightFactory = new PlaywrightFactory(objectMapper, jsonNode);
            playwright = playwrightFactory.getPlaywright();
            playwrightFactory.initializeBrowser(jsonNode);
            browser = playwrightFactory.getBrowser();
            playwrightFactory.initializeBrowserContext();
            browserContext = playwrightFactory.getBrowserContext();
            page = playwrightFactory.initializePage(jsonNode);
            iLogin = new Login(jsonNode, page);
            iLogout = new Logout(page);
            toastrUtil = new ToastrUtil(page);
            getTitleUtil = new GetTitleUtil(jsonNode, logger);
            saveToTestDataJsonUtil = new SaveToTestDataJsonUtil(playwrightFactory, objectMapper, jsonNode);
            getInvoiceReferenceIdUtil = new GetInvoiceReferenceIdUtil(playwright, playwrightFactory, page, iLogin, jsonNode, objectMapper, iLogout);
            locatorHandlerUtil = new LocatorHandlerUtil(logger);
            prListFlow = new PR_List_Flow(page);
            msaFlow = new MSA_Flow(page);
            oaFlow = new OA_Flow(page);
            ivFlow = new IV_Flow(page);

//TODO Requisition
            iPrCreate = new Create(playwrightFactory, objectMapper, iLogin, jsonNode, page, iLogout);
            iPrType = new PurchaseRequisitionTypeHandler(iPrCreate);
            iPrEdit = new Edit(iLogin, jsonNode, page, iLogout);
            iPrSendForApproval = new SendForApproval(playwrightFactory, objectMapper, iLogin, jsonNode, page, iLogout);
            iPrReject = new Reject(iLogin, jsonNode, page, iLogout);
            iPrApprove = new Approve(objectMapper, iLogin, jsonNode, page, iLogout, playwrightFactory);
            iPrBuyerManagerSuspend = new BuyerManagerSuspend(iLogin, jsonNode, page, iLogout, iPrEdit);
            iPrAssign = new Assign(playwrightFactory, iLogin, jsonNode, page, iLogout, objectMapper);
            iPrBuyerSuspend = new BuyerSuspend(iLogin, jsonNode, page, iLogout, objectMapper);

//TODO Request For Quotation
            iRfqCreate = new RfqCreate(iLogin, jsonNode, page, iLogout, playwrightFactory, objectMapper);
            iRfqEdit = new RfqEdit(iLogin, jsonNode, page, iLogout);
            iRfqSuspend = new RfqSuspend(iLogin, jsonNode, page, iLogout, iRfqEdit, iPrEdit, iPrSendForApproval, iPrApprove, iPrAssign, iRfqCreate);
            iQuoSubmit = new Quote(iLogin, jsonNode, page, iLogout);
            iQuoRegret = new QuotationRegret(iQuoSubmit, iLogin, jsonNode, page, iLogout);
            iQuoRequote = new Requote(iLogin, jsonNode, page, iLogout);
            iReadyForEvalutation = new ReadyForEvaluation(iLogin, jsonNode, page, iLogout, playwrightFactory, objectMapper);
            iTeCreate = new TechnicalEvaluationCreate(iLogin, jsonNode, page, iLogout);
            iTeReject = new TechnicalEvaluationReject(iLogin, jsonNode, page, iLogout, iTeCreate);
            iTeApprove = new TechnicalEvaluationApprove(iLogin, jsonNode, page, iLogout, iTeCreate);
            iCeCreate = new CommercialEvaluation(iLogin, jsonNode, page, iLogout);

//TODO Purchase Order Requests
            iPorCreate = new PorCreate(iLogin, jsonNode, page, iLogout, playwrightFactory, objectMapper, prListFlow);
            iPorEdit = new PorEdit(iLogin, jsonNode, page, iLogout);
            iPorSuspend = new PorSuspend(iLogin, jsonNode, page, iLogout, iPrEdit, iPrSendForApproval, iPrApprove, iPrAssign, iPorCreate, iPorEdit, iCeCreate);
            iPorSendForApproval = new PorSendForApproval(iLogin, jsonNode, page, iLogout, objectMapper, playwrightFactory);
            iPorReject = new PorReject(iLogin, jsonNode, page, iLogout, iPorEdit, iPorSendForApproval);
            iPorApprove = new PorApprove(iLogin, jsonNode, page, iLogout, playwrightFactory, objectMapper, iPorSendForApproval, msaFlow);

//TODO Purchase Orders
            iPoSendForVendor = new SendForVendor(iLogin, jsonNode, page, iLogout, playwrightFactory, objectMapper);

//TODO Purchase Order Request Revision
            iPorRevision = new PorRevision(iLogin, jsonNode, page, iLogout, iPorSendForApproval, iPorApprove, playwrightFactory, objectMapper);

//TODO Order Schedules
            iOsCreate = new OsCreate(iLogin, jsonNode, page, iLogout, playwrightFactory);
            iOsEdit = new OsEdit(iLogin, jsonNode, page, iLogout);
            iOsReject = new OsReject(iLogin, jsonNode, page, iLogout);
            iOsApprove = new OsApprove(iLogin, jsonNode, page, iLogout, objectMapper, oaFlow, playwrightFactory);

//TODO Inspections
            iInsReadyForInspection = new InsReadyForInspection(iLogin, jsonNode, page, iLogout);
            iInsCreate = new InsCreate(iLogin, jsonNode, page, iLogout);
            iInsFail = new InsFail(iLogin, jsonNode, page, iLogout, iInsReadyForInspection);
            iInsAssign = new InsAssign(iLogin, jsonNode, page, iLogout);

//TODO Dispatch Notes
            iDnCreate = new DnCreate(iLogin, jsonNode, page, iLogout);
            iDnEdit = new DnEdit(iLogin, jsonNode, page, iLogout);
            iDnAssign = new DnAssign(iLogin, jsonNode, page, iLogout, playwrightFactory);
            iDnReturn = new DnReturn(iLogin, jsonNode, page, iLogout, iDnEdit);
            iDnCancel = new DnCancel(iLogin, jsonNode, page, iLogout, iDnCreate);

//TODO Freight Forwarder Requests
            iFfrInvite = new FfrInvite(iLogin, jsonNode, page, iLogout, iDnAssign);
            iFfrQuote = new FfrQuote(iLogin, jsonNode, page, iLogout);
            iFfrRequote = new FfrRequote(iLogin, jsonNode, iFfrQuote, iLogout, page);

//TODO Work Orders
            iWoCreate = new WoCreate(iLogin, jsonNode, page, iLogout);
            iWoEdit = new WoEdit(iLogin, jsonNode, page, iLogout);
            iWoTrackerStatus = new WoTrackerStatus(iLogin, jsonNode, page, iLogout, playwrightFactory);
            iWoOkForInvoice = new WoOkForInvoice(iLogin, jsonNode, page, iLogout, playwrightFactory);

//TODO Currency Exchange Rate
            iCurrencyExchangeRate = new CurrencyExchangeRate(playwrightFactory, iLogin, jsonNode, iLogout);

//TODO Purchase Order Invoices
            iInvCreate = new InvCreate(playwrightFactory, iLogin, jsonNode, page, iLogout, iCurrencyExchangeRate);
            iInvCancel = new InvCancel(iLogin, jsonNode, page, iLogout, iInvCreate);
            iInvHold = new InvHold(iLogin, jsonNode, page, iLogout);
            iInvRevert = new InvRevert(iLogin, jsonNode, page, iLogout);
            iInvChecklistAccept = new InvChecklistAccept(iLogin, jsonNode, page, iLogout);
            iInvChecklistReject = new InvChecklistReject(iLogin, jsonNode, page, iLogout);
            iInvSendForApproval = new InvSendForApproval(iLogin, jsonNode, page, iLogout, objectMapper);
            iInvReturn = new InvReturn(iLogin, jsonNode, page, iLogout);
            iInvVerify = new InvVerify(iLogin, jsonNode, page, iLogout);
            iInvEdit = new InvEdit(iLogin, jsonNode, page, iLogout);
            iInvReject = new InvReject(iLogin, jsonNode, page, iLogout, iInvSendForApproval, iInvVerify);
            iInvApproval = new InvApproval(iLogin, jsonNode, page, iLogout, objectMapper, playwrightFactory, ivFlow);

//TODO Work Order Invoices
            iWoInvCreate = new WoInvCreate(playwrightFactory, iLogin, jsonNode, page, iLogout, iCurrencyExchangeRate);
            iWoInvCancel = new WoInvCancel(iLogin, jsonNode, page, iLogout, iWoInvCreate);
            iWoInvHold = new WoInvHold(iLogin, jsonNode, page, iLogout);
            iWoInvRevert = new WoInvRevert(iLogin, jsonNode, page, iLogout);
            iWoInvChecklistAccept = new WoInvChecklistAccept(iLogin, jsonNode, page, iLogout);
            iWoInvChecklistReject = new WoInvChecklistReject(iLogin, jsonNode, page, iLogout);
            iWoInvSendForApproval = new WoInvSendForApproval(iLogin, jsonNode, page, iLogout);
            iWoInvReturn = new WoInvReturn(iLogin, jsonNode, page, iLogout, iWoInvSendForApproval);
            iWoInvVerify = new WoInvVerify(iLogin, jsonNode, page, iLogout);
            iWoInvEdit = new WoInvEdit(iLogin, jsonNode, page, iLogout);
            iWoInvReject = new WoInvReject(iLogin, jsonNode, page, iLogout, iWoInvSendForApproval, iWoInvVerify);
            iWoInvApproval = new WoInvApproval(iLogin, jsonNode, page, iLogout, objectMapper, playwrightFactory, ivFlow);
        } catch (Exception exception) {
            logger.error("Error Initializing BaseMain Constructor: {}", exception.getMessage());
        }
    }
}