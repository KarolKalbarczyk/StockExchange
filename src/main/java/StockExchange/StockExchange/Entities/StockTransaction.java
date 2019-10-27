package StockExchange.StockExchange.Entities;

import StockExchange.StockExchange.Money.Money;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
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
    private String finalCost;

    public StockTransaction() {
    }

    public StockTransaction(Share share,Offer offer, Trader buyer) {
        this.share = share;
        this.finalCost = offer.getCost().getAsString();
        this.seller = offer.getOwner();
        this.buyer = buyer;
    }
}
