package StockExchange.StockExchange.Money;

import org.springframework.context.i18n.LocaleContextHolder;

import javax.management.relation.RoleUnresolved;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyImpl implements Money {

    private BigDecimal money;
    private final BigDecimal HUNDRED = new BigDecimal(100);

    private Money setUpDecimal(BigDecimal d){
        d.setScale(2,RoundingMode.HALF_UP);
        money = d;
        return this;
    }

    @Override
    public void setAmount(long n) {
        money = new BigDecimal(n);
        money = money.divide(HUNDRED);
        money.setScale(2, RoundingMode.HALF_UP);
    }

    public void setAmount(BigDecimal money){
        this.money = money;
    }

    @Override
    public Money add(long n) {
        return add(new BigDecimal(n));
    }

    @Override
    public Money add(BigDecimal n) {
        var d = money.add(n);
        return setUpDecimal(d);
    }

    @Override
    public Money subtract(long n) {
        return subtract(new BigDecimal(n));
    }

    @Override
    public Money subtract(BigDecimal n) {
        var d = money.subtract(n);
        return setUpDecimal(d);
    }

    @Override
    public Money multiply(long n) {
        return multiply(new BigDecimal(n));
    }

    @Override
    public Money multiply(BigDecimal n) {
        var d = money.multiply(n);
        return setUpDecimal(d);
    }

    @Override
    public Money div(long n) {
        return div(new BigDecimal(n));
    }

    @Override
    public Money div(BigDecimal n) {
        var d = money.divide(n);
        return setUpDecimal(d);
    }

    @Override
    public String getMoneyInPresentCurrency(){
       var locale = LocaleContextHolder.getLocale();
       var currency = Currency.valueOf(locale.getCountry());
       var rate = new BigDecimal(currency.getRate());
       var moneyToDisplay = this.money.multiply(rate).
               setScale(2,RoundingMode.HALF_UP);
       var a = moneyToDisplay.toString();
       return a;
    }
}
