package StockExchange.StockExchange.Entities;

import StockExchange.StockExchange.Money.Money;

import javax.persistence.*;
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
    private long value;

    public Company() {
    }

    public Company(long value) {
        this.value = value;
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

    public long getValue() {
        return value;
    }

    public long getValueInCurrency(){
        return Money.getMoneyInPresentCurrency(value);
    }

    public void setValue(long value) {
        this.value = value;
    }
}
