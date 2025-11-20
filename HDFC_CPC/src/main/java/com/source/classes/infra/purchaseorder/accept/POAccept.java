package com.source.classes.infra.purchaseorder.accept;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.source.interfaces.infra.purchaseorder.accept.IPoAccept;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import org.apache.logging.log4j.Logger;

import static com.enums.infra.purchaseorders.accept.LPoAccept.*;
import static com.factory.PlaywrightFactory.attachScreenshotWithName;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.utils.GetRowUtil.getPoRowVendor;

public class POAccept implements IPoAccept {

    Logger logger;
    JsonNode jsonNode;
    Page page;
    ILogin iLogin;
    ILogout iLogout;

    public POAccept( JsonNode jsonNode, Page page, ILogin iLogin, ILogout iLogout) {
        this.jsonNode = jsonNode;
        this.page = page;
        this.iLogin = iLogin;
        this.iLogout = iLogout;
        this.logger = org.apache.logging.log4j.LogManager.getLogger(POAccept.class);
    }

    public void accept(String trnId) {
        try{
            int trnCount;
            if (trnId.contains(" - ")) {
                trnCount = Integer.parseInt(trnId.split(" - ")[1].trim())-1;
            } else {
                trnCount = 0; // Use 0 for the first item
            }

            JsonNode approvers = jsonNode.get("mailIds").get("VendorEmails");

            iLogin.performLogin(approvers.get(trnCount).asText());

            Locator row = getPoRowVendor(page, trnId);
            try {
                page.waitForSelector(PO_LIST_NAV_BUTTON.getSelector()).click();
                row.click();
            } catch (Exception e) {
                page.waitForSelector(ASN_LIST_PAGE.getSelector()).click();
                page.waitForSelector(PO_LIST_NAV_BUTTON.getSelector()).click();
                row.click();
            }

            row.locator(ACKNOWLEDGE_BUTTON.getSelector()).click(new Locator.ClickOptions().setTimeout(10000));

            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Leave a comment")).fill("Accepted PO");

            page.waitForSelector(SUBMIT_BUTTON.getSelector()).click();

            Locator status = row.locator(ROW_STATUS.getSelector());
            assertThat(status).hasText("Ack Accepted", new LocatorAssertions.HasTextOptions().setTimeout(20000));

            status.scrollIntoViewIfNeeded();
            attachScreenshotWithName("PO Accepted" , page);

            iLogout.performLogout();
        }
        catch (Exception exception) {
            logger.error("Exception in PO Accept Login function: {}", exception.getMessage());
        }
    }
}
