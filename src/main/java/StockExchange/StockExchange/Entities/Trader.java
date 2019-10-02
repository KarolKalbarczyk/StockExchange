package StockExchange.StockExchange.Entities;

import StockExchange.StockExchange.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Trader  extends  BasicEntity{

    protected int wealth;
    protected String name;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,orphanRemoval = true,
            mappedBy = "owner")
    protected Collection<Share> ownedShares = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,orphanRemoval = true,
            mappedBy = "owner")
    protected Collection<Offer> offers = new ArrayList<>();

    public void sellShare(Trader trader,
                              Share share, int cost) {
        ownedShares.remove(share);
        wealth += cost;
        trader.changeWealth(-cost);
        share.changeOwner(trader);
    }

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @Override
    public String toString() {
        return "Trader{" +
                "wealth=" + wealth +
                ", name='" + name + '\'' +
                '}';
    }

    public void changeWealth(int amount){
        wealth += amount;
    }

    public Collection<Share> getOwnedShares() {
        return ownedShares;
    }

    public void setOwnedShares(Collection<Share> ownedShares) {
        this.ownedShares = ownedShares;
    }

    public int getWealth() {
        return wealth;
    }

    public void setWealth(int wealth) {
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
