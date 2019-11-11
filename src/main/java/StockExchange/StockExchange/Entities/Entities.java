package StockExchange.StockExchange.Entities;

public enum Entities {
    Account(Account.class),
    Company(Company.class),
    Offer(Offer.class),
    Person(Person.class),
    Share(Share.class),
    StockTranscation(StockTransaction.class),
    Trader(Trader.class);

    Class<?> clazz;

    Entities(Class<?> clazz){
        this.clazz = clazz;
    }

    public Class<?> getClazz(){return clazz;}
}
