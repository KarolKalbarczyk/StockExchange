package StockExchange.StockExchange.Money;

import org.springframework.context.i18n.LocaleContextHolder;

import javax.management.relation.RoleUnresolved;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class MoneyImpl implements Money {

    private BigDecimal money;
    private final BigDecimal HUNDRED = new BigDecimal(100);

    private Money setUpDecimal(BigDecimal d){
        d.setScale(2,RoundingMode.HALF_UP);
        money = d;
        return this;
    }

    MoneyImpl(long n){
        this.setAmount(n);
    }
    MoneyImpl(String n){
        var d = new BigDecimal(n);
        d = d.divide(HUNDRED);
        d .setScale(2,RoundingMode.HALF_UP);
        this.setAmount(d);
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
    public void setAmount(Money n) {
        this.money = (BigDecimal) n.getValue();
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
    public Money add(Money n) {
        BigDecimal d = (BigDecimal) n.getValue();
        d = money.add(d);
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
    public Money subtract(Money n) {
        BigDecimal d = (BigDecimal) n.getValue();
        d = money.subtract(d);
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
    public Money multiply(Money n) {
        BigDecimal d = (BigDecimal) n.getValue();
        d = money.multiply(d);
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
    public Money div(Money n) {
        BigDecimal d = (BigDecimal) n.getValue();
        d = money.divide(d);
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

    @Override
    public Number getValue(){
        return money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoneyImpl)) return false;
        MoneyImpl money1 = (MoneyImpl) o;
        return Objects.equals(money, money1.money);
    }

    @Override
    public int hashCode() {
        return Objects.hash(money);
    }

    @Override
    public String getAsString(){
        return money.multiply(HUNDRED).toString();
    }

    @Override
    public int compareTo(Money money){
        return this.money.compareTo((BigDecimal) money.getValue());
    }
}
