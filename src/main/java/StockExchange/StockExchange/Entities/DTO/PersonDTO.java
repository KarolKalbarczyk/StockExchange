package StockExchange.StockExchange.Entities.DTO;

import StockExchange.StockExchange.Entities.Person;

public class PersonDTO {
    public PersonDTO(Person Person) {
    }

    public Person getEntity() {
        var Person = new Person();
        return Person;
    }
}
