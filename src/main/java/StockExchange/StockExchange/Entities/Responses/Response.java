package StockExchange.StockExchange.Entities.Responses;

import StockExchange.StockExchange.Entities.BasicEntity;

import javax.persistence.Entity;

@Entity
public class Response extends BasicEntity {
    private String name;
    private Language language;
    private String text;
}
