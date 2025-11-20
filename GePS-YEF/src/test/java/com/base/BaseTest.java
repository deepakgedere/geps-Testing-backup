package com.base;
import com.classes.login.LoginTest;
import com.classes.requisition.approve.ApproveTest;
import com.classes.requisition.assign.AssignTest;
import com.classes.requisition.create.CreateTest;
import com.classes.requisition.edit.EditTest;
import com.classes.requisition.reject.RejectTest;
import com.classes.requisition.sendforapproval.SendForApprovalTest;
import com.classes.requisition.suspend.SuspendTest;
import com.factory.PlaywrightFactory;
import com.microsoft.playwright.Page;
import com.poc.base.BaseMain;
import com.poc.classes.login.Login;
import com.poc.classes.logout.Logout;
import com.poc.classes.requisition.approve.Approve;
import com.poc.classes.requisition.assign.Assign;
import com.poc.classes.requisition.create.Create;
import com.poc.classes.requisition.edit.Edit;
import com.poc.classes.requisition.reject.Reject;
import com.poc.classes.requisition.sendforapproval.SendForApproval;
import com.poc.classes.requisition.suspend.BuyerSuspend;
import com.poc.classes.requisition.type.PurchaseRequisitionTypeHandler;
import com.poc.interfaces.login.ILogin;
import com.poc.interfaces.logout.ILogout;
import com.poc.interfaces.requisitions.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import java.util.Properties;

public class BaseTest {

    protected PlaywrightFactory playwrightFactory;
    protected Properties properties;
    protected Page page;
    protected LoginTest login;
    protected ILogin iLogin;
    protected ILogout iLogout;
    protected IPrType iPrType;
    protected CreateTest create;
    protected IPrCreate iPrCreate;
    protected EditTest edit;
    protected IPrEdit iPrEdit;
    protected SendForApprovalTest sendForApproval;
    protected IPrSendForApproval iPrSendForApproval;
    protected RejectTest reject;
    protected IPrReject iPrReject;
    protected ApproveTest approve;
    protected IPrApprove iPrApprove;
    protected AssignTest assign;
    protected IPrAssign iPrAssign;
    protected SuspendTest suspend;
    protected IPrSuspend iPrSuspend;

//TODO Constructor
    public BaseTest() {
    }

    @BeforeTest
    public void setUp(){
        try {
            playwrightFactory = new PlaywrightFactory();
            properties = playwrightFactory.initializeProperties();
            page = playwrightFactory.initializePage(properties);

//TODO Requisition
            iLogin = new Login(properties, page);
            iLogout = new Logout(page);
            login = new LoginTest();
            iPrCreate = new Create(iLogin, properties, page, iLogout);
            iPrType = new PurchaseRequisitionTypeHandler(iPrCreate, properties);
            create = new CreateTest();
            iPrSendForApproval = new SendForApproval(iLogin, properties, page, iLogout);
            sendForApproval = new SendForApprovalTest();
            iPrApprove = new Approve(iLogin, properties, page, iLogout);
            approve = new ApproveTest();
            iPrAssign = new Assign(iLogin, properties, page, iLogout);
            assign = new AssignTest();
            iPrEdit = new Edit(iLogin, properties, page, iLogout, iPrSendForApproval, iPrApprove, iPrAssign);
            edit = new EditTest();
            iPrReject = new Reject(iLogin, properties, page, iLogout, iPrEdit);
            reject = new RejectTest();
            iPrSuspend = new BuyerSuspend(iLogin, properties, page, iLogout, iPrEdit);
            suspend = new SuspendTest();

        } catch (Exception exception) {
            System.out.println("Error :" + exception);
        }
    }

    @AfterTest
    public void tearDown() {
        try {
            page.context().browser().close();
        } catch (Exception exception) {
            System.out.println("Error :" + exception);
        }
    }

    @AfterTest
    public void tearDown(Page page) {
        try {
            page.context().browser().close();
        } catch (Exception exception) {
            System.out.println("Error :" + exception);
        }
    }
}