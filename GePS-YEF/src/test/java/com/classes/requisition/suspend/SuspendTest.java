package com.classes.requisition.suspend;
import com.base.BaseTest;
import org.testng.annotations.Test;

public class SuspendTest extends BaseTest {

    @Test
    public void suspend() {
        try {
            iPrSuspend.suspend();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }
}