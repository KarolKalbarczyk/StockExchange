package StockExchange.StockExchange;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "person")
@DiscriminatorValue("Person")
public class Person extends Trader{

    public Person() {
    }

    public Person(int wealth) {
        this.wealth = wealth;
    }


}
