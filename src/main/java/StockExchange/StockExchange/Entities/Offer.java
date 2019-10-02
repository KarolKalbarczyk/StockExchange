package StockExchange.StockExchange.Entities;

import javax.persistence.*;

@Entity
public class Offer extends BasicEntity {
    int cost;

    @OneToOne(cascade = CascadeType.ALL,
    fetch = FetchType.LAZY)
    @JoinColumn(name = "share_id")
    private Share share;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trader_id")
    private Trader owner;

    public Offer() {
    }

    public Offer(int cost, Share share, Trader owner) {
        this.cost = cost;
        this.share = share;
        this.owner = owner;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
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
