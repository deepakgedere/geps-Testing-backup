package com.classes.requisition.edit;
import com.base.BaseTest;
import org.testng.annotations.Test;

public class EditTest extends BaseTest {

    @Test
    public void NonCatalogPrEditTestMethod(){
        try {
            iPrEdit.edit();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }
}