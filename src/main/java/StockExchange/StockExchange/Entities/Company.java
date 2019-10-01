package StockExchange.StockExchange.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
@Entity
@Table(name = "company")
@DiscriminatorValue("Company")
public class Company extends Trader{
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,orphanRemoval = true,
            mappedBy = "company")
    private Collection<Share> shares = new ArrayList<>();
    private int value;

    public Company() {
    }

    public Company(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Company{" +
                "value=" + value +
                '}';
    }

    public Share newShare(){
        var share = new Share(this);
        shares.add(share);
        return share;
    }

    public Collection<Share> getShares() {
        return shares;
    }

    public void setShares(Collection<Share> shares) {
        this.shares = shares;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
