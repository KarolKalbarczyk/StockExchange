package StockExchange.StockExchange.Entities;

import javax.persistence.*;

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
