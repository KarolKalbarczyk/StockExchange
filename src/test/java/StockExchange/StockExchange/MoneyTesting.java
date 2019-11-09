package StockExchange.StockExchange;

import StockExchange.StockExchange.Money.Currency;
import StockExchange.StockExchange.Money.Money;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.i18n.LocaleContextHolder;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Currency.class)
public class MoneyTesting {

    Currency currency;

    @Test
    public void testShowingInDifferentCurrencies(){
        PowerMockito.mockStatic(Currency.class);
        var locale = LocaleContextHolder.getLocale();
        currency = Mockito.mock(Currency.class);
        Mockito.when(currency.getRate()).thenReturn(3.845);
        BDDMockito.given(Currency.valueOf(locale.getCountry())).willReturn(currency);
        Assert.assertEquals(385, Money.getMoneyInPresentCurrency(100));
    }

}
