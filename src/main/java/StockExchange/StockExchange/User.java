package StockExchange.StockExchange;

import StockExchange.StockExchange.Entities.BasicEntity;
import StockExchange.StockExchange.Entities.Trader;

import javax.persistence.Entity;

@Entity
public class User  extends BasicEntity {
    private Trader account;
}
