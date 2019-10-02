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
    private int finalCost;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Trader seller;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Trader buyer;

    public StockTransaction() {
    }

    public StockTransaction(Share share,Offer offer, Trader buyer) {
        this.share = share;
        this.finalCost = offer.getCost();
        this.seller = offer.getOwner();
        this.buyer = buyer;
    }
}