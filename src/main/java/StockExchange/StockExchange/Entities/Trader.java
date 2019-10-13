package StockExchange.StockExchange.Entities;

import StockExchange.StockExchange.UserData;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Trader  extends  BasicEntity{

    @Digits(integer = 10,fraction = 2)
    protected BigDecimal wealth;
    protected String name;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,orphanRemoval = true,
            mappedBy = "owner")
    protected Collection<Share> ownedShares = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,orphanRemoval = true,
            mappedBy = "owner")
    protected Collection<Offer> offers = new ArrayList<>();

    //@OneToOne(fetch = FetchType.LAZY)
   // @MapsId
   // private UserData account;

    @Override
    public String toString() {
        return "Trader{" +
                "cost=" + wealth +
                ", name='" + name + '\'' +
                '}';
    }

    public void changeWealth(BigDecimal amount){
        wealth.add(amount);
    }

    public Collection<Share> getOwnedShares() {
        return ownedShares;
    }

    public void setOwnedShares(Collection<Share> ownedShares) {
        this.ownedShares = ownedShares;
    }

    public BigDecimal getWealth() {
        return wealth;
    }

    public void setWealth(BigDecimal wealth) {
        this.wealth = wealth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Collection<Offer> offers) {
        this.offers = offers;
    }
}
