package StockExchange.StockExchange.Entities;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "trader")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public abstract class Trader  extends  BasicEntity{

    protected String name;
    protected long wealth;

    @OneToMany(fetch = FetchType.LAZY,
               mappedBy = "buyer")
    protected Collection<StockTransaction> transcationsBought = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "seller")
    protected Collection<StockTransaction> transcationsSold = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,orphanRemoval = true,
            mappedBy = "owner")
    protected Collection<Share> ownedShares = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,orphanRemoval = true,
            mappedBy = "owner")
    protected Collection<Offer> offers = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public Trader(){}

    public Trader(Account account){
        this.account = account;
    }

    @Override
    public String toString() {
        return "Trader{" +
                "cost=" + wealth +
                ", name='" + name + '\'' +
                '}';
    }

    public void addWealth(long amount){
        wealth += amount;
    }
    public void subtractWealth(long amount){
        wealth -= amount;
    }

    public Collection<Share> getOwnedShares() {
        return ownedShares;
    }

    public void setOwnedShares(Collection<Share> ownedShares) {
        this.ownedShares = ownedShares;
    }

    public long getWealth() {
        return wealth;
    }

    public void setWealth(long wealth) {
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trader)) return false;
        Trader trader = (Trader) o;
        return Objects.equals(id,trader.getId()) &&
                Objects.equals(name, trader.name) &&
                Objects.equals(wealth,trader.getWealth());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
