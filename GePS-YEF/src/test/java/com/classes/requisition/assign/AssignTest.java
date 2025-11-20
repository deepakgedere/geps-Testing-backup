package com.classes.requisition.assign;
import com.base.BaseTest;
import org.testng.annotations.Test;

public class AssignTest extends BaseTest {

    @Test
    public void assign() {
        try {
            iPrAssign.buyerManagerLogin();
            iPrAssign.buyerManagerAssign();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }
}