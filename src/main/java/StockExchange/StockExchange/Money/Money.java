package StockExchange.StockExchange.Money;

import java.io.Serializable;
import java.math.BigDecimal;

public interface Money extends Comparable<Money>{
    public void setAmount(long n);
    public void setAmount(BigDecimal n);
    public void setAmount(Money n);

    public Money add(long n);
    public Money add(BigDecimal n);
    public Money add(Money n);

    public Money subtract(long n);
    public Money subtract(BigDecimal n);
    public Money subtract(Money n);

    public Money multiply(long n);
    public Money multiply(BigDecimal n);
    public Money multiply(Money n);

    public Money div(long n);
    public Money div(BigDecimal n);
    public Money div(Money n);

    public String getMoneyInPresentCurrency();
    public Number getValue();
    public String getAsString();
}
