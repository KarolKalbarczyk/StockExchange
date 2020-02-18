package StockExchange.StockExchange.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "share_")
public class Share extends BasicEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trader_id")
    private Trader owner;

    @OneToMany(orphanRemoval = true,
              cascade = CascadeType.ALL,
              mappedBy = "share",
              fetch = FetchType.LAZY)
    private Collection<StockTransaction> transactions = new HashSet<>();

    @OneToOne(orphanRemoval = true,
            cascade = CascadeType.ALL,
            mappedBy = "share",
            fetch = FetchType.LAZY)
    private Offer offer;

    int test;

    public Share(Company company){
        owner = company;
        this.company = company;
    }

    public Share(int test) {
        this.test = test;
    }

    public Share(){}

    @Override
    public String toString() {
        return "Share{" +
               // "company=" + company +
                //"offer=" + offer +
                '}';
    }

    public boolean isOwner(Trader trader){
        return owner.equals(trader);
    }

    public Collection<StockTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Collection<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

   // @PreRemove
   // public void remove(){
   //     offer = null;
    //}

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Trader getOwner() {
        return owner;
    }

    public void addTranscation(StockTransaction transaction){
        transactions.add(transaction);
    }

    public void setOwner(Trader owner) {
        this.owner = owner;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}
