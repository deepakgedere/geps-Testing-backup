package com.classes.requisition.reject;
import com.base.BaseTest;
import org.testng.annotations.Test;

public class RejectTest extends BaseTest {

    @Test
    public void reject() {
        try {
            iPrReject.reject();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }
}