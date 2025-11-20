package com.procurement;
import com.base.BaseMain;

public class HDFC {

    static BaseMain baseMain;

//TODO Constructor
    private HDFC(){
    }

    public static void main(String[] args) {
        try {
            baseMain = new BaseMain();

        } catch (Exception exception) {
            System.out.println("Error initializing main function: " + exception.getMessage());
        }
    }
}