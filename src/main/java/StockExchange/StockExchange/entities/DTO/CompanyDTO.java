package StockExchange.StockExchange.entities.DTO;

import StockExchange.StockExchange.entities.Company;
import StockExchange.StockExchange.entities.Share;
import java.util.Collection;

public class CompanyDTO {
    private Collection<Share> shares;

    private long value;

    public CompanyDTO(Company Company) {
        this.shares = Company.getShares();
        this.value = Company.getValue();
    }

    public void setShares(Collection<Share> shares) {
        this.shares = shares;
    }

    public Collection<Share> getShares() {
        return shares;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public Company getEntity() {
        var Company = new Company();
        Company.setShares(shares);
        Company.setValue(value);
        return Company;
    }
}
