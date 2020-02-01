package StockExchange.StockExchange.entities.DTO;

import StockExchange.StockExchange.entities.Company;
import StockExchange.StockExchange.entities.Offer;
import StockExchange.StockExchange.entities.Share;
import StockExchange.StockExchange.entities.StockTransaction;
import StockExchange.StockExchange.entities.Trader;
import java.util.Collection;

public class ShareDTO {
    private Trader owner;

    private Offer offer;

    private int test;

    private Company company;

    private Collection<StockTransaction> transactions;

    public ShareDTO(Share Share) {
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

    public Share getEntity() {
        var Share = new Share();
        Share.setOwner(owner);
        Share.setOffer(offer);
        Share.setTest(test);
        Share.setCompany(company);
        Share.setTransactions(transactions);
        return Share;
    }
}
