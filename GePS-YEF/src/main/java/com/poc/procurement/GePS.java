package com.poc.procurement;
import com.poc.base.BaseMain;

public class GePS {

    static BaseMain baseMain;

//TODO Constructor
    private GePS(){
    }

    public static void main(String[] args) {
        try {
            baseMain = new BaseMain();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }
}