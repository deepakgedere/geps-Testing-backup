package com.constants.requisitions;

public class LPunchout {

//TODO Constructor
    private LPunchout(){
    }

    public static final String BECHTLE_PUNCHOUT_VENDOR_LOCATOR = "//a[contains(@onclick, 'https://oci.bechtle.com/shop/default_bios.asp')]";
    public static final String ACCEPT_ALL_BUTTON = "[data-testid='uc-accept-all-button']";
    public static final String PRODUCT_LINK = "//a[@href = 'https://www.bechtle.com/nl-en/shop/my-account/wishlist/8829880009405']";
    public static final String ADD_TO_BASKET = "//button[@data-testid='addtocart_position_wishlist_submit']";
    public static final String VIEW_CART = "//a[@class='o-header__userMenuContainer__cart']";
    public static final String CHECKOUT = "//a[@data-testid='cart_button_submit_oci_xml']";
    public static final String ITEM_EDIT_BUTTON = "#editrequisitionitem";



    public static String getPunchoutUrl(String type) {
        String punchoutLocator = "";
        if (type.equalsIgnoreCase("PS")) {
            punchoutLocator = "//a[@onclick=\"setPunchOutPRType('PS')\"]";
        } else if (type.equalsIgnoreCase("Sales")) {
            punchoutLocator = "//a[@onclick=\"setPunchOutPRType('Sales')\"]";
        } else if (type.equalsIgnoreCase("SD")) {
            punchoutLocator = "//a[@onclick=\"setPunchOutPRType('SD')\"]";
        }
        return punchoutLocator;
    }
}