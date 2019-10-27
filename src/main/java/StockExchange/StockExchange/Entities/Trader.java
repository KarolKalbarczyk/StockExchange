package StockExchange.StockExchange.Entities;

import StockExchange.StockExchange.Money.Money;
import StockExchange.StockExchange.Money.MoneyFactory;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "trader")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public abstract class Trader  extends  BasicEntity{

    protected String name;
    @Basic(fetch = FetchType.EAGER)
    protected String wealth;

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

    @Override
    public String toString() {
        return "Trader{" +
                "cost=" + wealth +
                ", name='" + name + '\'' +
                '}';
    }

    public void addWealth(Money amount){
        wealth = MoneyFactory.getMoney(wealth).add(amount).getAsString();
    }
    public void subtractWealth(Money amount){
        wealth = MoneyFactory.getMoney(wealth).subtract(amount).getAsString();
    }

    public Collection<Share> getOwnedShares() {
        return ownedShares;
    }

    public void setOwnedShares(Collection<Share> ownedShares) {
        this.ownedShares = ownedShares;
    }

    public Money getWealth() {
        return MoneyFactory.getMoney(wealth);
    }

    public void setWealth(Money wealth) {
        this.wealth = wealth.getAsString();
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
}
