package StockExchange.StockExchange.entities;

public enum Entities {
    Account(Entities.Account_),
    Company(Company.class),
    Offer(Offer.class),
    Person(Person.class),
    Share(Share.class),
    StockTranscation(StockTransaction.class),
    Trader(Trader.class),
    OwnedShares(Share.class);

    Class<?> clazz;

    Entities(Class<?> clazz){
        this.clazz = clazz;
    }

    public Class<?> getClazz(){return clazz;}
}
