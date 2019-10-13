package StockExchange.StockExchange.Entities;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity
public class Offer extends BasicEntity {

    @Digits(integer = 10,fraction = 2)
    protected BigDecimal cost;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "share_id")
    private Share share;
    @ManyToOne()
    @JoinColumn(name = "trader_id")
    private Trader owner;

    public Offer() {
    }

    public Offer(@Digits(integer = 10, fraction = 2) BigDecimal cost,
                 Share share, Trader owner) {
        this.cost = cost;
        this.share = share;
        this.owner = owner;
    }

    @PreRemove
    public void preRemove(){
        share = null;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
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
