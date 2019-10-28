package StockExchange.StockExchange;

import StockExchange.StockExchange.Money.Currency;
import StockExchange.StockExchange.Money.Money;
import StockExchange.StockExchange.Money.MoneyFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.i18n.LocaleContextHolder;

import java.sql.DriverManager;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Currency.class)
public class MoneyTesting {

    Currency currency;

    @org.junit.Test
    public void testProperConversionToString(){
        var s = "1000";
        var money = MoneyFactory.getMoney(1000);
        var money2 = MoneyFactory.getMoney("1000");
        Assert.assertEquals(s, money.getAsString(),money.getValue().toString());
        Assert.assertEquals(s, money2.getValue().toString(),money2.getAsString());
    }

    @Test
    public void testShowingInDifferentCurrencies(){
        PowerMockito.mockStatic(Currency.class);
        var locale = LocaleContextHolder.getLocale();
        currency = Mockito.mock(Currency.class);
        Mockito.when(currency.getRate()).thenReturn(3.845);
        BDDMockito.given(Currency.valueOf(locale.getCountry())).willReturn(currency);
        var money = MoneyFactory.getMoney(100);
        Assert.assertEquals("3.85",money.getMoneyInPresentCurrency());
    }

}
