package StockExchange.StockExchange.StringCriteria;

import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.Entities.Offer_;
import StockExchange.StockExchange.Entities.Share;
import StockExchange.StockExchange.Entities.Trader;

import javax.persistence.metamodel.Attribute;
import java.util.Arrays;
import java.util.List;

public class OfferCriteria extends GenericCriteria {


    public Criteria<Offer> costInRange(double min,double max){
        return valueInRange(Offer.class, Offer_.cost,min,max);
    }

    @Override
    public <T> Criteria<T> chooseMethod(String attribute, String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public  Criteria<Offer> chooseJoin(String attribute, Criteria... criteria) {
        return switch(attribute.toLowerCase()){
            case "share" -> joinShare(criteria);
            case "trader" -> joinTrader(criteria);
            default -> throw exception;
        };
    }

    public Criteria<Offer> joinShare(Criteria<Share>... criteria){
        return join(Offer_.share,Offer.class, Arrays.asList(criteria),Share.class);
    }

    public Criteria<Offer> joinTrader(Criteria<Trader>... criteria){
        return join(Offer_.owner,Offer.class, Arrays.asList(criteria),Trader.class);
    }

    @Override
    public  Criteria<Offer> chooseMethod(String attribute, double min, double max) {
        return switch (attribute.toLowerCase()){
            case "cost" -> costInRange(min,max);
            default -> throw exception;
        };
    }
}
