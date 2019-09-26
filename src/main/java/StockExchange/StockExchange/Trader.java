package StockExchange.StockExchange;

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


    public void sellShare(Trader trader,
                              Share share, int cost) {
        ownedShares.remove(share);
        wealth += cost;
        trader.changeWealth(-cost);
        share.changeOwner(trader);
    }

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
}
