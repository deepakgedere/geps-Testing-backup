package com.listeners;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

public class FailFastListener implements ITestListener {

    private static volatile boolean hasFailureOccurred = false;

    @Override
    public void onTestStart(ITestResult result) {
        if (hasFailureOccurred) {
            throw new SkipException("Skipping test due to a previous failure (fail-fast mode).");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        hasFailureOccurred = true;
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Optional: log skipped test
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // No action needed
    }

    @Override
    public void onStart(ITestContext context) {
        // No action needed
    }

    @Override
    public void onFinish(ITestContext context) {
        // No action needed
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // No action needed
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }
}