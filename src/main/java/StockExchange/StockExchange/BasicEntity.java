package StockExchange.StockExchange;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected long id;
}
