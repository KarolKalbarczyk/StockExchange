package StockExchange.StockExchange.Entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Offer extends BasicEntity {

    private long cost;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "share_id")
    private Share share;
    @ManyToOne()
    @JoinColumn(name = "trader_id")
    private Trader owner;

    public Offer() {
    }

    public Offer(int cost,
                 Share share, Trader owner) {
        this.cost = cost;
        this.share = share;
        share.setOffer(this);
        this.owner = owner;
    }

    @PreRemove
    public void preRemove(){
        owner.getOffers().remove(this);
        share.setOffer(null);
        owner = null;
        share = null;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offer)) return false;
        Offer offer = (Offer) o;
        return Objects.equals(cost, offer.cost) && Objects.equals(id,offer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cost);
    }
}
