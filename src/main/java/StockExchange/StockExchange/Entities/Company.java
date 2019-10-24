package StockExchange.StockExchange.Entities;

import StockExchange.StockExchange.Money.Money;
import StockExchange.StockExchange.Money.MoneyFactory;
import StockExchange.StockExchange.Money.MoneyImpl;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
@Entity
@Table(name = "company")
@DiscriminatorValue("Company")
public class Company extends Trader{
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,orphanRemoval = true,
            mappedBy = "company")
    private Collection<Share> shares = new ArrayList<>();
    //@Digits(integer = 10,fraction = 2)
    private Money value;
   // Money a = new Money();

    public Company() {
    }

    public Company(long value) {
        this.value = MoneyFactory.getMoney(value);
    }


    @Override
    public String toString() {
        return "Company{" +
                "value=" + value +
                '}';
    }

    public Share newShare(){
        var share = new Share(this);
        shares.add(share);
        return share;
    }

    public Collection<Share> getShares() {
        return shares;
    }

    public void setShares(Collection<Share> shares) {
        this.shares = shares;
    }

    public String getValue() {
        return value.getMoneyInPresentCurrency();
    }

    public void setValue(Money value) {
        this.value = value;
    }
}
