package StockExchange.StockExchange.Entities.DTO;

import StockExchange.StockExchange.Entities.Company;
import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.Entities.Share;
import StockExchange.StockExchange.Entities.StockTransaction;
import StockExchange.StockExchange.Entities.Trader;
import java.util.Collection;

public class ShareDTO {
    private Trader owner;

    private Offer offer;

    private int test;

    private Company company;

    private Collection<StockTransaction> transactions;

    ShareDTO(Share Share) {
        this.owner = Share.getOwner();
        this.offer = Share.getOffer();
        this.test = Share.getTest();
        this.company = Share.getCompany();
        this.transactions = Share.getTransactions();
    }

    public void setOwner(Trader owner) {
        this.owner = owner;
    }

    public Trader getOwner() {
        return owner;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public int getTest() {
        return test;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void setTransactions(Collection<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    public Collection<StockTransaction> getTransactions() {
        return transactions;
    }

    Share getEntity() {
        var Share = new Share();
        Share.setOwner(owner);
        Share.setOffer(offer);
        Share.setTest(test);
        Share.setCompany(company);
        Share.setTransactions(transactions);
        return Share;
    }
}
