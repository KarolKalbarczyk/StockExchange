package StockExchange.StockExchange.Entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
class BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
