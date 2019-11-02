package StockExchange.StockExchange.Money;

import org.springframework.context.i18n.LocaleContextHolder;

import javax.management.relation.RoleUnresolved;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {

    static BigDecimal HUNDRED = new BigDecimal(100);

    public static int getMoneyInPresentCurrency(long amount){
       var locale = LocaleContextHolder.getLocale();
       var currency = Currency.valueOf(locale.getCountry());
       var rate = new BigDecimal(currency.getRate());
       var money = new BigDecimal(amount);
       var moneyToDisplay = money.multiply(rate).
               setScale(0,RoundingMode.HALF_UP);
       return moneyToDisplay.intValue();
    }

    public static String getAsString(long amount){
        var money = new BigDecimal(amount);
        return money.multiply(HUNDRED).toString();
    }


}