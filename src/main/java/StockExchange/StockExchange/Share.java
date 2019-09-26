package StockExchange.StockExchange;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "share")
public class Share extends BasicEntity{
    @ManyToOne(fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "trader_id")
    private Trader owner;

   /* @OneToMany(orphanRemoval = true,
    cascade = CascadeType.ALL,
    mappedBy = "share")
    private Collection<StockTransaction> transactions;*/

    public Share(Company company){
        owner = company;
        this.company = company;
    }

    public Share(){}

    @Override
    public String toString() {
        return "Share{" +
                //"company=" + company +
                "owner=" + owner +
                '}';
    }

    public void changeOwner(Trader owner){
        this.owner = owner;
        owner.getOwnedShares().add(this);
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Trader getOwner() {
        return owner;
    }

    public void setOwner(Trader owner) {
        this.owner = owner;
    }
}
