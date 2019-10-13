package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.Entities.Offer_;

public class OfferCriteria extends GenericCriteria {
    public static Criteria<Offer> costEquals(double cost){
        return valueEqual(Offer.class, Offer_.cost,cost);
    }
}
