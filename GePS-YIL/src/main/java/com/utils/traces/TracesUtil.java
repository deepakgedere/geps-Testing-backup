package com.utils.traces;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Tracing;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TracesUtil {
    BrowserContext browserContext;
    String traceFileName;
    static Logger logger = LoggerUtil.getLogger(TracesUtil.class);;

    public TracesUtil(BrowserContext browserContext, String traceFileName) {
        this.browserContext = browserContext;
        this.traceFileName = traceFileName;
    }

    public static void startTracing(BrowserContext context, String traceFileName) {
        try {
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true)
                    .setTitle("Test Trace")
                    .setName(traceFileName)
            );
        } catch (Exception exception) {
            logger.error("Error in Start Tracing Function: {}", exception.getMessage());
        }
    }

    public static void stopTracing(BrowserContext context, String traceFileName) {
        Path traceDir = Paths.get("test-output/traces");
        try {
            Files.createDirectories(traceDir);
            context.tracing().stop(new Tracing.StopOptions().setPath(traceDir.resolve(traceFileName)));
        } catch (Exception exception) {
            logger.error("Error in Stop Tracing Function: {}", exception.getMessage());
        }
    }
}
