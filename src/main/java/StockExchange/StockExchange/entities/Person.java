package StockExchange.StockExchange.entities;

import javax.persistence.*;

@Entity
@Table(name = "person")
@DiscriminatorValue("Person")
public class Person extends Trader{

    public Person() {
    }

    public Person(Account account){
        super(account);
    }
}
