package com.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;
import com.source.classes.login.LoginTest;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Page;
import com.source.classes.login.Login;
import com.source.classes.logout.Logout;
import com.source.classes.logout.LogoutTest;
import com.source.classes.requisitions.create.Create;
import com.source.classes.requisitions.create.PrCreateTest;
import com.source.classes.requisitions.prType.PurchaseRequisitionTypeHandler;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.source.interfaces.requisitions.prCreate.IPrCreate;
import com.source.interfaces.requisitions.prType.IPrType;
import com.utils.*;
import com.utils.traces.TracesUtil;
import org.apache.logging.log4j.Logger;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {

    protected Logger logger = LoggerUtil.getLogger(BaseTest.class);

    // Thread-safe objects using ThreadLocal
    protected static ThreadLocal<ObjectMapper> objectMapper = new ThreadLocal<>();
    protected static ThreadLocal<JsonNode> jsonNode = new ThreadLocal<>();
    protected static ThreadLocal<PlaywrightFactory> playwrightFactory = new ThreadLocal<>();
    protected static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    protected static ThreadLocal<Browser> browser = new ThreadLocal<>();
    protected static ThreadLocal<BrowserContext> browserContext = new ThreadLocal<>();
    protected static ThreadLocal<Page> page = new ThreadLocal<>();
    protected static ThreadLocal<ILogin> iLogin = new ThreadLocal<>();
    protected static ThreadLocal<ILogout> iLogout = new ThreadLocal<>();
    protected static ThreadLocal<LoginTest> loginTest = new ThreadLocal<>();
    protected static ThreadLocal<LogoutTest> logoutTest = new ThreadLocal<>();
    protected static ThreadLocal<PrCreateTest> prCreateTest = new ThreadLocal<>();
    protected static ThreadLocal<IPrType> iPrType = new ThreadLocal<>();
    protected static ThreadLocal<IPrCreate> iPrCreate = new ThreadLocal<>();
    protected static ThreadLocal<String> traceFileName = new ThreadLocal<>();

    public BaseTest() {
    }

    @BeforeTest
    public void globalSetup() {
        try {
            objectMapper.set(new ObjectMapper());
            // Thread-specific file name
            long threadId = Thread.currentThread().getId();
            String testName = Reporter.getCurrentTestResult().getTestContext().getName();
            String originalFilePath = "src/test/resources/config/test-data.json";
            String threadFilePath = "src/test/resources/config/" + testName + ".json";
            Files.copy(
                    Paths.get(originalFilePath),
                    Paths.get(threadFilePath),
                    StandardCopyOption.REPLACE_EXISTING
            );
            File jsonFile = new File(threadFilePath);
            jsonNode.set(objectMapper.get().readTree(jsonFile));
            if (jsonNode.get().has("output") && jsonNode.get().get("output").has("jsonFilePath")) {
                if (jsonNode.get() instanceof ObjectNode rootObject && rootObject.get("output") instanceof ObjectNode outputObject) {
                    outputObject.put("jsonFilePath", testName); // save method name
                }
            }
            objectMapper.get().writerWithDefaultPrettyPrinter().writeValue(jsonFile, jsonNode.get());

            String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
            traceFileName.set("GePS-HOPES_YEA-Trace_Suite_" + timestamp + ".zip");
            playwrightFactory.set(new PlaywrightFactory(jsonNode.get()));
            playwrightFactory.get().setPlaywright();
            playwright.set(playwrightFactory.get().getPlaywright());
            playwrightFactory.get().initializeBrowser(jsonNode.get());
            browser.set(playwrightFactory.get().getBrowser());
            playwrightFactory.get().initializeBrowserContext();
            browserContext.set(playwrightFactory.get().getBrowserContext());
            page.set(playwrightFactory.get().initializePage(jsonNode.get()));
            TracesUtil.startTracing(browserContext.get(), traceFileName.get());

            // Initialize Test Objects
            loginTest.set(new LoginTest());
            logoutTest.set(new LogoutTest());
            prCreateTest.set(new PrCreateTest());

            //Initialize Class Objects
            iLogin.set(new Login(jsonNode.get(), page.get(), objectMapper.get()));
            iLogout.set(new Logout(page.get(), jsonNode.get()));
            iPrCreate.set(new Create(objectMapper.get(), iLogin.get(), jsonNode.get(), page.get(), iLogout.get()));
            iPrType.set(new PurchaseRequisitionTypeHandler(iPrCreate.get()));

        } catch (Exception exception) {
            logger.error("Error Initializing globalSetup(): {}", exception.getMessage());
        }
    }

    @AfterTest(alwaysRun = true)
    public void globalTeardown() {
        try {
            TracesUtil.stopTracing(browserContext.get(), traceFileName.get());

            if (browserContext.get() != null) {
                browserContext.get().close();
            }

            if (browser.get() != null) {
                browser.get().close();
            }

            if (playwright.get() != null) {
                playwright.get().close();
            }

        } catch (Exception e) {
            logger.error("Error during teardown: {}", e.getMessage());
        } finally {
            // Remove thread-local values to prevent memory leaks
            objectMapper.remove();
            jsonNode.remove();
            playwrightFactory.remove();
            playwright.remove();
            browser.remove();
            browserContext.remove();
            page.remove();
            iLogin.remove();
            iLogout.remove();
            loginTest.remove();
            logoutTest.remove();
            traceFileName.remove();
        }
    }

    // Helper getters if needed in subclasses
    protected Page getPage() {
        return page.get();
    }

    protected ILogin getLogin() {
        return iLogin.get();
    }

    protected ILogout getLogout() {
        return iLogout.get();
    }
}