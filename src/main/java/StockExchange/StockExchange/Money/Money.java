package StockExchange.StockExchange.Money;

import java.io.Serializable;
import java.math.BigDecimal;

public interface Money extends Serializable{
    public void setAmount(long n);
    public void setAmount(BigDecimal n);

    public Money add(long n);
    public Money add(BigDecimal n);

    public Money subtract(long n);
    public Money subtract(BigDecimal n);

    public Money multiply(long n);
    public Money multiply(BigDecimal n);

    public Money div(long n);
    public Money div(BigDecimal n);

    public String getMoneyInPresentCurrency();

}
