package StockExchange.StockExchange.Entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Immutable
public class StockTransaction extends BasicEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "share_id")
    private Share share;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Calendar date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Trader seller;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Trader buyer;
    private long finalCost;

    public StockTransaction() {
    }

    public StockTransaction(Offer offer, Trader buyer) {
        this.share = offer.getShare();
        this.finalCost = offer.getCost();
        this.seller = offer.getOwner();
        this.buyer = buyer;
    }

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Trader getSeller() {
        return seller;
    }

    public void setSeller(Trader seller) {
        this.seller = seller;
    }

    public Trader getBuyer() {
        return buyer;
    }

    public void setBuyer(Trader buyer) {
        this.buyer = buyer;
    }

    public long getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(long finalCost) {
        this.finalCost = finalCost;
    }
}
