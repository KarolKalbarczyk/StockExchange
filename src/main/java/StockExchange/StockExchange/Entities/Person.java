package StockExchange.StockExchange.Entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "person")
@DiscriminatorValue("Person")
public class Person extends Trader{

    public Person() {
    }

    public Person(BigDecimal wealth) {
        this.wealth = wealth;
    }
}
