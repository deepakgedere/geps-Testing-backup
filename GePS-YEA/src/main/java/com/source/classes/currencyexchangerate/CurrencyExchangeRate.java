package com.source.classes.currencyexchangerate;
import com.factory.PlaywrightFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.source.interfaces.currencyexchangerate.ICurrencyExchangeRate;
import com.source.interfaces.login.ILogin;
import com.source.interfaces.logout.ILogout;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.util.List;
import static com.constants.currencyexchangerate.LCurrencyExchangeRate.*;
import static com.utils.LocatorHandlerUtil.locatorVisibleHandler;

public class CurrencyExchangeRate implements ICurrencyExchangeRate {

    Logger logger;
    JsonNode jsonNode;
    PlaywrightFactory playwrightFactory;
    ILogin iLogin;
    ILogout iLogout;
    Page page;

//TODO Constructor
    private CurrencyExchangeRate() {
    }

    public CurrencyExchangeRate(PlaywrightFactory playwrightFactory, ILogin iLogin, JsonNode jsonNode, ILogout iLogout) {
        this.iLogin = iLogin;
        this.jsonNode = jsonNode;
        this.playwrightFactory = playwrightFactory;
        this.iLogout = iLogout;
        this.logger = LoggerUtil.getLogger(CurrencyExchangeRate.class);
    }

    public double findCurrency() {
        double rate = 0;
        try {
            page = playwrightFactory.getCurrencyExchangeRate();

            String adminMailId = jsonNode.get("mailIds").get("adminEmail").asText();
            iLogin.performLogin(adminMailId, page);

            Locator setingsNavigationBarLocator = page.locator(SETTING_NAVIGATION_BAR_LOCATOR);
            locatorVisibleHandler(setingsNavigationBarLocator);
            setingsNavigationBarLocator.click();

//TODO CurrencyExchangeRate
            Locator currencyExchangeRateLocator = page.locator(CURRENCY_EXCHANGE_RATE_LOCATOR);
            locatorVisibleHandler(currencyExchangeRateLocator);
            currencyExchangeRateLocator.click();

//TODO SearchBoxCurrencyCode
            String fromCode = jsonNode.get("configSettings").get("currencyCode").asText();
            ObjectMapper objectMapper = new ObjectMapper();
            APIResponse apiResponse = page.request().fetch("https://geps_hopes_yea.cormsquare.com/api/currencyExchangeRate/GetActiveCERates?_=1747756714967");
            JsonNode responseNode = objectMapper.readTree(apiResponse.body());

            for (JsonNode node : responseNode) {
                if (fromCode.equals(node.get("fromCode").asText()) && "SGD".equals(node.get("toCode").asText())) {
                    rate = node.get("rate").asDouble();
                    break;
                }
            }

            iLogout.performLogout(page);

            playwrightFactory.tearDown(page);
        } catch (Exception exception) {
            logger.error("Exception in Find Currency Function: {}", exception.getMessage());
        }
        return rate;
    }
}