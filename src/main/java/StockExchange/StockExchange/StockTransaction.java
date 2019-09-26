package StockExchange.StockExchange;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class StockTransaction extends BasicEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "share_id")
    private Share share;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Calendar date;
    private int proposedCost;
    private int finalCost;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Trader seller;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Trader buyer;
}
