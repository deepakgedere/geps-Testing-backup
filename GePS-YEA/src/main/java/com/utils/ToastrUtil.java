package com.utils;
import com.microsoft.playwright.*;
import java.util.concurrent.*;

public class ToastrUtil {

    static Page page;
    static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    static ScheduledFuture<?> toastrTask;
    static boolean messageCaptured = false;

//TODO Constructor
    public ToastrUtil(Page page) {
        this.page = page;
    }

    public static void startMonitoring() {
        toastrTask = scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                if (!messageCaptured && page.isVisible(".toast-message")) {
                    String toastrMessage = page.textContent(".toast-message");
                    if (!toastrMessage.isEmpty()) {
                        System.out.println("Toastr message detected: " + toastrMessage);
                        messageCaptured = true;
                    }
                }
            } catch (PlaywrightException exception) {
                if (!exception.getMessage().contains("Object doesn't exist")) {
                    exception.printStackTrace();
                }
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    public static void stopMonitoring() {
        if (toastrTask != null) {
            toastrTask.cancel(true);
        }
        scheduledExecutorService.shutdown();
    }
}