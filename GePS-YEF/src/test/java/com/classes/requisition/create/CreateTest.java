package com.classes.requisition.create;
import com.base.BaseTest;
import org.testng.annotations.Test;

public class CreateTest extends BaseTest {

    @Test
    public void create(){
        try{
            iPrType.processRequisitionType();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }
}