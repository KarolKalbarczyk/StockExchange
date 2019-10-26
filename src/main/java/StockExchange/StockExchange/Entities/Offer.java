package StockExchange.StockExchange.Entities;

import StockExchange.StockExchange.Money.Money;
import StockExchange.StockExchange.Money.MoneyFactory;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity
public class Offer extends BasicEntity {

    private String cost;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "share_id")
    private Share share;
    @ManyToOne()
    @JoinColumn(name = "trader_id")
    private Trader owner;

    public Offer() {
    }

    public Offer(Money cost,
                 Share share, Trader owner) {
        this.cost = cost.getAsString();
        this.share = share;
        share.setOffer(this);
        this.owner = owner;
    }

    @PreRemove
    public void preRemove(){
        owner = null;
        share = null;
    }

    public Money getCost() {
        return MoneyFactory.getMoney(cost);
    }

    public void setCost(Money cost) {
        this.cost = cost.getAsString();
    }

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public Trader getOwner() {
        return owner;
    }

    public void setOwner(Trader owner) {
        this.owner = owner;
    }
}
