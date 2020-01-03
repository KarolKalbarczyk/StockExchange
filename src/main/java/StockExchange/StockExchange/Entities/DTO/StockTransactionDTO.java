package StockExchange.StockExchange.Entities.DTO;

import StockExchange.StockExchange.Entities.Share;
import StockExchange.StockExchange.Entities.StockTransaction;
import StockExchange.StockExchange.Entities.Trader;
import java.util.Calendar;

public class StockTransactionDTO {
    private Calendar date;

    private Trader seller;

    private long finalCost;

    private Share share;

    private Trader buyer;

    StockTransactionDTO(StockTransaction StockTransaction) {
        this.date = StockTransaction.getDate();
        this.seller = StockTransaction.getSeller();
        this.finalCost = StockTransaction.getFinalCost();
        this.share = StockTransaction.getShare();
        this.buyer = StockTransaction.getBuyer();
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Calendar getDate() {
        return date;
    }

    public void setSeller(Trader seller) {
        this.seller = seller;
    }

    public Trader getSeller() {
        return seller;
    }

    public void setFinalCost(long finalCost) {
        this.finalCost = finalCost;
    }

    public long getFinalCost() {
        return finalCost;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public Share getShare() {
        return share;
    }

    public void setBuyer(Trader buyer) {
        this.buyer = buyer;
    }

    public Trader getBuyer() {
        return buyer;
    }

    StockTransaction getEntity() {
        var StockTransaction = new StockTransaction();
        StockTransaction.setDate(date);
        StockTransaction.setSeller(seller);
        StockTransaction.setFinalCost(finalCost);
        StockTransaction.setShare(share);
        StockTransaction.setBuyer(buyer);
        return StockTransaction;
    }
}
