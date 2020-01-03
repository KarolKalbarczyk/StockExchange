package StockExchange.StockExchange.Entities.DTO;

import StockExchange.StockExchange.Entities.Account;
import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.Entities.Share;
import StockExchange.StockExchange.Entities.Trader;
import java.lang.String;
import java.util.Collection;

public class TraderDTO {
    private long wealth;

    private Collection<Offer> offers;

    private String name;

    private Collection<Share> ownedShares;

    private Account account;

    TraderDTO(Trader Trader) {
        this.wealth = Trader.getWealth();
        this.offers = Trader.getOffers();
        this.name = Trader.getName();
        this.ownedShares = Trader.getOwnedShares();
        this.account = Trader.getAccount();
    }

    public void setWealth(long wealth) {
        this.wealth = wealth;
    }

    public long getWealth() {
        return wealth;
    }

    public void setOffers(Collection<Offer> offers) {
        this.offers = offers;
    }

    public Collection<Offer> getOffers() {
        return offers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setOwnedShares(Collection<Share> ownedShares) {
        this.ownedShares = ownedShares;
    }

    public Collection<Share> getOwnedShares() {
        return ownedShares;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
