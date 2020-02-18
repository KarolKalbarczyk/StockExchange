package StockExchange.StockExchange.money;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
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
