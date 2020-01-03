package StockExchange.StockExchange.Entities.DTO;

import StockExchange.StockExchange.Entities.Person;

public class PersonDTO {
    PersonDTO(Person Person) {
    }

    Person getEntity() {
        var Person = new Person();
        return Person;
    }
}
