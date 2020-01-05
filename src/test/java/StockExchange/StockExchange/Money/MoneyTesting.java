package StockExchange.StockExchange.Money;

import StockExchange.StockExchange.Money.Currency;
import StockExchange.StockExchange.Money.Money;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.engine.config.JupiterConfiguration;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
public class MoneyTesting {

    @Test
    public void testShowingInDifferentCurrencies(){
        var amount = Money.multiply(100,new BigDecimal(3.845));
        Assert.assertEquals(385, amount);
    }

    @Test
    public void testConnectionToRateSide(){
        var rate = Money.getRate();
        Assert.assertNotEquals(0,rate);
    }
}
