package com.base;
import com.source.classes.bms.requisitions.create.BmsCreate;
import com.source.classes.infra.billofquantity.approve.BOQApprove;
import com.source.classes.infra.billofquantity.create.BOQCreate;
import com.source.classes.infra.billofquantity.reject.BOQReject;
import com.source.classes.infra.purchaseorder.accept.POAccept;
import com.source.classes.infra.purchaseorder.amend.POAmend;
import com.source.classes.infra.purchaseorder.approve.POApprove;
import com.source.classes.infra.purchaseorder.reject.POReject;
import com.source.classes.infra.requisitions.approve.Approve;
import com.source.classes.infra.requisitions.create.Create;
import com.source.classes.infra.requisitions.edit.Edit;
import com.source.classes.infra.requisitions.reject.Reject;
import com.source.classes.logout.Logout;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.source.classes.login.Login;
import com.source.interfaces.bms.requisitions.create.IBmsPrCreate;
import com.source.interfaces.infra.billofquantity.approve.IBoqApprove;
import com.source.interfaces.infra.billofquantity.create.IBoqCreate;
import com.source.interfaces.infra.billofquantity.reject.IBoqReject;
import com.source.interfaces.infra.purchaseorder.accept.IPoAccept;
import com.source.interfaces.infra.purchaseorder.amend.IPoAmend;
import com.source.interfaces.infra.purchaseorder.approve.IPoApprove;
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
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.io.File;

public class BaseMain {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected PlaywrightFactory playwrightFactory;
    protected Logger logger;
    protected JsonNode jsonNode;
    protected ObjectMapper objectMapper;
    protected Page page;
    protected ILogin iLogin;
    protected ILogout iLogout;
    protected IPrCreate iPrCreate;
    protected IPrEdit iPrEdit;
    protected IPrReject iPrReject;
    protected IPrApprove iPrApprove;
    protected IBoqCreate iBoqCreate;
    protected IBoqReject iBoqReject;
    protected IBoqApprove iBoqApprove;
    protected IPoApprove iPoApprove;
    protected IPoReject iPoReject;
    protected IPoAmend iPoAmmend;
    protected IPoAccept iPoAccept;

    protected IBmsPrCreate iBmsPrCreate;


//TODO Constructor
    public BaseMain() {
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

//TODO Purchase Requisition
            iPrCreate = new Create(jsonNode, page, iLogin, iLogout, objectMapper);
            iPrEdit = new Edit(jsonNode, page, iLogin, iLogout, iPrCreate);
            iPrReject = new Reject(jsonNode, page, iLogin, iLogout);
            iPrApprove = new Approve(jsonNode, page, iLogin, iLogout);
            iBmsPrCreate = new BmsCreate(jsonNode, page, iLogin, iLogout, objectMapper);

//TODO Bill Of Quantity
            iBoqCreate = new BOQCreate(jsonNode, page, iLogin, iLogout);
            iBoqReject = new BOQReject(jsonNode, page, iLogin, iLogout, iPrReject, iBoqCreate);
            iBoqApprove = new BOQApprove(jsonNode, page, iLogin, iLogout);

//TODO Purchase Order
            iPoApprove = new POApprove(jsonNode, page, iLogin, iLogout);
            iPoReject = new POReject(jsonNode, page, iLogin, iLogout);
            iPoAmmend = new POAmend(jsonNode, page, iLogin, iLogout, iPrEdit, iPrApprove, iBoqCreate, iBoqApprove, iPoApprove);
            iPoAccept = new POAccept(jsonNode, page, iLogin, iLogout);


        } catch (Exception exception) {
            logger.error("Exception in BaseMain Constructor: {}", exception.getMessage());
        }
    }
}