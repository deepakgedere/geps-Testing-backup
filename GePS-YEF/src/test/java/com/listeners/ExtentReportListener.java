package com.listeners;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import com.factory.PlaywrightFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import static com.factory.PlaywrightFactory.takeScreenshot;

public class ExtentReportListener implements ITestListener {

    PlaywrightFactory playWrightFactory;
    public String OUTPUT_FOLDER = "./build/";
    public String FILE_NAME = "TestExecutionReport.html";
    public ExtentReports extent = init();
    public ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    public ExtentReports extentReports;

//TODO Constructor
    public ExtentReportListener(){
    }

    public ExtentReports init() {
        Path path = Paths.get(OUTPUT_FOLDER);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException error) {
                System.out.println("What is the error: " + error.getMessage());
            }
        }

        playWrightFactory = new PlaywrightFactory();
        extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(OUTPUT_FOLDER + FILE_NAME);
        reporter.config().setReportName("Yokogawa Corporation Of America");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("System", "Windows");
        extentReports.setSystemInfo("Author", "Issac");
        extentReports.setSystemInfo("Build#", "0.1");
        extentReports.setSystemInfo("Team", "QA");
        extentReports.setSystemInfo("Customer Name", "YEA");
        return extentReports;
    }

    @Override
    public synchronized void onStart(ITestContext context) {
        System.out.println("Test Suite started!");
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        System.out.println(("Test Suite is ending!"));
        extent.flush();
        test.remove();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String qualifiedName = result.getMethod().getQualifiedName();
        int last = qualifiedName.lastIndexOf(".");
        int mid = qualifiedName.substring(0, last).lastIndexOf(".");
        String className = qualifiedName.substring(mid + 1, last);

        System.out.println(methodName + " started!");
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());

        extentTest.assignCategory(result.getTestContext().getSuite().getName());
        extentTest.assignCategory(className);
        test.set(extentTest);
        test.get().getModel().setStartTime(getTime(result.getStartMillis()));
    }

    public synchronized void onTestSuccess(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " passed!"));
        test.get().pass("Test passed");
        test.get().pass(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(),result.getMethod().getMethodName()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestFailure(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " failed!"));
        test.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(),result.getMethod().getMethodName()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestSkipped(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " skipped!"));
        test.get().skip(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(), result.getMethod().getMethodName()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
    }

    public Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}