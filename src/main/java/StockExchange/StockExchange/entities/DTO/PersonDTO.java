package StockExchange.StockExchange.entities.DTO;

import StockExchange.StockExchange.entities.Person;

public class PersonDTO {
    public PersonDTO(Person Person) {
    }

    public Person getEntity() {
        var Person = new Person();
        return Person;
    }
}
