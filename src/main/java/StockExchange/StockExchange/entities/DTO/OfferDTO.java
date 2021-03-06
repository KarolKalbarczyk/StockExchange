package StockExchange.StockExchange.entities.DTO;

import StockExchange.StockExchange.entities.Offer;
import StockExchange.StockExchange.entities.Share;
import StockExchange.StockExchange.entities.Trader;

public class OfferDTO {
    private Trader owner;

    private long cost;

    private Share share;

    public OfferDTO(Offer Offer) {
        this.owner = Offer.getOwner();
        this.cost = Offer.getCost();
        this.share = Offer.getShare();
    }

    public void setOwner(Trader owner) {
        this.owner = owner;
    }

    public Trader getOwner() {
        return owner;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public long getCost() {
        return cost;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public Share getShare() {
        return share;
    }

    public Offer getEntity() {
        var Offer = new Offer();
        Offer.setOwner(owner);
        Offer.setCost(cost);
        Offer.setShare(share);
        return Offer;
    }
}
